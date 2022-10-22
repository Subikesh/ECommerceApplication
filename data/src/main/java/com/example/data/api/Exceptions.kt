package com.example.data.api

class EmptyBodyException(message: String, throwable: Throwable? = null): Exception(message, throwable)

class NoNetworkException(message: String? = null, throwable: Throwable? = null): Exception(message, throwable)