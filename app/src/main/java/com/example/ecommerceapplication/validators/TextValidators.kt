package com.example.ecommerceapplication.validators

import android.os.Build
import android.text.Html
import android.widget.EditText
import java.util.regex.Pattern

object TextValidators {

    /**
     * Password validation regex
     */
    private const val PASSWORD_VALIDATOR = "^(?=.*[0-9])(?=.*[A-Za-z])(?=\\S+\$).{8,}\$"
    private const val PASSWORD_ERROR_TEXT = "Password must have <ul>" +
            "<li> at least 8 characters</li>" +
            "<li> an alphabet</li>" +
            "<li> a number</li>"
    private const val EMAIL_ERROR_TEXT = "Please enter a valid email address!"

    /**
     * Checks if the email entered is valid or not
     * @param mailText The edit text of the entered email
     * @return true if mail is valid, else false
     */
    fun checkEmail(mailText: EditText): Boolean {
        val email = mailText.text.toString()
        var error = true
        if (email == "") {
            mailText.error = "Please enter an email address!"
            error = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mailText.error = EMAIL_ERROR_TEXT
            error = false
        }
        if (error)
            mailText.requestFocus()
        return error
    }

    /**
     * Checks if the password entered is valid or not based on the regex PASSWORD_VALIDATOR
     * @param passwordText The edit text of the entered email
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
            passwordText.error = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) Html.fromHtml(
                PASSWORD_ERROR_TEXT,
                Html.FROM_HTML_MODE_COMPACT
            ) else Html.fromHtml(
                PASSWORD_ERROR_TEXT
            )
            error = false
        }
        if (error)
            passwordText.requestFocus()
        return error
    }
}