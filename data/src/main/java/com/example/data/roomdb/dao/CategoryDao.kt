package com.example.data.roomdb.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.data.roomdb.entities.Category
import com.example.data.roomdb.entities.Product
import com.example.data.roomdb.relations.CategoryWithProducts

@Dao
interface CategoryDao : BaseDao<Category> {
    /**
     * Select all Categories
     * @return List of categories selected
     */
    @Query("SELECT * FROM category")
    suspend fun getAll(): List<Category>

//    /**
//     * Find products for the given search query
//     * @param query Search string given by user
//     */
//    @Query("SELECT * FROM category WHERE LOWER(title) LIKE '%' || :query || '%'")
//    suspend fun searchCategories(query: String): List<CategoryWithProducts>

    @Query("SELECT * FROM category WHERE categoryId = :categoryId")
    suspend fun get(categoryId: String): Category
}