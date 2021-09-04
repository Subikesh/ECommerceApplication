package com.example.ecommerceapplication.ui.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.data.entities.User
import com.example.data.usecases.Authentication
import com.example.ecommerceapplication.MainActivity
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.databinding.FragmentLoginBinding
import com.example.ecommerceapplication.validators.TextValidators
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        // Toolbar
        val toolbar = binding.loginToolbar.root
        toolbar.title = "Login"
        (activity as MainActivity).setSupportActionBar(toolbar)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mailText = binding.loginEmail
        val passText = binding.loginPassword
        val loginButton = binding.loginSubmit
        val signupButton = binding.loginSignUp

        val authentication = Authentication(requireActivity())

        loginButton.setOnClickListener {
            var user: User?
            val mailValid = TextValidators.checkEmail(mailText)
            val passValid = TextValidators.checkPassword(passText)
            if (mailValid && passValid) {
                lifecycleScope.launch {
                    user =
                        authentication.userLogin(mailText.text.toString(), passText.text.toString())
                    if (user == null)
                        Toast.makeText(context, "User credentials incorrect!", Toast.LENGTH_SHORT)
                            .show()
                    else
                        findNavController().navigate(R.id.action_loginFragment_to_navigation_user)
                    Log.d(TAG, "onViewCreated: Login done $user")
                }
            }
        }

        signupButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "LoginFragment"
        fun getInstance() = LoginFragment()
    }
}