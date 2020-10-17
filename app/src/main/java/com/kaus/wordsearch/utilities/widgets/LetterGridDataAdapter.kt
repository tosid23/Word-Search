package com.kaus.wordsearch.utilities.widgets

import java.util.*

/**
 * Created by abdularis on 26/06/17.
 */
abstract class LetterGridDataAdapter : Observable() {
    abstract val colCount: Int
    abstract val rowCount: Int
    abstract fun getLetter(row: Int, col: Int): Char
}