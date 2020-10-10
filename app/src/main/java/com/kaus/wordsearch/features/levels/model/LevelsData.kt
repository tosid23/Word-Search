package com.kaus.wordsearch.features.levels.model

data class LevelsData(
    val id: Int,
    val name: String,
    val image: Int,
    val level: Int,
    val is_completed: Boolean
)