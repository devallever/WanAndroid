package com.xm.lib.base.adapter.binding;

import android.util.SparseArray;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.xm.lib.base.adapter.pager.ViewPagerFragmentAdapter;
import com.xm.lib.base.adapter.pager.ViewPagerItemAdapter;

/**
 *
 * @Author: Jerry.
 * @Date: 2020/11/13 12
 * @Desc:
 */
public class BindingViewPagerAdapter {

    public static void initFragmentViewPager(ViewPager mViewPager, FragmentManager mFragmentManager, SparseArray<Fragment> mData){
        ViewPagerFragmentAdapter fragmentAdapter = new ViewPagerFragmentAdapter(mFragmentManager, mData);
        mViewPager.setAdapter(fragmentAdapter);
    }


    public static void initItemViewPager(ViewPager mViewPager,  SparseArray<View> mData){
        ViewPagerItemAdapter pagerItemAdapter = new ViewPagerItemAdapter(mData);
        mViewPager.setAdapter(pagerItemAdapter);
    }
}
