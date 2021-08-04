package com.everdeng.android.app.wanandroid.ui.project

import androidx.fragment.app.Fragment
import com.everdeng.android.app.wanandroid.BR
import com.everdeng.android.app.wanandroid.R
import com.everdeng.android.app.wanandroid.base.BaseFragment2
import com.everdeng.android.app.wanandroid.databinding.FragmentProjectBinding
import com.everdeng.android.app.wanandroid.ui.article.ArticleListFragment
import com.everdeng.android.app.wanandroid.ui.project.model.ProjectViewModel
import com.xm.lib.base.config.DataBindingConfig
import com.xm.lib.widget.viewpager.ViewPagerAdapter

class ProjectFragment : BaseFragment2<FragmentProjectBinding, ProjectViewModel>() {
    override fun initDataBindingConfig() =
        DataBindingConfig(R.layout.fragment_project, BR.projectViewModel)

    override fun initDataAndEvent() {

        val titles = listOf("标题", "标题", "标题")
        val fragmentList = arrayListOf<Fragment>()
        fragmentList.add(ArticleListFragment())
        fragmentList.add(ArticleListFragment())
        fragmentList.add(ArticleListFragment())


        val mViewPagerAdapter = ViewPagerAdapter(childFragmentManager, mCxt, fragmentList)
        mBinding.viewPager.adapter = mViewPagerAdapter
        mBinding.viewPager.offscreenPageLimit = titles.size
        mBinding.slideTabLayout.setViewPager(mBinding.viewPager, titles.toTypedArray())
    }

    override fun destroyView() {
    }
}