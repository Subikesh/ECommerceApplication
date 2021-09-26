package com.example.data.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.roomdb.entities.Product

@Dao
interface ProductDao : BaseDao<Product> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insert(obj: Product): Long

    @Query("SELECT * FROM product WHERE productId = :productId")
    suspend fun getProduct(productId: String): Product
}