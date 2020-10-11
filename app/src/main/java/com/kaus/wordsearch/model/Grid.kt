package com.kaus.wordsearch.model

class Grid {
    private var mArray: Array<CharArray>

    constructor(grid: Array<CharArray>) {
        mArray = grid
    }

    constructor(rowCount: Int, colCount: Int) {
        require(!(rowCount <= 0 || colCount <= 0)) { "Row and column should be greater than 0" }
        mArray = Array(rowCount) { CharArray(colCount) }
        reset()
    }

    fun getRowCount(): Int {
        return mArray.size
    }

    fun getColCount(): Int {
        return mArray[0].size
    }

    fun getArray(): Array<CharArray> {
        return mArray
    }

    fun at(row: Int, col: Int): Char {
        return mArray[row][col]
    }

    fun setAt(row: Int, col: Int, c: Char) {
        mArray[row][col] = c
    }

    fun reset() {
        for (i in mArray.indices) {
            for (j in mArray[i].indices) {
                mArray[i][j] = NULL_CHAR
            }
        }
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        for (i in 0 until getRowCount()) {
            for (j in 0 until getColCount()) {
                stringBuilder.append(at(i, j))
            }
            if (i != getRowCount() - 1) stringBuilder.append(GRID_NEWLINE_SEPARATOR)
        }
        return stringBuilder.toString()
    }

    companion object {
        const val GRID_NEWLINE_SEPARATOR = ','
        const val NULL_CHAR = '\u0000'
    }
}