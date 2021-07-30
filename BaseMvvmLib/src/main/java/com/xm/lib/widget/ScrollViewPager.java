package com.xm.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.xm.lib.R;
import com.xm.lib.manager.LogPrint;

/**
 * 自定义ViewPager
 * 可设置Viewpager是否可以滑动
 *
 * @author Administrator
 */
public class ScrollViewPager extends ViewPager {

    private boolean noScroll = false;
    private boolean isReset = false;

    public ScrollViewPager(Context context) {
        super(context);
        init();
    }

    public ScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ScrollViewPager);
        noScroll = array.getBoolean(R.styleable.ScrollViewPager_enable_scroll, noScroll);
        isReset = array.getBoolean(R.styleable.ScrollViewPager_enable_size, isReset);
        array.recycle();
        init();

    }

    private void init() {
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    /**
     * 是否可以滑动
     */
    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        /* return false;//super.onTouchEvent(arg0); */
        if (noScroll) {
            return false;
        } else {
            return super.onTouchEvent(arg0);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (noScroll) {
            return false;
        } else {
            return super.onInterceptTouchEvent(arg0);
        }
    }

    public void setReset(boolean reset) {
        isReset = reset;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (isReset) {
            // find the current child view
            // and you must cache all the child view
            // use setOffscreenPageLimit(adapter.getCount())

            View view = getChildAt(getCurrentItem());
//            LogPrint.e("index =>"+getCurrentItem()+" count =>"+getChildCount());
            if (view != null) {
                // measure the current child view with the specified measure spec
                view.measure(widthMeasureSpec, heightMeasureSpec);
            }
            setMeasuredDimension(getMeasuredWidth(), measureHeight(heightMeasureSpec, view));
        }
    }

    /**
     * Determines the height of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @param view the base view with already measured height
     *
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureHeight(int measureSpec, View view) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            // set the height from the base view if available
            if (view != null) {
                result = view.getMeasuredHeight();
            }
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
//        LogPrint.e("measureHeight result =>"+result);
        return result;
    }


    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }
}
