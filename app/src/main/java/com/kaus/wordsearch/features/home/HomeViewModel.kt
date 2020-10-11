package com.kaus.wordsearch.features.home

import androidx.lifecycle.viewModelScope
import com.kaus.wordsearch.R
import com.kaus.wordsearch.model.PuzzleData
import com.kaus.wordsearch.model.Word
import com.kaus.wordsearch.utilities.LANGUAGE_ENGLISH
import com.kaus.wordsearch.utilities.base_classes.BaseViewModel
import com.kaus.wordsearch.utilities.room.RoomDb
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {

    fun createPuzzleData() {
        viewModelScope.launch {
            val theKingsEnWordList = ArrayList<Word>()
            theKingsEnWordList.add(Word(id = 11, word = "RAM", is_answered = false))
            theKingsEnWordList.add(Word(id = 12, word = "LAKSHMAN", is_answered = false))
            theKingsEnWordList.add(Word(id = 13, word = "BHARAT", is_answered = false))
            theKingsEnWordList.add(Word(id = 14, word = "JANAK", is_answered = false))
            theKingsEnWordList.add(Word(id = 15, word = "RAVANA", is_answered = false))
            theKingsEnWordList.add(Word(id = 16, word = "NAKUL", is_answered = false))
            val savedKingsEn = RoomDb.getInstance().puzzleDataDao().getPuzzleById(1)
            var isKingsDone = false
            savedKingsEn?.let { a -> isKingsDone = a.is_completed }
            val theKingsEn = PuzzleData(
                id = 1, level = 1, title = "The Kings!", language = LANGUAGE_ENGLISH,
                wordsList = theKingsEnWordList, is_completed = isKingsDone, image = R.drawable.kings
            )
            RoomDb.getInstance().puzzleDataDao().insertData(theKingsEn)

            val theQueensEnWordList = ArrayList<Word>()
            theQueensEnWordList.add(Word(id = 21, word = "SITA", is_answered = false))
            theQueensEnWordList.add(Word(id = 22, word = "KAUSALYA", is_answered = false))
            theQueensEnWordList.add(Word(id = 23, word = "SUMITRA", is_answered = false))
            theQueensEnWordList.add(Word(id = 24, word = "KAIKAYI", is_answered = false))
            theQueensEnWordList.add(Word(id = 25, word = "MANDAVI", is_answered = false))
            theQueensEnWordList.add(Word(id = 26, word = "MANDODARI", is_answered = false))
            val savedQueensEn = RoomDb.getInstance().puzzleDataDao().getPuzzleById(1)
            var isQueensDone = false
            savedQueensEn?.let { a -> isQueensDone = a.is_completed }
            val theQueensEn = PuzzleData(
                id = 2,
                level = 2,
                title = "The Queens!",
                language = LANGUAGE_ENGLISH,
                wordsList = theQueensEnWordList,
                is_completed = isQueensDone,
                image = R.drawable.queens
            )
            RoomDb.getInstance().puzzleDataDao().insertData(theQueensEn)

            val theAsurasEnWordList = ArrayList<Word>()
            theAsurasEnWordList.add(Word(id = 31, word = "RAVANA", is_answered = false))
            theAsurasEnWordList.add(Word(id = 32, word = "INDRAJIT", is_answered = false))
            theAsurasEnWordList.add(Word(id = 33, word = "MAARICH", is_answered = false))
            theAsurasEnWordList.add(Word(id = 34, word = "BAALI", is_answered = false))
            theAsurasEnWordList.add(Word(id = 35, word = "VIRADHA", is_answered = false))
            theAsurasEnWordList.add(Word(id = 36, word = "TATAKA", is_answered = false))
            val savedAsurasEn = RoomDb.getInstance().puzzleDataDao().getPuzzleById(1)
            var isAsurasDone = false
            savedAsurasEn?.let { a -> isAsurasDone = a.is_completed }
            val theAsurasEn = PuzzleData(
                id = 3,
                title = "The Asuras!",
                language = LANGUAGE_ENGLISH,
                wordsList = theAsurasEnWordList,
                level = 3,
                is_completed = isAsurasDone,
                image = R.drawable.asuras
            )
            RoomDb.getInstance().puzzleDataDao().insertData(theAsurasEn)

            val theRishisEnWordList = ArrayList<Word>()
            theRishisEnWordList.add(Word(id = 41, word = "AGASTYA", is_answered = false))
            theRishisEnWordList.add(Word(id = 42, word = "ATRI", is_answered = false))
            theRishisEnWordList.add(Word(id = 43, word = "VAKMIKI", is_answered = false))
            theRishisEnWordList.add(Word(id = 44, word = "VISHWAMITRA", is_answered = false))
            theRishisEnWordList.add(Word(id = 45, word = "VASHISTHA", is_answered = false))
            theRishisEnWordList.add(Word(id = 46, word = "ANASUYA", is_answered = false))
            val savedRishisEn = RoomDb.getInstance().puzzleDataDao().getPuzzleById(1)
            var isRishisDone = false
            savedRishisEn?.let { a -> isRishisDone = a.is_completed }
            val theRishisEn = PuzzleData(
                id = 4,
                title = "The Rishis!",
                language = LANGUAGE_ENGLISH,
                wordsList = theRishisEnWordList,
                level = 4,
                is_completed = isRishisDone,
                image = R.drawable.rishis
            )
            RoomDb.getInstance().puzzleDataDao().insertData(theRishisEn)

        }
    }
}