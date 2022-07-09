package com.example.ecommerceapplication.ui.cart

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.fragment.findNavController
import com.example.data.di.RoomModule
import com.example.data.roomdb.entities.ShoppingCart
import com.example.data.session.SessionManager
import com.example.data.usecases.UserShoppingCart
import com.example.ecommerceapplication.MainActivity
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.databinding.FragmentCheckoutBinding
import com.example.ecommerceapplication.di.AppModule
import com.example.ecommerceapplication.di.DaggerAppComponent
import com.example.ecommerceapplication.extensions.NotificationChannels
import com.example.ecommerceapplication.extensions.initAlertDialog
import com.example.ecommerceapplication.validators.TextValidators
import kotlinx.coroutines.launch
import javax.inject.Inject

const val SHOPPING_CART = "shoppingCart"

class CheckoutFragment : Fragment() {

    @Inject
    lateinit var session: SessionManager
    @Inject
    lateinit var userShoppingCart: UserShoppingCart

    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CartViewModel

    private var cart: ShoppingCart? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        DaggerAppComponent.builder()
            .appModule(AppModule(requireActivity()))
            .roomModule(RoomModule(requireActivity()))
            .build().inject(this)

        val factory = CartViewModel.Factory(requireActivity().application, session, userShoppingCart)
        viewModel = ViewModelProvider(this, factory).get(CartViewModel::class.java)
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)

        val toolbar = binding.checkoutToolbar.root
        toolbar.title = getString(R.string.checkout_title)
        (activity as MainActivity).setSupportActionBar(toolbar)
        (activity as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cart = arguments?.get(SHOPPING_CART) as ShoppingCart?
        val buyFromProduct: Boolean = (cart != null)

        // Deeplink PendingIntent to go to order fragment
        val redirectIntent = NavDeepLinkBuilder(requireActivity())
            .setGraph(R.navigation.mobile_navigation)
            .setDestination(R.id.orderFragment)
            .createPendingIntent()

        val notificationChannel = NotificationChannels.OrdersChannel

        val builder = NotificationCompat.Builder(
            requireContext(),
            notificationChannel.CHANNEL_ID
        ).setSmallIcon(R.mipmap.ic_launcher_round)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(redirectIntent)

        // Card number formatting
        binding.cardInputCheckout.addTextChangedListener(object : TextWatcher {
            private var current = ""
            val nonDigits = "[^\\d]".toRegex()

            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (s.toString() != current) {
                    val userInput = s.toString().replace(nonDigits, "")
                    if (userInput.length <= 16) {
                        current = userInput.chunked(4).joinToString(" ")
                        s.filters = arrayOfNulls<InputFilter>(0)
                    }
                    s.replace(0, s.length, current, 0, current.length)
                }
            }
        })

        binding.makePaymentBtn.setOnClickListener {
            if (validateInputs()) {
                val alertDialog = AlertDialog.Builder(requireActivity())
                alertDialog.initAlertDialog(
                    "Order confirmation",
                    "Are you sure you want to place the order?",
                    { _, _ ->
                        lifecycleScope.launch {
                            builder.setContentTitle("Order placed!")
                                .setContentText("Your order is placed successfully!")
                            // Show order placed notification
                            with(NotificationManagerCompat.from(requireContext())) {
                                notify(notificationChannel.notificationId, builder.build())
                            }

                            if (buyFromProduct)
                                viewModel.makeOrder(cart!!)
                            else
                                viewModel.moveCartToOrder()
                            findNavController().navigate(R.id.action_checkoutFragment_to_orderFragment)
                        }
                    },
                    { dialog, _ -> dialog.cancel() })
                alertDialog.show()
            }
        }

        binding.failPaymentBtn.setOnClickListener {
            val alertDialog = AlertDialog.Builder(requireActivity())
            alertDialog.initAlertDialog(
                "Order confirmation",
                "Are you sure you want to place the order?",
                { _, _ ->
                    lifecycleScope.launch {
                        if (buyFromProduct)
                            viewModel.makeOrder(cart!!, false)
                        else
                            viewModel.moveCartToOrder(false)
                        findNavController().navigate(R.id.action_checkoutFragment_to_orderFragment)
                    }
                },
                { dialog, _ -> dialog.cancel() })
            alertDialog.show()
        }

        binding.cancelPaymentBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun validateInputs() = TextValidators.checkCard(binding.cardInputCheckout) &&
            TextValidators.checkEmpty(binding.nameInputCheckout, "Enter card owner name") &&
            TextValidators.checkExpiry(binding.expiryMonth, binding.expiryYearCheckout) &&
            TextValidators.checkCvv(binding.cvvInputCheckout)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}