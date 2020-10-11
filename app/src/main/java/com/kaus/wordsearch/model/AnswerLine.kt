package com.kaus.wordsearch.model

class AnswerLine @JvmOverloads constructor(
    var startRow: Int = 0,
    var startCol: Int = 0,
    var endRow: Int = 0,
    var endCol: Int = 0,
    var color: Int = 0
) {
    override fun toString(): String {
        return "$startRow,$startCol:$endRow,$endCol"
    }

    fun fromString(string: String?) {
        /*
            Expected format string = 1,1:6,6
         */
        if (string == null) return
        val split = string.split(":", limit = 2).toTypedArray()
        if (split.size >= 2) {
            val start = split[0].split(",", limit = 2).toTypedArray()
            val end = split[1].split(",", limit = 2).toTypedArray()
            if (start.size >= 2 && end.size >= 2) {
                startRow = start[0].toInt()
                startCol = start[1].toInt()
                endRow = end[0].toInt()
                endCol = end[1].toInt()
            }
        }
    }
}