package com.example.ecommerceapplication.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data.repository.ProductEntityMapperImpl
import com.example.ecommerceapplication.MainActivity
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.databinding.FragmentCartBinding
import com.example.ecommerceapplication.extensions.initRecyclerView
import com.example.ecommerceapplication.ui.product.PRODUCT_OBJECT
import kotlinx.coroutines.launch

class CartFragment : Fragment() {

    private lateinit var viewModel: CartViewModel
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(CartViewModel::class.java)

        _binding = FragmentCartBinding.inflate(inflater, container, false)

        // Toolbar
        val toolbar = binding.cartToolbar.root
        toolbar.title = getString(R.string.title_cart)
        setHasOptionsMenu(true)
        (activity as MainActivity).setSupportActionBar(toolbar)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val rvCart = binding.cartRv
            val cartList = viewModel.getCartAndProductList()
            if (cartList == null || cartList.isEmpty()) {
                binding.totalCost.text = getString(R.string.price_holder, 0.0)
                Toast.makeText(
                    context,
                    "There are no items in your shopping cart",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                var totalPrice = 0.0
                for (cart in cartList)
                    totalPrice += cart.product.discountPrice * cart.cartItem.quantity

                binding.totalCost.text = getString(R.string.price_holder, totalPrice)

                val cartRvAdapter = CartRecyclerAdapter(
                    cartList.toMutableList(),
                    requireActivity(),
                    onItemClicked = {
                        val bundle = bundleOf(PRODUCT_OBJECT to ProductEntityMapperImpl.fromEntity(it))
                        findNavController().navigate(
                            R.id.action_navigation_cart_to_productFragment,
                            bundle
                        )
                    },
                    saveItemQuantity = { cart, quantity ->
                        viewModel.increaseProductQuantity(cart, quantity)
                    },
                    deleteCartItem = {
                        viewModel.deleteCartItem(it)
                    },
                    updateTotal = { cartItemList ->
                        var price = 0.0
                        for (cartItem in cartItemList)
                            price += cartItem.product.discountPrice * cartItem.cartItem.quantity
                        binding.totalCost.text = getString(R.string.price_holder, price)
                        viewModel.updateTotal(cartItemList[0].cartItem.cartId, price)
                    })

                rvCart.initRecyclerView(LinearLayoutManager(requireActivity()), cartRvAdapter)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}