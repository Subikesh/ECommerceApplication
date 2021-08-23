package com.example.ecommerceapplication.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.data.db.EcommerceContract
import com.example.data.session.SessionManager
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private lateinit var session: SessionManager

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        session = SessionManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userText = binding.loginUser
        val passText = binding.loginPassword
        val loginButton = binding.loginSubmit
        val signupButton = binding.loginSignUp

        loginButton.setOnClickListener {
            val userFound = EcommerceContract.UserEntry.findEntry(
                requireActivity(),
                userText.text.toString(),
                passText.text.toString()
            )
            if (userFound) {
                session.login = true
                findNavController().navigate(R.id.action_loginFragment_to_profileFragment)
            } else {
                session.login = false
            }
            // TODO: change backstack of navigation from profile page to login page or home page
            // TODO: if user not found, do something
        }
    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}