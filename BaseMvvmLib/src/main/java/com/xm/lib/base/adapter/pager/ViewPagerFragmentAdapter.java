package com.xm.lib.base.adapter.pager;

import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * @ClassName ViewPagerFragmentAdaapter
 * @Description TODO
 * @Author Jerry
 * @Date 2020/5/4 13:12
 * @Version 1.0
 */
public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {

    private SparseArray<Fragment> fragments;

    public ViewPagerFragmentAdapter(@NonNull FragmentManager fm,SparseArray<Fragment> fragments) {
        super(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return null == fragments ? 0 : fragments.size();
    }
}
