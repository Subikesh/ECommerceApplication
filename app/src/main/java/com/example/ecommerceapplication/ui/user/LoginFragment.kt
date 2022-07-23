package com.example.ecommerceapplication.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.data.di.RoomModule
import com.example.data.session.SessionManager
import com.example.data.repository.Authentication
import com.example.data.repository.UserWishlist
import com.example.ecommerceapplication.MainActivity
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.databinding.FragmentLoginBinding
import com.example.ecommerceapplication.di.AppModule
import com.example.ecommerceapplication.di.DaggerAppComponent
import com.example.ecommerceapplication.validators.TextValidators
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginFragment : Fragment() {

    @Inject
    lateinit var authentication: Authentication
    @Inject
    lateinit var session: SessionManager
    @Inject
    lateinit var userWishlist: UserWishlist

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: UserViewModel

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

        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        // Toolbar
        val toolbar = binding.loginToolbar.root
        toolbar.title = "Login"
        (activity as MainActivity).setSupportActionBar(toolbar)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mailText = binding.loginEmail
        val passText = binding.loginPassword
        val loginButton = binding.loginSubmit
        val signupButton = binding.loginSignUp

        loginButton.setOnClickListener {
            loginUser(mailText, passText)
        }

        signupButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        // Clearing password text on state restored
        binding.loginPassword.text.clear()
    }

    private fun loginUser(
        mailText: EditText,
        passText: EditText
    ) {
        val mailValid = TextValidators.checkEmail(mailText)
        val passValid = TextValidators.checkPassword(passText)
        if (mailValid && passValid) {
            lifecycleScope.launch {
                viewModel.loginUser(mailText.text.toString(), passText.text.toString())
                if (viewModel.user != null) {
                    findNavController().navigate(R.id.action_loginFragment_to_navigation_home)
                } else
                    Toast.makeText(context, "User credentials incorrect!", Toast.LENGTH_SHORT)
                        .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}