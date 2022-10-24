package com.example.domain.usecase

import com.example.domain.models.Category
import com.example.domain.repository.CategoryRepository
import javax.inject.Inject

class GetAndSaveCategoriesUseCase @Inject constructor(private val repository: CategoryRepository) :
    BaseUseCase<List<Category>, GetAndSaveCategoriesUseCase.ReqParams>() {

    override suspend fun executeOnBackground(param: ReqParams): Result<List<Category>> {
        return repository.fetchAndSaveCategories(param.forceReload)
    }

    class ReqParams(val forceReload: Boolean = false, val fromCount: Int = 0, val size: Int = 50)
}