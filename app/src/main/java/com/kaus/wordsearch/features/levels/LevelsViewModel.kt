package com.kaus.wordsearch.features.levels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kaus.wordsearch.model.PuzzleData
import com.kaus.wordsearch.utilities.base_classes.BaseViewModel
import com.kaus.wordsearch.utilities.room.RoomDb
import kotlinx.coroutines.launch

class LevelsViewModel : BaseViewModel() {

    val puzzleLiveData: MutableLiveData<List<PuzzleData>> = MutableLiveData()

    fun getPuzzleData(language: String) {
        viewModelScope.launch {
            val puzzlesList = RoomDb.getInstance().puzzleDataDao().getAllPuzzlesByLanguage(language)
            puzzlesList?.let { puzzleLiveData.postValue(it) }
        }
    }
}