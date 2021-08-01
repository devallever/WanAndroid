package com.everdeng.android.app.wanandroid.ui.main

import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.everdeng.android.app.wanandroid.BR
import com.everdeng.android.app.wanandroid.R
import com.everdeng.android.app.wanandroid.base.BaseDataActivity2
import com.everdeng.android.app.wanandroid.databinding.ActivityMainBinding
import com.everdeng.android.app.wanandroid.ui.main.model.MainViewModel
import com.everdeng.android.app.wanandroid.util.Utils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.xm.lib.base.config.DataBindingConfig
import com.xm.lib.util.CoroutineHelper
import com.xm.lib.util.log
import kotlinx.coroutines.launch

class MainActivity : BaseDataActivity2<ActivityMainBinding, MainViewModel>() {


    override fun initDataAndEvent() {
        CoroutineHelper.ioCoroutine.launch {
            Utils.test()
        }

        val navView: BottomNavigationView = mBinding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        /*
        Passing each menu ID as a set of Ids because each
        menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
        setOf(
        R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
        )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        */
        navView.setupWithNavController(navController)

        //代码在BottomNavigationView的构造方法中
        //解决BottomNavigationView多次点击重复replace fragment
        navView.setOnNavigationItemReselectedListener {
            log("${it.itemId}")
        }
    }

    override fun destroyView() {
        CoroutineHelper.cancelAll()

    }

    override fun initDataBindingConfig() =
        DataBindingConfig(R.layout.activity_main, BR.mainViewModel)
}