package com.example.ecommerceapplication.ui.user

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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.data.di.RoomModule
import com.example.data.session.SessionManager
import com.example.data.usecases.Authentication
import com.example.data.usecases.UserWishlist
import com.example.ecommerceapplication.MainActivity
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.databinding.FragmentWishlistBinding
import com.example.ecommerceapplication.di.AppModule
import com.example.ecommerceapplication.di.DaggerAppComponent
import com.example.ecommerceapplication.extensions.initRecyclerView
import com.example.ecommerceapplication.ui.home.products.ProductRecyclerAdapter
import com.example.ecommerceapplication.ui.product.PRODUCT_OBJECT
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO: Move this fragment to app>ui>cart

class WishlistFragment : Fragment() {

    @Inject
    lateinit var authentication: Authentication
    @Inject
    lateinit var session: SessionManager
    @Inject
    lateinit var userWishlist: UserWishlist

    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        DaggerAppComponent.builder()
            .appModule(AppModule(requireActivity()))
            .roomModule(RoomModule(requireActivity()))
            .build().inject(this)
        val factory = UserViewModel.Factory(authentication, session, userWishlist)
        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)

        // Toolbar
        val toolbar = binding.wishlistToolbar.root
        toolbar.title = getString(R.string.profile_wishlist)
        (activity as MainActivity).setSupportActionBar(toolbar)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvProducts = binding.wishlistProducts
        lifecycleScope.launch {
            val wishlist = viewModel.getWishlist()
            if (wishlist != null) {
                if (wishlist.isNotEmpty()) {
                    rvProducts.initRecyclerView(
                        GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false),
                        ProductRecyclerAdapter(wishlist, requireActivity(), onItemClicked = {
                            val bundle = bundleOf(PRODUCT_OBJECT to it)
                            findNavController().navigate(
                                R.id.action_wishlistFragment_to_productFragment,
                                bundle
                            )
                        }), true
                    )
                } else {
                    Toast.makeText(context, "Your wishlist is empty", Toast.LENGTH_SHORT).show()
                }
            } else
                Toast.makeText(context, "Error retrieving your wishlist", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = WishlistFragment()
    }
}