package com.xm.lib.widget.viewpager

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 *
 * @author caihan
 * @date 2016/12/1
 */
class ScrollViewPager : ViewPager {
    private var isCanScroll = false

    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
    }

    fun setScanScroll(isCanScroll: Boolean) {
        this.isCanScroll = isCanScroll
    }

    override fun scrollTo(x: Int, y: Int) {
        super.scrollTo(x, y)
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean =
        isCanScroll && super.onTouchEvent(event)

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean =
        isCanScroll && super.onInterceptTouchEvent(event)

    companion object {
        private const val TAG = "CustomViewPager"
    }
}