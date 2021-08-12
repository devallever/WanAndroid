package com.everdeng.android.app.wanandroid.ui.project

import androidx.lifecycle.lifecycleScope
import com.everdeng.android.app.wanandroid.BR
import com.everdeng.android.app.wanandroid.R
import com.everdeng.android.app.wanandroid.base.BaseFragment2
import com.everdeng.android.app.wanandroid.databinding.FragmentProjectBinding
import com.everdeng.android.app.wanandroid.ui.project.model.ProjectViewModel
import com.xm.lib.base.config.DataBindingConfig
import com.xm.lib.widget.viewpager.ViewPagerAdapter

class ProjectFragment : BaseFragment2<FragmentProjectBinding, ProjectViewModel>() {
    override fun initDataBindingConfig() =
        DataBindingConfig(R.layout.fragment_project, BR.projectViewModel)

    override fun initDataAndEvent() {
        lifecycleScope.launchWhenResumed {
            mViewModel.init()
        }
        mViewModel.vpAdapter = ViewPagerAdapter(childFragmentManager, mCxt, mViewModel.fragmentList, mBinding.viewPager)
        mBinding.viewPager.adapter = mViewModel.vpAdapter
//        mBinding.viewPager.offscreenPageLimit = 5
        mBinding.slideTabLayout.setViewPager(mBinding.viewPager)
//        mViewModel.titleLiveData.observe(this,
//            {
//                it.map {it
//                    mBinding.slideTabLayout.addNewTab(it)
//                }
//            })
    }

    override fun destroyView() {
    }
}