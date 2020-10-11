package com.kaus.wordsearch.model

data class UsedWord(
    var answerLine: AnswerLine,
    var isAnswered: Boolean,
    var isMystery: Boolean,
    var revealCount: Int,
    var word: Word
)