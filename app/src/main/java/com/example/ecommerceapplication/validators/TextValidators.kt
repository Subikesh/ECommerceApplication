package com.example.ecommerceapplication.validators

import android.os.Build
import android.text.Html
import android.util.Log
import android.widget.EditText
import java.util.regex.Pattern

object TextValidators {

    /**
     * Password validation regex
     */
    private const val PASSWORD_VALIDATOR = "^(?=.*[0-9])(?=.*[A-Za-z])(?=\\S+\$).{8,}\$"
    private const val PASSWORD_ERROR_HTML = "Password must have <ul>" +
            "<li> at least 8 characters without blank space</li>" +
            "<li> an alphabet</li>" +
            "<li> a number</li> </ul>"
    private const val PASSWORD_ERROR_TEXT =
        "Password must have at least 8 characters, an alphabet and a number"
    private const val EMAIL_ERROR_TEXT = "Please enter a valid email address!"
    private const val CARD_ERROR_TEXT = "Enter valid card number"
    private const val EXPIRY_MONTH_ERROR = "Enter valid month number"
    private const val EXPIRY_YEAR_ERROR = "Enter valid year number"
    private const val CVV_ERROR = "Enter valid cvv number"

    /**
     * Checks if the email entered is valid or not
     * @param mailText The edit text of the entered email
     * @return true if mail is valid, else false
     */
    fun checkEmail(mailText: EditText): Boolean {
        val email = mailText.text.toString()
        var error = true
        if (email.isEmpty()) {
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
        if (password.isEmpty()) {
            passwordText.error = "Please enter password!"
            error = false
        } else if (!pattern.matcher(password).matches()) {
            passwordText.error = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) Html.fromHtml(
                PASSWORD_ERROR_HTML,
                Html.FROM_HTML_MODE_COMPACT
            ) else PASSWORD_ERROR_TEXT
            error = false
        }
        if (error)
            passwordText.requestFocus()
        return error
    }

    fun checkEmpty(editText: EditText, errorMsg: String): Boolean {
        return if (editText.text.isEmpty()) {
            editText.error = errorMsg
            editText.requestFocus()
            false
        } else true
    }

    private fun checkText(editText: EditText, predicate: Boolean, error: String): Boolean {
        return if (predicate) {
            editText.error = error
            editText.requestFocus()
            false
        } else true
    }

    fun checkMonth(monthText: EditText) =
        checkText(
            monthText,
            !(monthText.text.isNotEmpty() && Integer.valueOf(monthText.text.toString()) <= 12),
            EXPIRY_MONTH_ERROR
        )

    fun checkYear(yearText: EditText) =
        checkText(yearText, yearText.text.length < 4, EXPIRY_YEAR_ERROR)

    fun checkCard(cardText: EditText) =
        checkText(cardText, cardText.text.length < 19, CARD_ERROR_TEXT)

    fun checkCvv(cvvText: EditText) =
        checkText(cvvText, cvvText.text.length < 3, CVV_ERROR)
}