package com.example.ecommerceapplication.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data.di.RoomModule
import com.example.data.session.SessionManager
import com.example.data.repository.UserOrders
import com.example.data.repository.UserShoppingCart
import com.example.ecommerceapplication.MainActivity
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.databinding.FragmentOrderBinding
import com.example.ecommerceapplication.di.AppModule
import com.example.ecommerceapplication.di.DaggerAppComponent
import com.example.ecommerceapplication.extensions.initRecyclerView
import kotlinx.coroutines.launch
import javax.inject.Inject

class OrderFragment : Fragment() {

    @Inject
    lateinit var session: SessionManager
    @Inject
    lateinit var userShoppingCart: UserShoppingCart
    @Inject
    lateinit var userOrder: UserOrders

    private lateinit var viewModel: CartViewModel
    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        DaggerAppComponent.builder()
            .appModule(AppModule(requireActivity()))
            .roomModule(RoomModule(requireActivity()))
            .build().inject(this)
        val factory = CartViewModel.Factory(session, userShoppingCart, userOrder)
        viewModel = ViewModelProvider(this, factory).get(CartViewModel::class.java)

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
            if (orders != null) {
                if (orders.isNotEmpty()) {
                    rvOrders.initRecyclerView(
                        LinearLayoutManager(requireActivity()),
                        OrderRecyclerAdapter(orders, requireActivity())
                    )
                } else {
                    Toast.makeText(context, "You have not made any orders yet.", Toast.LENGTH_SHORT)
                        .show()
                }
            } else // If user tries to see orders when he's logged out
                findNavController().popBackStack(R.id.navigation_home, false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}