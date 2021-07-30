package com.everdeng.android.app.wanandroid.ui.system

import com.everdeng.android.app.wanandroid.BR
import com.everdeng.android.app.wanandroid.R
import com.everdeng.android.app.wanandroid.base.BaseFragment2
import com.everdeng.android.app.wanandroid.databinding.FragmentSystemBinding
import com.everdeng.android.app.wanandroid.ui.system.model.SystemViewModel
import com.xm.lib.base.config.DataBindingConfig

class SystemFragment: BaseFragment2<FragmentSystemBinding, SystemViewModel>() {
    override fun initDataBindingConfig() = DataBindingConfig(R.layout.fragment_system, BR.systemViewModel)

    override fun initDataAndEvent() {
    }

    override fun destroyView() {
    }
}