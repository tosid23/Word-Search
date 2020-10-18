package com.kaus.wordsearch.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "puzzle_data_table")
@Parcelize
data class PuzzleData(
    @PrimaryKey val id: Int,
    val title: String,
    val language: String,
    val image: String,
    val level: Int,
    val wordsList: List<Word>,
    var is_completed: Boolean
) : Parcelable