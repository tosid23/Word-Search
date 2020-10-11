package com.kaus.wordsearch.utilities.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.kaus.wordsearch.model.PuzzleData
import com.kaus.wordsearch.utilities.base_classes.BaseDao

@Dao
interface PuzzleDataDao : BaseDao<PuzzleData> {

    @Query("SELECT * from puzzle_data_table WHERE language = :language")
    suspend fun getAllPuzzlesByLanguage(language: String): List<PuzzleData>?

    @Query("SELECT * from puzzle_data_table WHERE id = :id")
    suspend fun getPuzzleById(id: Int): PuzzleData?
}