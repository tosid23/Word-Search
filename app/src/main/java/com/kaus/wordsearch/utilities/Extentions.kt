package com.kaus.wordsearch.utilities

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.View.*
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.kaus.wordsearch.widgets.others.GridIndex
import java.util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun Int.px2dp() = (this / Resources.getSystem().displayMetrics.density).toInt()
fun Int.dp2px() = (this * Resources.getSystem().displayMetrics.density).toInt()
fun Boolean.toInt() = if (this) 1 else 0
fun String.capitalizeWords(): String =
    split(" ").joinToString(" ") { it.capitalize(Locale.getDefault()) }


fun View?.show() {
    this?.visibility = VISIBLE
}

fun View?.hide(shouldBeGone: Boolean = true) {
    this?.visibility = if (shouldBeGone) GONE else INVISIBLE
}

fun ImageView?.changeImageViewTint(context: Context, color: Int) {
    this?.setColorFilter(
        ContextCompat.getColor(context, color),
        android.graphics.PorterDuff.Mode.MULTIPLY
    )
}

fun View?.changeBackgroundDrawable(context: Context, drawable: Int) {
    this?.background = ContextCompat.getDrawable(context, drawable)
}

fun TextView?.changeTextColor(context: Context, color: Int) {
    this?.setTextColor(ContextCompat.getColor(context, color))
}

fun Context?.hideKeyboard(view: View) {
    this?.let {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun getIndexLength(start: GridIndex, end: GridIndex): Int {
    val x: Int = abs(start.col - end.col)
    val y: Int = abs(start.row - end.row)
    return max(x, y) + 1
}

fun getReverseString(str: String): String? {
    val out = StringBuilder()
    for (i in str.length - 1 downTo 0) out.append(str[i])
    return out.toString()
}

fun <T> randomizeList(list: MutableList<T>) {
    val count = list.size
    for (i in 0 until count) {
        val randIdx: Int = getRandomIntRange(
            min(i + 1, count - 1),
            count - 1
        )
        val temp = list[randIdx]
        list[randIdx] = list[i]
        list[i] = temp
    }
}

fun getRandomIntRange(min: Int, max: Int): Int {
    return min + getRandomInt() % (max - min + 1)
}

fun getRandomInt(): Int {
    return abs(sRand.nextInt())
}

private val sRand = Random()

const val NULL_CHAR = '\u0000'

fun getRandomChar(): Char {
    // ASCII A = 65 - Z = 90
    return getRandomIntRange(65, 90).toChar()
}

fun fillNullCharWidthRandom(gridArr: Array<CharArray>) {
    for (i in gridArr.indices) {
        for (j in gridArr[i].indices) {
            if (gridArr[i][j] == NULL_CHAR) gridArr[i][j] =
                getRandomChar()
        }
    }
}


