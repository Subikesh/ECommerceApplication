package com.example.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


abstract class BaseUseCase<Data, Param> {
    suspend fun execute(param: Param): Result<Data> = withContext(Dispatchers.IO) {
        try {
            executeOnBackground(param)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    protected abstract suspend fun executeOnBackground(param: Param): Result<Data>
}