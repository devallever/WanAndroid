package com.everdeng.android.app.wanandroid.ui.main

import androidx.fragment.app.Fragment
import com.everdeng.android.app.wanandroid.R
import com.everdeng.android.app.wanandroid.ui.found.FoundFragment
import com.everdeng.android.app.wanandroid.ui.home.HomeFragment
import com.everdeng.android.app.wanandroid.ui.main.model.MainViewModel
import com.everdeng.android.app.wanandroid.ui.mine.MineFragment
import com.everdeng.android.app.wanandroid.ui.project.ProjectFragment
import com.everdeng.android.app.wanandroid.ui.qa.QAFragment
import com.everdeng.android.app.wanandroid.ui.system.SystemFragment
import com.xm.lib.base.ui.BaseTabActivity
import com.xm.lib.base.ui.BaseTabViewModel
import com.xm.lib.base.ui.Tab
import com.xm.lib.databinding.ActivityBaseTabBinding

class HomeActivity : BaseTabActivity<ActivityBaseTabBinding, MainViewModel>(){
    override fun isPaddingTop() = true
    override fun statusColor(): Int = R.color.colorPrimary
    override fun getTabModel(): MutableList<Tab> {
        val tabList = mutableListOf<Tab>()
        tabList.add(Tab(HomeFragment::class.java, R.drawable.ic_dashboard_black_24dp, R.string.title_home))
        tabList.add(Tab(SystemFragment::class.java, R.drawable.ic_dashboard_black_24dp, R.string.title_project))
        tabList.add(Tab(SystemFragment::class.java, R.drawable.ic_dashboard_black_24dp, R.string.title_found))
        tabList.add(Tab(QAFragment::class.java, R.drawable.ic_dashboard_black_24dp, R.string.title_qa))
        tabList.add(Tab(SystemFragment::class.java, R.drawable.ic_dashboard_black_24dp, R.string.title_mine))
        return tabList
    }

    override fun getFragmentList(): MutableList<Fragment> {
        val fragmentList = mutableListOf<Fragment>()
        fragmentList.add(HomeFragment())
        fragmentList.add(ProjectFragment())
        fragmentList.add(FoundFragment())
        fragmentList.add(QAFragment())
        fragmentList.add(MineFragment())
        return fragmentList
    }

    override fun enableScroll() = false


}