package com.kaus.wordsearch.features.puzzle

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kaus.wordsearch.features.puzzle.gameplay.GameDataCreator
import com.kaus.wordsearch.model.AnswerLine
import com.kaus.wordsearch.model.GameData
import com.kaus.wordsearch.model.UsedWord
import com.kaus.wordsearch.utilities.base_classes.BaseViewModel
import com.kaus.wordsearch.utilities.room.RoomDb
import kotlinx.coroutines.launch

class PuzzleViewModel : BaseViewModel() {

    var gameDataCreator = GameDataCreator()
    var gameLiveData: MutableLiveData<GameData> = MutableLiveData()
    var answeredWordLiveData: MutableLiveData<UsedWord> = MutableLiveData()

    fun createGame(puzzleId: Int) {
        viewModelScope.launch {

            val puzzleData = RoomDb.getInstance().puzzleDataDao().getPuzzleById(puzzleId)
            puzzleData?.let {
                val gameData = gameDataCreator.newGameData(
                    colCount = 12,
                    rowCount = 12,
                    name = it.title,
                    words = it.wordsList.toMutableList()
                )
                gameLiveData.postValue(gameData)
            }
        }
    }

    fun answerWord(answerStr: String, answerLine: AnswerLine, reverseMatching: Boolean) {
        val correctWord: UsedWord? =
            gameLiveData.value?.markWordAsAnswered(answerStr, answerLine, reverseMatching)
        answeredWordLiveData.postValue(correctWord)
        //val correct = correctWord != null
//        if (correct) {
////            //mGameDataSource.markWordAsAnswered(correctWord)
////
////        }
    }
}