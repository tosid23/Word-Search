package com.kaus.wordsearch.features.completed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kaus.wordsearch.model.PuzzleData
import com.kaus.wordsearch.utilities.base_classes.BaseViewModel
import com.kaus.wordsearch.utilities.room.RoomDb
import kotlinx.coroutines.launch

class CompletedViewModel : BaseViewModel() {

    val puzzleLiveData: MutableLiveData<List<PuzzleData>> = MutableLiveData()

    fun getData(puzzleId: Int) {
        viewModelScope.launch {

            val list: ArrayList<PuzzleData> = ArrayList()

            val puzzleData = RoomDb.getInstance().puzzleDataDao().getPuzzleById(puzzleId)
            puzzleData?.let {
                list.add(it)
            }
            val nextPuzzleData = RoomDb.getInstance().puzzleDataDao().getPuzzleById(puzzleId + 1)
            nextPuzzleData?.let {
                list.add(it)
            }
            puzzleLiveData.postValue(list)

        }
    }

}