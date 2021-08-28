package com.example.ecommerceapplication.ui.user

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.data.entities.User
import com.example.data.usecases.Authentication
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.databinding.FragmentSignupBinding
import kotlinx.coroutines.launch

class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mailText = binding.signupEmail
        val passText = binding.signupPassword
        val confirmPassword = binding.signupConfirmPassword
        val userText = binding.signupUsername

        val authentication = Authentication(requireActivity())

        binding.createAccountButton.setOnClickListener {
            var user: User?
            if (passText.text.toString() == confirmPassword.text.toString()) {
                lifecycleScope.launch {
                    user = authentication.userSignup(
                        mailText.text.toString(),
                        passText.text.toString(),
                        userText.text.toString()
                    )
                    if (user != null) {
                        findNavController().navigate(R.id.action_signupFragment_to_navigation_user2)
                        Log.d(TAG, "onViewCreated: User logged in")
                    } else {
                        Toast.makeText(context, "User email already exists", Toast.LENGTH_SHORT)
                            .show()
                        Log.d(TAG, "onViewCreated: User not logged in")
                    }
                }
            } else {
                Toast.makeText(context, "Passwords don't match!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "SignupFragment"
        fun getInstance() = SignupFragment()
    }
}