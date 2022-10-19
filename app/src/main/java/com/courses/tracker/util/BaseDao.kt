package com.courses.tracker.util

import androidx.room.*

@Dao
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: T)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(entities: List<T>)
    @Update
    suspend fun update(entity: T)
    @Delete
    suspend fun delete(entity: T)
}