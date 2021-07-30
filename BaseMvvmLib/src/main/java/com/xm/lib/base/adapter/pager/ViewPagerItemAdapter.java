package com.xm.lib.base.adapter.pager;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.xm.lib.manager.LogPrint;

/**
 * @ClassName ViewPagerItemAdapter
 * @Description TODO
 * @Author Jerry
 * @Date 2020/5/4 13:17
 * @Version 1.0
 */
public class ViewPagerItemAdapter extends PagerAdapter {

    private SparseArray<View> views;

    public ViewPagerItemAdapter(SparseArray<View> views) {
        this.views = views;
        if (null == views || 0 == views.size()) {
            throw new NullPointerException("views is Empty");
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return null == views ? 0 : views.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = views.get(position);
        container.addView(view);
        LogPrint.i("instantiateItem =>"+position+" view =>"+view.getClass().getName());
        setViewItem(view, position);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = views.get(position);
        container.removeView(view);
        LogPrint.i("destroyItem =>"+position+" view =>"+view.getClass().getName());
        destroyItem(view, position);
    }

    protected void destroyItem(View view, int position) {

    }

    protected void setViewItem(View view, int position) {

    }
}
