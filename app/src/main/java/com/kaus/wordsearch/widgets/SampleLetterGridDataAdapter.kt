package com.kaus.wordsearch.widgets

/**
 * Sample data adapter (for preview in android studio visual editor)
 */
internal class SampleLetterGridDataAdapter(private val mRowCount: Int, private val mColCount: Int) :
    LetterGridDataAdapter() {
    override val colCount: Int
        get() = mColCount

    override val rowCount: Int
        get() = mRowCount

    override fun getLetter(row: Int, col: Int): Char {
        return 'A'
    }
}