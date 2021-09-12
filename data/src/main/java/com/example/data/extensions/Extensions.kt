package com.example.data.extensions

/**
 * Converts string from slug to string
 * Input is given as slug string format Ex: first_string
 * @return String separated with spaces Ex: First String
 */
fun String.fromSlug(): String {
    val words = this.split("_")
    return words.joinToString(" ") { it.replaceFirstChar(Char::uppercase) }
}