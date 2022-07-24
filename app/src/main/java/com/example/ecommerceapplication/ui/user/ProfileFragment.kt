package com.example.ecommerceapplication.ui.user

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.data.di.RoomModule
import com.example.data.session.SessionManager
import com.example.data.repository.Authentication
import com.example.domain.repository.WishlistRepository
import com.example.ecommerceapplication.MainActivity
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.databinding.FragmentProfileBinding
import com.example.ecommerceapplication.di.AppModule
import com.example.ecommerceapplication.di.DaggerAppComponent
import com.example.ecommerceapplication.extensions.initAlertDialog
import javax.inject.Inject

class ProfileFragment : Fragment() {
    @Inject
    lateinit var authentication: Authentication
    @Inject
    lateinit var session: SessionManager
    @Inject
    lateinit var userWishlist: WishlistRepository

    private var _binding: FragmentProfileBinding? = null
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
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val factory = UserViewModel.Factory(authentication, session, userWishlist)
        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        // Toolbar
        val toolbar = binding.profileToolbar.root
        toolbar.title = "User Profile"
        (activity as MainActivity).setSupportActionBar(toolbar)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = session.user
        binding.profileUsername.text = user?.username
        binding.profileEmail.text = user?.email

        binding.profileWishlist.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_wishlistFragment)
        }

        binding.profileCart.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_navigation_cart)
        }

        binding.profileOrders.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_orderFragment)
        }

        binding.logoutButton.setOnClickListener {
            val alertDialog = AlertDialog.Builder(requireActivity())
            alertDialog.initAlertDialog(
                "Logout",
                "Are you sure you want to logout?",
                { _, _ ->
                    viewModel.logoutUser()
                    findNavController().navigate(R.id.action_profileFragment_to_navigation_user)
                },
                { dialog, _ ->
                    dialog.cancel()
                })
            alertDialog.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}