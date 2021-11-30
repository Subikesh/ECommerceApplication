package com.example.ecommerceapplication.ui.product

import android.graphics.Paint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.domain.models.Product
import com.example.ecommerceapplication.MainActivity
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.databinding.FragmentProductBinding
import com.example.ecommerceapplication.extensions.NotificationChannels
import com.example.ecommerceapplication.extensions.getGlideImage
import com.example.ecommerceapplication.ui.cart.SHOPPING_CART
import kotlinx.coroutines.launch

const val PRODUCT_OBJECT = "productObject"

class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private lateinit var productObj: Product
    private lateinit var viewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        _binding = FragmentProductBinding.inflate(inflater, container, false)

        productObj = arguments?.get(PRODUCT_OBJECT) as Product
        viewModel.setProduct(productObj)

        // Toolbar
        val toolbar = binding.productToolbar.root
        toolbar.title = productObj.title
        (activity as MainActivity).setSupportActionBar(toolbar)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Binding all views to corresponding product detail */
        binding.singleProductImage.getGlideImage(requireActivity(), productObj.bigImageUrl)
        binding.singleProductTitle.text = productObj.title
        binding.productBrand.text = productObj.brand
        binding.discountText.text = getString(R.string.discount, productObj.discountPercent.toInt())
        binding.singleProductMrp.paintFlags =
            binding.singleProductMrp.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        binding.singleProductMrp.text =
            getString(R.string.price_holder, productObj.maximumRetailPrice.amount)
        binding.productOfferPrice.text =
            getString(R.string.price_holder, productObj.discountPrice.amount)

        // Only show discount red icon if there is > 20% discount
        if (productObj.discountPercent < 20)
            binding.discountText.visibility = View.GONE
        else
            binding.discountText.text =
                getString(R.string.discount, productObj.discountPercent.toInt())

        // Show product description or not
        if (productObj.description.isNotEmpty()) {
            binding.descriptionText.text = productObj.description
        } else {
            binding.description.visibility = View.GONE
            binding.productSeparator.visibility = View.GONE
            binding.descriptionText.visibility = View.GONE
        }

        // Show Out of stock text or not
        if (!productObj.inStock) {
            binding.outOfStock.visibility = View.GONE
        }

        // Helper function to set UI if item already in cart
        fun itemInCart() {
            binding.cartButton.text = getString(R.string.go_to_cart)
            binding.cartButton.setOnClickListener {
                findNavController().navigate(R.id.action_productFragment_to_navigation_cart)
            }
        }

        // Set wishlist as selected if it is already selected
        lifecycleScope.launch {
            if (viewModel.inWishlist()) {
                binding.wishlistProduct.setImageResource(R.drawable.heart_filled_24)
            }

            if (viewModel.isInCart()) {
                itemInCart()
            } else {
                binding.cartButton.setOnClickListener {
                    if (viewModel.addProductToCart()) {
                        Toast.makeText(context, "Product added to cart", Toast.LENGTH_SHORT).show()
                        itemInCart()
                    } else
                        Toast.makeText(context, "Login to add product to cart", Toast.LENGTH_SHORT)
                            .show()
                }

                binding.buyNowButton.setOnClickListener {
                    if (viewModel.isLoggedIn()) {
                        lifecycleScope.launch {
                            val cart = viewModel.buyProduct()
                            val bundle = bundleOf(SHOPPING_CART to cart)
                            findNavController().navigate(
                                R.id.action_productFragment_to_checkoutFragment,
                                bundle
                            )
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Please login to buy this product",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        /** Implementing on click functionalities */
        binding.wishlistProduct.setOnClickListener {
            // Notify user that wishlist updated
            val notificationId = 0
            val builder = NotificationCompat.Builder(
                requireContext(),
                NotificationChannels.OffersChannel.CHANNEL_ID
            ).setSmallIcon(R.mipmap.ic_launcher_round)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setTimeoutAfter(10000)

            lifecycleScope.launch {
                if (viewModel.wishlistProduct()) {
                    Toast.makeText(context, "Product added to your wishlist", Toast.LENGTH_SHORT)
                        .show()
                    (it as ImageButton).setImageResource(R.drawable.heart_filled_24)
                    builder.setContentTitle("Added item to your wishlist!")
                        .setContentText(
                            "${
                                if (productObj.title.length > 15)
                                    productObj.title.substring(0, 15) + "..."
                                else productObj.title
                            } added to your wishlist"
                        )
                } else {
                    (it as ImageButton).setImageResource(R.drawable.heart_blank_24)
                    builder.setContentTitle("Removed item from your wishlist!")
                        .setContentText("${
                            if (productObj.title.length > 15)
                                productObj.title.substring(0, 15) + "..."
                            else productObj.title
                        } removed from your wishlist")
                }
                // Showing notification
                with(NotificationManagerCompat.from(requireContext())) {
                    notify(notificationId, builder.build())
                    Log.d("Notification", "Notification started")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}