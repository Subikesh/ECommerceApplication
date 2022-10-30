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

    @Query("SELECT c.* FROM category c LEFT JOIN product p ON c.categoryId = p.categoryId GROUP BY c.categoryId HAVING COUNT(p.productId) >= :minProducts")
    suspend fun getCategoriesWithMinProducts(minProducts: Int): List<Category>

    @Query("SELECT * FROM category WHERE categoryId = :categoryId")
    suspend fun get(categoryId: String): Category
}