package com.kaus.wordsearch.utilities.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.kaus.wordsearch.R
import kotlin.math.max
import kotlin.math.min

abstract class GridBehavior : View {
    private var mGridWidth = 0
    private var mGridHeight = 0

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    var gridWidth: Int
        get() = (mGridWidth * scaleX).toInt()
        set(gridWidth) {
            mGridWidth = gridWidth
            invalidate()
        }
    var gridHeight: Int
        get() = (mGridHeight * scaleY).toInt()
        set(gridHeight) {
            mGridHeight = gridHeight
            invalidate()
        }

    abstract fun getColCount(): Int
    abstract fun getRowCount(): Int

    open fun getRequiredWidth(): Int {
        return paddingLeft + paddingRight + getColCount() * gridWidth
    }

    open fun getRequiredHeight(): Int {
        return paddingTop + paddingBottom + getRowCount() * gridHeight
    }

    fun getColIndex(screenPos: Int): Int {
        return max(min((screenPos - paddingLeft) / gridWidth, getColCount() - 1), 0)
    }

    fun getRowIndex(screenPos: Int): Int {
        return max(min((screenPos - paddingTop) / gridHeight, getRowCount() - 1), 0)
    }

    open fun getCenterColFromIndex(cIdx: Int): Int {
        return min(max(0, cIdx), getColCount() - 1) * gridWidth +
                gridWidth / 2 + paddingLeft
    }

    open fun getCenterRowFromIndex(rIdx: Int): Int {
        return min(max(0, rIdx), getRowCount() - 1) * gridHeight +
                gridHeight / 2 + paddingTop
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var measuredWidth = getRequiredWidth()
        var measuredHeight = getRequiredHeight()
        if (widthMode == MeasureSpec.EXACTLY) measuredWidth =
            width else if (widthMeasureSpec == MeasureSpec.AT_MOST) measuredWidth =
            min(measuredWidth, width)
        if (heightMode == MeasureSpec.EXACTLY) measuredHeight =
            height else if (heightMode == MeasureSpec.AT_MOST) measuredHeight =
            min(measuredHeight, height)
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        mGridWidth = 50
        mGridHeight = 50
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.GridBehavior, 0, 0)
            mGridWidth = a.getDimensionPixelSize(R.styleable.GridBehavior_gridWidth, mGridWidth)
            mGridHeight = a.getDimensionPixelSize(R.styleable.GridBehavior_gridHeight, mGridHeight)
            a.recycle()
        }
    }

    abstract fun setColCount(colCount: Int)
    abstract fun setRowCount(rowCount: Int)
}