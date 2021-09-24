package com.example.data.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.data.roomdb.entities.Product

@Dao
interface ProductDao : BaseDao<Product> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insert(obj: Product): Long
}