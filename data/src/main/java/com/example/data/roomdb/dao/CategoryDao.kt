package com.example.data.roomdb.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.data.roomdb.entities.Category

@Dao
interface CategoryDao : BaseDao<Category> {
    /**
     * Select all Categories
     * @return List of categories selected
     */
    @Query("SELECT * FROM category")
    fun getAll(): List<Category>

    @Query("SELECT * FROM category WHERE categoryId = :categoryId")
    fun get(categoryId: String): Category
}