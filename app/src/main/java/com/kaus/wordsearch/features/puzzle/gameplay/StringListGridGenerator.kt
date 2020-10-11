package com.kaus.wordsearch.features.puzzle.gameplay

import com.kaus.wordsearch.utilities.NULL_CHAR
import com.kaus.wordsearch.utilities.fillNullCharWidthRandom
import com.kaus.wordsearch.utilities.getRandomInt
import com.kaus.wordsearch.widgets.others.Direction
import java.util.*

class StringListGridGenerator : GridGenerator<List<String>, List<String>>() {

    private val randomDirection: Direction
        get() {
            var dir: Direction
            do {
                dir = Direction.values()[getRandomInt() % Direction.values().size]
            } while (dir == Direction.NONE)
            return dir
        }

    private fun tryPlaceWord(word: String, gridArr: Array<CharArray>): Boolean {
        val startDir = randomDirection
        var currDir = startDir
        var row: Int
        var col: Int
        var startRow: Int
        var startCol: Int

        do {
            startRow = getRandomInt() % gridArr.size
            row = startRow
            do {
                startCol = getRandomInt() % gridArr[0].size
                col = startCol
                do {
                    if (isValidPlacement(row, col, currDir, gridArr, word)) {
                        placeWordAt(row, col, currDir, gridArr, word)
                        return true
                    }
                    col = ++col % gridArr[0].size
                } while (col != startCol)
                row = ++row % gridArr.size
            } while (row != startRow)
            currDir = currDir.nextDirection()
        } while (currDir != startDir)
        return false
    }

    /**
     *
     * @param col starting column
     * @param row starting row
     * @param dir direction of the word
     * @param gridArr grid where the word will be placed
     * @param word the actual word to be checked
     * @return true if it is a valid placement, false otherwise
     */
    @Suppress("NAME_SHADOWING")
    private fun isValidPlacement(
        row: Int,
        col: Int,
        dir: Direction,
        gridArr: Array<CharArray>,
        word: String
    ): Boolean {
        var row = row
        var col = col
        val wLen = word.length
        if (dir == Direction.EAST && col + wLen >= gridArr[0].size) return false
        if (dir == Direction.WEST && col - wLen < 0) return false
        if (dir == Direction.NORTH && row - wLen < 0) return false
        if (dir == Direction.SOUTH && row + wLen >= gridArr.size) return false
        if (dir == Direction.SOUTH_EAST && (col + wLen >= gridArr[0].size || row + wLen >= gridArr.size)) return false
        if (dir == Direction.NORTH_WEST && (col - wLen < 0 || row - wLen < 0)) return false
        if (dir == Direction.SOUTH_WEST && (col - wLen < 0 || row + wLen >= gridArr.size)) return false
        if (dir == Direction.NORTH_EAST && (col + wLen >= gridArr[0].size || row - wLen < 0)) return false
        for (i in 0 until wLen) {
            if (gridArr[row][col] != NULL_CHAR && gridArr[row][col] != word[i]) return false
            col += dir.xOff
            row += dir.yOff
        }
        return true
    }

    @Suppress("NAME_SHADOWING")
    private fun placeWordAt(
        row: Int,
        col: Int,
        dir: Direction,
        gridArr: Array<CharArray>,
        word: String
    ) {
        var row = row
        var col = col
        for (element in word) {
            gridArr[row][col] = element
            col += dir.xOff
            row += dir.yOff
        }
    }

    companion object {
        private const val MIN_GRID_ITERATION_ATTEMPT = 1
    }

    override fun setGrid(dataInput: List<String>, grid: Array<CharArray>): List<String> {
        val usedStrings: MutableList<String> = ArrayList()
        var usedCount: Int
        for (i in 0 until MIN_GRID_ITERATION_ATTEMPT) {
            usedCount = 0
            usedStrings.clear()
            resetGrid(grid)
            for (word in dataInput) {
                if (tryPlaceWord(word, grid)) {
                    usedCount++
                    usedStrings.add(word)
                }
            }
            if (usedCount >= dataInput.size) break
        }
        fillNullCharWidthRandom(grid)
        return usedStrings
    }
}