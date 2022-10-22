package com.example.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


abstract class BaseUseCase<Data, Param> {
    suspend fun execute(param: Param): Result<Data> = withContext(Dispatchers.IO) {
        executeOnBackground(param)
    }

    abstract suspend fun executeOnBackground(param: Param): Result<Data>
}