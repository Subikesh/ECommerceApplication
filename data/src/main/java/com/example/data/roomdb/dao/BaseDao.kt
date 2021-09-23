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
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(obj: T): Long

    /**
     * Insert list of objects entries
     * @param objects list of objects to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(vararg objects: T): List<Long>

    /**
     * Delete an object entry from the table
     * @param obj object entry to be deleted
     */
    @Delete
    suspend fun delete(obj: T)
}