package com.everdeng.android.app.wanandroid.ui.project

import com.everdeng.android.app.wanandroid.BR
import com.everdeng.android.app.wanandroid.R
import com.everdeng.android.app.wanandroid.base.BaseFragment2
import com.everdeng.android.app.wanandroid.databinding.FragmentProjectBinding
import com.everdeng.android.app.wanandroid.ui.project.model.ProjectViewModel
import com.xm.lib.base.config.DataBindingConfig

class ProjectFragment: BaseFragment2<FragmentProjectBinding, ProjectViewModel>() {
    override fun initDataBindingConfig() = DataBindingConfig(R.layout.fragment_project, BR.projectViewModel)

    override fun initDataAndEvent() {
    }

    override fun destroyView() {
    }
}