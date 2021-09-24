package com.example.data.roomdb.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.data.roomdb.entities.Category
import com.example.data.roomdb.relations.CategoryWithProducts

@Dao
interface CategoryDao : BaseDao<Category> {
    /**
     * Select all Categories
     * @return List of categories selected
     */
    @Query("SELECT * FROM category")
    suspend fun getAll(): List<Category>

    /**
     * Select all products which are under specified category
     * @param categoryId Id of category for which products are to be selected
     * @return List of Category-Product relation objects
     */
    @Transaction
    @Query("SELECT * FROM category WHERE categoryId=:categoryId")
    suspend fun getProducts(categoryId: String): List<CategoryWithProducts>
}