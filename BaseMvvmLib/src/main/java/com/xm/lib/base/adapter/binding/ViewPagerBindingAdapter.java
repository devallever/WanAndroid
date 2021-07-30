package com.xm.lib.base.adapter.binding;

import android.util.SparseArray;
import android.view.View;

import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by Juzhengyuan
 *
 * @Author: Jerry.
 * @Date: 2020/11/13 13
 * @Desc:
 */
public class ViewPagerBindingAdapter {

    @BindingAdapter(value = {"vpFragmentManager", "fragments"}, requireAll = false)
    public static void initFragmentPager(ViewPager mViewPager, FragmentManager mFragmentManager, SparseArray<Fragment> mData) {
        BindingViewPagerAdapter.initFragmentViewPager(mViewPager, mFragmentManager, mData);
    }

    @BindingAdapter(value = {"views"})
    public static void initItemPager(ViewPager mViewPager, SparseArray<View> mData) {
        BindingViewPagerAdapter.initItemViewPager(mViewPager, mData);
    }
}
