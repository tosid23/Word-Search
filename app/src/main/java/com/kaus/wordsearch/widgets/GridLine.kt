package com.kaus.wordsearch.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import com.kaus.wordsearch.R


/**
 * ____________
 * |   |   |   |
 * |---|---|---|
 * |___|___|___|
 * |   |   |   |
 */
class GridLine : GridBehavior {
    private var mLineWidth = 0
    private var mColCount = 0
    private var mRowCount = 0
    private var mPaint: Paint? = null

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    override fun getColCount(): Int {
        return mColCount
    }

    override fun getRowCount(): Int {
        return mRowCount
    }

    var lineWidth: Int
        get() = mLineWidth
        set(lineWidth) {
            mLineWidth = lineWidth
            invalidate()
        }
    var lineColor: Int
        get() = mPaint!!.color
        set(color) {
            mPaint!!.color = color
            invalidate()
        }

    override fun getRequiredWidth(): Int {
        return super.getRequiredWidth() + mLineWidth
    }

    override fun getRequiredHeight(): Int {
        return super.getRequiredHeight() + mLineWidth
    }

    override fun getCenterColFromIndex(cIdx: Int): Int {
        return super.getCenterColFromIndex(cIdx) + mLineWidth / 2
    }

    override fun getCenterRowFromIndex(rIdx: Int): Int {
        return super.getCenterRowFromIndex(rIdx) + mLineWidth / 2
    }

    override fun setColCount(colCount: Int) {
        mColCount = colCount
        invalidate()
        requestLayout()
    }

    override fun setRowCount(rowCount: Int) {
        mRowCount = rowCount
        invalidate()
        requestLayout()
    }

    override fun onDraw(canvas: Canvas) {
        val viewWidth = getRequiredWidth()
        val viewHeight = getRequiredHeight()
        val pLeft = paddingLeft
        val pTop = paddingTop
        val pRight = paddingRight
        val pBottom = paddingBottom
        var y = pTop.toFloat()
        // horizontal lines
        for (i in 0..getRowCount()) {
            mPaint?.let {
                canvas.drawRect(
                    pLeft.toFloat(), y, viewWidth + mLineWidth - pRight.toFloat(), y + mLineWidth,
                    it
                )
            }
            y += gridHeight.toFloat()
        }
        var x = pLeft.toFloat()
        // vertical lines
        for (i in 0..getColCount()) {
            mPaint?.let {
                canvas.drawRect(
                    x, pTop.toFloat(), x + mLineWidth, viewHeight + mLineWidth - pBottom.toFloat(),
                    it
                )
            }
            x += gridWidth.toFloat()
        }
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        mLineWidth = 2
        mColCount = 8
        mRowCount = 8
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.GridLine, 0, 0)
            mLineWidth = a.getDimensionPixelSize(R.styleable.GridLine_lineWidth, mLineWidth)
            mPaint!!.color = a.getColor(R.styleable.GridLine_lineColor, Color.GRAY)
            mColCount = a.getInteger(R.styleable.GridLine_gridColumnCount, mColCount)
            mRowCount = a.getInteger(R.styleable.GridLine_gridRowCount, mRowCount)
            a.recycle()
        }
    }
}