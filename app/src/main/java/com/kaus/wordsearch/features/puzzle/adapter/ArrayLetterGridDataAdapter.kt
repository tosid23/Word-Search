package com.kaus.wordsearch.features.puzzle.adapter

import com.kaus.wordsearch.widgets.LetterGridDataAdapter


/**
 * Created by abdularis on 09/07/17.
 */
class ArrayLetterGridDataAdapter internal constructor(private var mGrid: Array<CharArray>) :
    LetterGridDataAdapter() {

    var grid: Array<CharArray>?
        get() = mGrid
        set(grid) {
            if (grid != null && !grid.contentEquals(mGrid)) {
                mGrid = grid
                setChanged()
                notifyObservers()
            }
        }

    override val colCount: Int
        get() = mGrid[0].size
    override val rowCount: Int
        get() = mGrid.size

    override fun getLetter(row: Int, col: Int): Char {
        return mGrid[row][col]
    }
}