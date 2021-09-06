package com.example.ecommerceapplication.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.data.session.SessionManager
import com.example.data.usecases.Authentication
import com.example.ecommerceapplication.MainActivity
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private lateinit var session: SessionManager
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        session = SessionManager(requireContext())

        // Toolbar
        val toolbar = binding.profileToolbar.root
        toolbar.title = "User Profile"
        (activity as MainActivity).setSupportActionBar(toolbar)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val logoutBtn = binding.logoutButton
        val authentication = Authentication(requireActivity())
        val session = SessionManager(requireActivity())
        val user = session.user
        binding.profileUsername.text = user?.username

        logoutBtn.setOnClickListener {
            authentication.userLogout()
            findNavController().navigate(R.id.action_profileFragment_to_navigation_user)
        }
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}