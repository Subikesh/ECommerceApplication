package com.example.ecommerceapplication.validators

import android.widget.EditText
import java.util.regex.Pattern

object TextValidators {

    /**
     * Password validation regex
     */
    private const val PASSWORD_VALIDATOR = "^(?=.*[0-9])(?=.*[A-Za-z])(?=\\S+\$).{8,}\$"

    /**
     * Checks if the email entered is valid or not
     * @param mailText  The edit text of the entered email
     * @return true if mail is valid, else false
     */
    fun checkEmail(mailText: EditText): Boolean {
        val email = mailText.text.toString()
        var error = true
        if (email == "") {
            mailText.error = "Please enter an email address!"
            error = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mailText.error = "Please enter a valid email address!"
            error = false
        }
        if (error)
            mailText.requestFocus()
        return error
    }

    /**
     * Checks if the password entered is valid or not based on the regex PASSWORD_VALIDATOR
     * @param mailText  The edit text of the entered email
     * @return true if mail is valid, else false
     */
    fun checkPassword(passwordText: EditText): Boolean {
        val password = passwordText.text.toString()
        val pattern = Pattern.compile(PASSWORD_VALIDATOR)
        var error = true
        if (password == "") {
            passwordText.error = "Please enter password!"
            error = false
        } else if (!pattern.matcher(password).matches()) {
            passwordText.error = "Password must have 8 characters and have at least an alphabet and a number"
            error = false
        }
        if (error)
            passwordText.requestFocus()
        return error
    }
}