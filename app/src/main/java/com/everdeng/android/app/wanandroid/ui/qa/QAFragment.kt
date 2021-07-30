package com.everdeng.android.app.wanandroid.ui.qa

import com.everdeng.android.app.wanandroid.BR
import com.everdeng.android.app.wanandroid.R
import com.everdeng.android.app.wanandroid.base.BaseFragment2
import com.everdeng.android.app.wanandroid.databinding.FragmentQaBinding
import com.everdeng.android.app.wanandroid.ui.qa.model.QAViewModel
import com.xm.lib.base.config.DataBindingConfig

class QAFragment: BaseFragment2<FragmentQaBinding, QAViewModel>() {
    override fun initDataBindingConfig() = DataBindingConfig(R.layout.fragment_qa, BR.qaViewModel)

    override fun initDataAndEvent() {
    }

    override fun destroyView() {
    }
}