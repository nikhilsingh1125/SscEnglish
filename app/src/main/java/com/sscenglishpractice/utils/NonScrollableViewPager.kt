package com.sscenglishpractice.utils

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NonScrollableViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // Always return false to disable scrolling
        return false
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        // Always return false to disable scrolling
        return false
    }
}