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
import com.example.data.db.EcommerceDatabase
import com.example.data.entities.User
import com.example.data.session.SessionManager
import com.example.data.usecases.Authentication
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private lateinit var session: SessionManager
    private lateinit var db: EcommerceDatabase

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

        val mailText = binding.loginEmail
        val passText = binding.loginPassword
        val loginButton = binding.loginSubmit
        val signupButton = binding.loginSignUp

        val authentication = Authentication(requireActivity())

        /*loginButton.setOnClickListener {
            val userFound = EcommerceContract.UserEntry.findEntry(
                requireActivity(),
                mailText.text.toString(),
                passText.text.toString()
            )
            if (userFound) {
                session.login = true
                findNavController().navigate(R.id.action_loginFragment_to_navigation_user)
            } else {
                session.login = false
            }
            // TODO: change backstack of navigation from profile page to login page or home page
            // TODO: if user not found, do something
        }

        signupButton.setOnClickListener {
            Log.i(TAG, "onViewCreated: signup processing")
            val inserted = EcommerceContract.UserEntry.addEntry(
                requireActivity(),
                "username",
                passText.text.toString(),
                mailText.text.toString()
            )
            if (inserted < 0) {
                Log.i(TAG, "onViewCreated: no user created")
                Toast.makeText(context, "no user created", Toast.LENGTH_SHORT).show()
            } else {
                Log.i(TAG, "onViewCreated: \"$inserted userId created\"")
                Toast.makeText(context, "$inserted userId created", Toast.LENGTH_SHORT).show()
            }
        }*/

        loginButton.setOnClickListener {
            var user: User?
            lifecycleScope.launch {
                user =
                    authentication.userLogin(mailText.text.toString(), passText.text.toString())
                Log.d(TAG, "onViewCreated: User: $user")
                if (user == null)
                    Toast.makeText(context, "User credentials incorrect!", Toast.LENGTH_SHORT).show()
                else
                    findNavController().navigate(R.id.action_loginFragment_to_navigation_user)
                Log.d(TAG, "onViewCreated: Login done ${user?.email ?: "Null"}")
            }
        }

        signupButton.setOnClickListener {
            // TODO: create signup functionality
        }
    }

    companion object {
        private const val TAG = "LoginFragment"
        fun newInstance() = LoginFragment()
    }
}