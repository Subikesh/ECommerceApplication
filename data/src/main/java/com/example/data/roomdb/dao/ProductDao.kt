package com.example.data.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.roomdb.entities.Product

@Dao
interface ProductDao : BaseDao<Product> {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    override suspend fun insert(obj: Product): Long

    @Query("SELECT * FROM product WHERE productId = :productId")
    suspend fun getProduct(productId: String): Product

    /**
     * Find categories and products for the given search query
     * Searches product title, brand and category title
     * @param query Search string given by user
     */
    @Query("SELECT p.* FROM product p, category c WHERE c.categoryId = p.categoryId AND (LOWER(p.title) LIKE '%' || :query || '%' OR LOWER(p.brand) LIKE '%' || :query || '%' OR LOWER(c.title) LIKE '%' || :query || '%')")
    suspend fun searchProducts(query: String): List<Product>
}