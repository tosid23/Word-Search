package com.kaus.wordsearch.model


import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class PuzzleListData(
    @SerializedName("puzzleList") var puzzleList: List<PuzzleData>
) : Parcelable