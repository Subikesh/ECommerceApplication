package com.example.data.repository.base

import com.example.data.api.EmptyBodyException
import com.example.data.api.NoNetworkException
import retrofit2.Response
import java.net.SocketTimeoutException

open class BaseRepository {

    suspend fun <T> executeNetworkService(execute: suspend () -> Response<T>): Result<T> {
        return try {
            val apiResult = execute()
            if (apiResult.isSuccessful) {
                val body = apiResult.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(EmptyBodyException("Response body empty!"))
                }
            } else {
                Result.failure(Exception(apiResult.message() ?: "Unknown error occurred in API response."))
            }
        } catch (socketException: SocketTimeoutException) {
            Result.failure(socketException)
        } catch (exception: Exception) {
            exception.printStackTrace()
            Result.failure(NoNetworkException(throwable = exception))
        }
    }
}