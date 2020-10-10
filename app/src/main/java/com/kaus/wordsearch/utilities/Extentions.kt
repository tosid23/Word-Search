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
import java.util.*

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


