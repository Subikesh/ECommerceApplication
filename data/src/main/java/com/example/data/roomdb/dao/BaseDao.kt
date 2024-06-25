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
    fun insert(obj: T): Long

    /**
     * Delete an object entry from the table
     * @param obj object entry to be deleted
     */
    @Delete
    fun delete(obj: T)
}