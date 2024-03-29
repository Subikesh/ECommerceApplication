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
import com.example.ecommerceapplication.databinding.FragmentSignupBinding
import com.example.ecommerceapplication.di.AppModule
import com.example.ecommerceapplication.di.DaggerAppComponent
import com.example.ecommerceapplication.validators.TextValidators
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignupFragment : Fragment() {

    @Inject
    lateinit var authentication: Authentication
    @Inject
    lateinit var session: SessionManager
    @Inject
    lateinit var userWishlist: UserWishlist

    private var _binding: FragmentSignupBinding? = null
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

        _binding = FragmentSignupBinding.inflate(inflater, container, false)

        val factory = UserViewModel.Factory(authentication, session, userWishlist)
        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        // Toolbar
        val toolbar = binding.signupToolbar.root
        toolbar.title = "Create a new account"
        (activity as MainActivity).setSupportActionBar(toolbar)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mailText = binding.signupEmail
        val passText = binding.signupPassword
        val confirmPassword = binding.signupConfirmPassword
        val userText = binding.signupUsername

        binding.createAccountButton.setOnClickListener {
            createUser(mailText, passText, confirmPassword, userText)
        }
    }

    private fun createUser(
        mailText: EditText,
        passText: EditText,
        confirmPassword: EditText,
        userText: EditText
    ) {
        val mailValid = TextValidators.checkEmail(mailText)
        val passValid = TextValidators.checkPassword(passText)
        if (mailValid && passValid) {
            lifecycleScope.launch {
                val created = viewModel.createUser(
                    mailText.text.toString(),
                    passText.text.toString(),
                    confirmPassword.text.toString(),
                    userText.text.toString()
                )
                if (created) {
                    if (viewModel.user != null) {
                        Toast.makeText(context, "User logged in", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_signupFragment_to_navigation_home)
                    } else {
                        Toast.makeText(context, "User email already exists", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(context, "Passwords don't match!", Toast.LENGTH_SHORT).show()
                }
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