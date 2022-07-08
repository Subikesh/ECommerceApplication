package com.example.ecommerceapplication.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.data.di.RoomModule
import com.example.data.session.SessionManager
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.databinding.FragmentUserBinding
import com.example.ecommerceapplication.di.AppModule
import com.example.ecommerceapplication.di.DaggerAppComponent
import javax.inject.Inject

class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    @Inject
    lateinit var session: SessionManager

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        DaggerAppComponent.builder()
            .appModule(AppModule(requireActivity()))
            .roomModule(RoomModule(requireActivity()))
            .build().inject(this)

        _binding = FragmentUserBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Let user navigate to profile page if authenticated and login_page if not
        if (session.login && session.user != null) {
            findNavController().navigate(R.id.action_navigation_user_to_profileFragment)
        } else {
            findNavController().navigate(R.id.action_navigation_user_to_loginFragment)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}