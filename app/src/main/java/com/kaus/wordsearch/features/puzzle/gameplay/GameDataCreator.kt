package com.kaus.wordsearch.features.puzzle.gameplay

import com.kaus.wordsearch.model.*
import com.kaus.wordsearch.utilities.getRandomIntRange
import com.kaus.wordsearch.utilities.randomizeList
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.min

class GameDataCreator {
    fun newGameData(
        words: MutableList<Word>,
        rowCount: Int, colCount: Int,
        name: String?
    ): GameData {
        val gameData = GameData()
        randomizeList(words)
        val grid = Grid(rowCount, colCount)
        val maxCharCount = min(rowCount, colCount)
        val usedStrings = StringListGridGenerator()
            .setGrid(getStringListFromWord(words, 100, maxCharCount), grid.getArray())
        gameData.addUsedWords(buildUsedWordFromString(usedStrings))
        gameData.grid = grid
        if (name == null || name.isEmpty()) {
            val name1 = "Puzzle " +
                    SimpleDateFormat("HH.mm.ss", Locale.ENGLISH)
                        .format(Date(System.currentTimeMillis()))
            gameData.name = name1
        } else {
            gameData.name = name
        }
        return gameData
    }

    private fun buildUsedWordFromString(strings: List<String>): List<UsedWord> {
        var mysteryWordCount = getRandomIntRange(strings.size / 2, strings.size)
        val usedWords: MutableList<UsedWord> = ArrayList()
        for (i in strings.indices) {
            var isMystery = false
            var revealCount = 0
            if (mysteryWordCount > 0) {
                isMystery = true
                revealCount = getRandomIntRange(0, strings[i].length - 1)
                mysteryWordCount--
            }
            val uw = UsedWord(
                word = Word(-1, strings[i], false),
                answerLine = AnswerLine(),
                isAnswered = false,
                isMystery = isMystery,
                revealCount = revealCount
            )
            usedWords.add(uw)
        }
        randomizeList(usedWords)
        return usedWords
    }

    @Suppress("NAME_SHADOWING")
    private fun getStringListFromWord(
        words: List<Word>,
        count: Int,
        maxCharCount: Int
    ): List<String> {
        var count = count
        count = min(count, words.size)
        val stringList: MutableList<String> = ArrayList()
        var temp: String
        for (i in words.indices) {
            if (stringList.size >= count) break
            temp = words[i].word
            if (temp.length <= maxCharCount) {
                stringList.add(temp)
            }
        }
        return stringList
    }
}