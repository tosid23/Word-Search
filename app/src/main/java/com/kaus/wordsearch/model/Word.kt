package com.kaus.wordsearch.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Word(
    var id: Int,
    var word: String,
    var is_answered: Boolean,
    var description: String
) : Parcelable