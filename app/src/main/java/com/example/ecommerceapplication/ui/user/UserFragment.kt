package com.example.ecommerceapplication.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.data.session.SessionManager
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.databinding.FragmentUserBinding

class UserFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private var _binding: FragmentUserBinding? = null
    private lateinit var session: SessionManager

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userViewModel =
            ViewModelProvider(this).get(UserViewModel::class.java)

        _binding = FragmentUserBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textUser
        userViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        // Let user navigate to login_page if not authenticated or profile page if authenticated
        session = SessionManager(requireActivity())
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