package com.everdeng.android.app.wanandroid.ui.found

import com.everdeng.android.app.wanandroid.BR
import com.everdeng.android.app.wanandroid.R
import com.everdeng.android.app.wanandroid.base.BaseFragment2
import com.everdeng.android.app.wanandroid.databinding.FragmentFoundBinding
import com.everdeng.android.app.wanandroid.ui.found.model.FoundViewModel
import com.xm.lib.base.config.DataBindingConfig

class FoundFragment: BaseFragment2<FragmentFoundBinding, FoundViewModel>() {
    override fun initDataBindingConfig() = DataBindingConfig(R.layout.fragment_found, BR.foundViewModel)

    override fun initDataAndEvent() {
    }

    override fun destroyView() {
    }
}