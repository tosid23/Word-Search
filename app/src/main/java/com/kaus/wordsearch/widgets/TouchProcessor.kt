package com.kaus.wordsearch.widgets

import android.view.MotionEvent
import kotlin.math.abs
import kotlin.math.max

internal class TouchProcessor(private val mListener: OnTouchProcessed, moveThreshold: Float) {
    private var mIsDown = false
    private val mMoveThreshold: Float = max(moveThreshold, 0.1f)
    private var mLastX = 0f
    private var mLastY = 0f

    /*
        call this function to process touch event
     */
    fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mLastX = event.x
                mLastY = event.y
                mIsDown = true
                mListener.onDown(event)
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                mIsDown = false
                mListener.onUp(event)
            }
            MotionEvent.ACTION_MOVE -> if (mIsDown &&
                (abs(mLastX - event.x) > mMoveThreshold || abs(mLastY - event.y) > mMoveThreshold)
            ) {
                mLastX = event.x
                mLastY = event.y
                mListener.onMove(event)
            }
            else -> return false
        }
        return true
    }

    interface OnTouchProcessed {
        fun onDown(event: MotionEvent)
        fun onUp(event: MotionEvent)
        fun onMove(event: MotionEvent)
    }

}