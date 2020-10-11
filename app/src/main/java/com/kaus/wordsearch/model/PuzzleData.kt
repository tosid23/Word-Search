package com.kaus.wordsearch.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "puzzle_data_table")
data class PuzzleData(
    @PrimaryKey val id: Int,
    val title: String,
    val language: String,
    val image: Int,
    val level: Int,
    val wordsList: List<Word>,
    val is_completed: Boolean
)