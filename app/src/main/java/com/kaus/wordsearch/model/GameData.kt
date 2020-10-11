package com.kaus.wordsearch.model

import com.kaus.wordsearch.utilities.getReverseString
import java.util.*

class GameData @JvmOverloads constructor(
    var id: Int = 0,
    var name: String = "",
    var duration: Int = 0,
    var grid: Grid? = null,
    private val mUsedWords: MutableList<UsedWord> = ArrayList()
) {
    val usedWords: List<UsedWord>
        get() = mUsedWords

    fun markWordAsAnswered(
        word: String,
        answerLine: AnswerLine,
        enableReverse: Boolean
    ): UsedWord? {
        val answerStrRev = getReverseString(word)
        for (usedWord in mUsedWords) {
            if (usedWord.isAnswered) continue
            val currUsedWord = usedWord.word.word
            if (currUsedWord.equals(word, ignoreCase = true) ||
                currUsedWord.equals(answerStrRev, ignoreCase = true) && enableReverse
            ) {
                usedWord.isAnswered = true
                usedWord.word.is_answered = true
                usedWord.answerLine = answerLine
                return usedWord
            }
        }
        return null
    }

    val answeredWordsCount: Int
        get() {
            var count = 0
            for (uw in mUsedWords) {
                if (uw.isAnswered) count++
            }
            return count
        }
    val isFinished: Boolean
        get() = answeredWordsCount == mUsedWords.size

    fun addUsedWord(usedWord: UsedWord) {
        mUsedWords.add(usedWord)
    }

    fun addUsedWords(usedWords: List<UsedWord>?) {
        mUsedWords.addAll(usedWords!!)
    }
}