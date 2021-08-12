package com.xm.lib.widget.viewpager;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

/**
 * @author Allever
 * @date 18/5/21
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;
    private Context mContext;
    private ViewPager mViewPager;

    public ViewPagerAdapter(FragmentManager fragmentManager, Context context, List<Fragment> fragmentList) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mFragmentList = fragmentList;
        mContext = context;
    }

    public ViewPagerAdapter(FragmentManager fragmentManager, Context context, List<Fragment> fragmentList, ViewPager viewPager) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mFragmentList = fragmentList;
        mContext = context;
        mViewPager = viewPager;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
