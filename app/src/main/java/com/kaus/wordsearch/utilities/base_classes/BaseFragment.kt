package com.kaus.wordsearch.utilities.base_classes

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.util.*

open class BaseFragment : Fragment() {

    val Int.px2dp: Int get() = (this / Resources.getSystem().displayMetrics.density).toInt()
    val Int.dp2px: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()
    fun Boolean.toInt() = if (this) 1 else 0

    protected fun hideView(vararg views: View) {
        for (view in views) {
            view.visibility = View.GONE
        }
    }

    protected fun softHideView(vararg views: View) {
        for (view in views) {
            view.visibility = View.INVISIBLE
        }
    }

    protected fun showView(vararg views: View) {
        for (view in views) {
            view.visibility = View.VISIBLE
        }
    }

    protected fun changeImageViewTint(view: ImageView, context: Context, color: Int) {
        view.setColorFilter(
            ContextCompat.getColor(context, color),
            android.graphics.PorterDuff.Mode.MULTIPLY
        )
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun String.capitalizeWords(): String =
        split(" ").joinToString(" ") { it.capitalize(Locale.getDefault()) }
}