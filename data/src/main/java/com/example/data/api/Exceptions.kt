package com.example.data.api

class EmptyBodyException(message: String, throwable: Throwable? = null) :
    Exception(message, throwable)

class NoNetworkException(message: String? = null, throwable: Throwable? = null) :
    Exception(message, throwable)

class NoDataFetchedFromDBException(
    message: String? = null,
    throwable: Throwable? = null,
    tableName: String? = null
) : Exception(message ?: "No $tableName fetched from database", throwable)