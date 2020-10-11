package com.kaus.wordsearch.features.puzzle.gameplay

import com.kaus.wordsearch.model.AnswerLine
import com.kaus.wordsearch.widgets.StreakView


class StreakLineMapper : Mapper<AnswerLine, StreakView.StreakLine>() {
    override fun map(obj: AnswerLine): StreakView.StreakLine {
        val s = StreakView.StreakLine()
        s.startIndex[obj.startRow] = obj.startCol
        s.endIndex[obj.endRow] = obj.endCol
        s.color = obj.color
        return s
    }

    override fun revMap(obj: StreakView.StreakLine): AnswerLine {
        val a = AnswerLine()
        a.startRow = obj.startIndex.row
        a.startCol = obj.startIndex.col
        a.endRow = obj.endIndex.row
        a.endCol = obj.endIndex.col
        a.color = obj.color
        return a
    }
}