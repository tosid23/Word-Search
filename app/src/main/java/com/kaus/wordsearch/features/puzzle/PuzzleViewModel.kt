package com.kaus.wordsearch.features.puzzle

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kaus.wordsearch.model.AnswerLine
import com.kaus.wordsearch.model.GameData
import com.kaus.wordsearch.model.UsedWord
import com.kaus.wordsearch.model.Word
import com.kaus.wordsearch.utilities.base_classes.BaseViewModel
import com.kaus.wordsearch.utilities.gameplay.GameDataCreator
import com.kaus.wordsearch.utilities.room.RoomDb
import kotlinx.coroutines.launch

class PuzzleViewModel : BaseViewModel() {

    var gameDataCreator = GameDataCreator()
    var gameLiveData: MutableLiveData<GameData> = MutableLiveData()
    var answeredWordLiveData: MutableLiveData<UsedWord> = MutableLiveData()
    var isGameOverLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var wordDetailsLiveData: MutableLiveData<Word> = MutableLiveData()

    fun createGame(puzzleId: Int) {
        viewModelScope.launch {

            val puzzleData = RoomDb.getInstance().puzzleDataDao().getPuzzleById(puzzleId)
            puzzleData?.let {
                val gameData = gameDataCreator.newGameData(
                    colCount = 12,
                    rowCount = 12,
                    name = it.title,
                    words = it.wordsList.toMutableList(),
                    image = it.image
                )
                gameLiveData.postValue(gameData)
            }
        }
    }

    fun getWordDataFromDb(puzzleId: Int, searchWord: String) {
        viewModelScope.launch {
            val puzzle = RoomDb.getInstance().puzzleDataDao().getPuzzleById(puzzleId)
            puzzle?.let { pd ->
                pd.wordsList.forEach { word ->
                    if (word.word == searchWord) {
                        wordDetailsLiveData.postValue(word)
                        return@launch
                    }
                }
            }
        }
    }

    fun answerWord(
        answerStr: String,
        answerLine: AnswerLine,
        reverseMatching: Boolean,
        puzzleId: Int
    ) {
        viewModelScope.launch {
            val correctWord: UsedWord? =
                gameLiveData.value?.markWordAsAnswered(answerStr, answerLine, reverseMatching)
            answeredWordLiveData.postValue(correctWord)
            val correct = correctWord != null
            val isFinished = gameLiveData.value?.isFinished
            if (correct) {
                isFinished?.let { finished ->
                    if (finished) {
                        val puzzleData =
                            RoomDb.getInstance().puzzleDataDao().getPuzzleById(puzzleId)
                        puzzleData?.let { pd ->
                            pd.is_completed = true
                            RoomDb.getInstance().puzzleDataDao().updateData(pd)
                        }
                        isGameOverLiveData.postValue(true)
                    }
                }
            }
        }
    }
}