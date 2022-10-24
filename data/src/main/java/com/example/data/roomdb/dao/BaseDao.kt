package com.example.data.roomdb.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface BaseDao<T> {
    /**
     * Insert single object to table
     * @param obj Object to be inserted
     * @return success or error code if some conflict occurs
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obj: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(objList: List<T>)

    /**
     * Delete an object entry from the table
     * @param obj object entry to be deleted
     */
    @Delete
    suspend fun delete(obj: T)
}