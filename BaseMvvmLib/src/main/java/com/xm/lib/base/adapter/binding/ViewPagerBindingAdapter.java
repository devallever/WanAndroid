package com.xm.lib.base.adapter.binding;

import android.util.SparseArray;
import android.view.View;

import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.xm.lib.base.adapter.pager.ViewPagerFragmentAdapter;
import com.xm.lib.base.adapter.pager.ViewPagerItemAdapter;

import java.util.List;

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
        initFragmentViewPager(mViewPager, mFragmentManager, mData);
    }


    @BindingAdapter(value = {"vpAdapter", "fragments"}, requireAll = false)
    public static void initFragmentPager(ViewPager mViewPager, FragmentPagerAdapter fragmentPagerAdapter, List<Fragment> mData) {
        mViewPager.setAdapter(fragmentPagerAdapter);
    }

    @BindingAdapter(value = {"views"})
    public static void initItemPager(ViewPager mViewPager, SparseArray<View> mData) {
        initItemViewPager(mViewPager, mData);
    }

    public static void initFragmentViewPager(ViewPager mViewPager, FragmentManager mFragmentManager, SparseArray<Fragment> mData){
        ViewPagerFragmentAdapter fragmentAdapter = new ViewPagerFragmentAdapter(mFragmentManager, mData);
        mViewPager.setAdapter(fragmentAdapter);
    }


    public static void initItemViewPager(ViewPager mViewPager,  SparseArray<View> mData){
        ViewPagerItemAdapter pagerItemAdapter = new ViewPagerItemAdapter(mData);
        mViewPager.setAdapter(pagerItemAdapter);
    }
}
