package com.example.ecommerceapplication

import android.graphics.Paint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.domain.models.Product
import com.example.ecommerceapplication.databinding.FragmentProductBinding
import com.example.ecommerceapplication.extensions.getGlideImage

const val PRODUCT_OBJECT = "productObject"

class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private lateinit var productObj: Product

    companion object {
        fun newInstance() = ProductFragment()
    }

    private lateinit var viewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        _binding = FragmentProductBinding.inflate(inflater, container, false)

        productObj = arguments?.get(PRODUCT_OBJECT) as Product

        // Toolbar
        val toolbar = binding.productToolbar.root
        (activity as MainActivity).setSupportActionBar(toolbar)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Binding all views to corresponding product detail */
        binding.singleProductImage.getGlideImage(requireActivity(), productObj.imageUrl)
        binding.singleProductTitle.text = productObj.title
        binding.productBrand.text = productObj.brand
        binding.discountText.text = getString(R.string.discount, productObj.discountPercent.toInt())
        binding.singleProductMrp.paintFlags = binding.singleProductMrp.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        binding.singleProductMrp.text = getString(R.string.price_holder, productObj.maximumRetailPrice.value)
        binding.productOfferPrice.text = getString(R.string.price_holder, productObj.discountPrice.value)

        // Only show discount red icon if there is > 20% discount
        if (productObj.discountPercent < 20)
            binding.discountText.visibility = View.GONE
        else
            binding.discountText.text = getString(R.string.discount, productObj.discountPercent.toInt())

        // Show product description or not
        if (productObj.description.isNotEmpty()) {
            binding.descriptionText.text = productObj.description
        } else {
            binding.description.visibility = View.GONE
            binding.descriptionText.visibility = View.GONE
        }

        // Show Out of stock text or not
        if (!productObj.inStock) {
            binding.outOfStock.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}