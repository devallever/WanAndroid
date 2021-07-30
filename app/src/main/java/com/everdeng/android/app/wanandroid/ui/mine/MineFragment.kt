package com.everdeng.android.app.wanandroid.ui.mine

import com.everdeng.android.app.wanandroid.BR
import com.everdeng.android.app.wanandroid.R
import com.everdeng.android.app.wanandroid.base.BaseFragment2
import com.everdeng.android.app.wanandroid.databinding.FragmentMineBinding
import com.everdeng.android.app.wanandroid.ui.mine.model.MineViewModel
import com.xm.lib.base.config.DataBindingConfig

class MineFragment: BaseFragment2<FragmentMineBinding, MineViewModel>() {
    override fun initDataBindingConfig() = DataBindingConfig(R.layout.fragment_mine, BR.mineViewModel)

    override fun initDataAndEvent() {
    }

    override fun destroyView() {
    }
}