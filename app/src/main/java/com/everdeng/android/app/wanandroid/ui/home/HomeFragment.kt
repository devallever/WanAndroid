package com.everdeng.android.app.wanandroid.ui.home

import com.everdeng.android.app.wanandroid.BR
import com.everdeng.android.app.wanandroid.R
import com.everdeng.android.app.wanandroid.base.BaseFragment2
import com.everdeng.android.app.wanandroid.databinding.FragmentHomeBinding
import com.everdeng.android.app.wanandroid.ui.home.model.HomeViewModel
import com.xm.lib.base.config.DataBindingConfig

class HomeFragment : BaseFragment2<FragmentHomeBinding, HomeViewModel>() {
    override fun initDataBindingConfig() =
        DataBindingConfig(R.layout.fragment_home, BR.homeViewModel)

    override fun initDataAndEvent() {
    }

    override fun destroyView() {
    }
}