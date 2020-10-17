package com.kaus.wordsearch.utilities.base_classes

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(vararg t: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataList(t: List<T>)

    @Update
    suspend fun updateData(vararg t: T)

    @Delete
    suspend fun deleteData(vararg t: T)
}