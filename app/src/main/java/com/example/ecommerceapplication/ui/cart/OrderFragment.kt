package com.example.ecommerceapplication.ui.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceapplication.MainActivity
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.databinding.FragmentOrderBinding
import com.example.ecommerceapplication.extensions.initRecyclerView
import kotlinx.coroutines.launch

class OrderFragment : Fragment() {

    private lateinit var viewModel: CartViewModel
    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(CartViewModel::class.java)

        _binding = FragmentOrderBinding.inflate(inflater, container, false)

        // Toolbar
        val toolbar = binding.orderToolbar.root
        toolbar.title = getString(R.string.profile_orders)
        (activity as MainActivity).setSupportActionBar(toolbar)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvOrders = binding.ordersRv

        lifecycleScope.launch {
            val orders = viewModel.getOrderList()
            Log.d("Order", orders.toString())
            rvOrders.initRecyclerView(
                LinearLayoutManager(requireActivity()),
                OrderRecyclerAdapter(orders, requireActivity())
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}