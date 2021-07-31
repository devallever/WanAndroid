package com.everdeng.android.app.wanandroid

import android.content.Context
import com.xm.lib.BaseApp
import com.xm.lib.base.config.NetConfig
import com.xm.lib.datastroe.DataStore
import com.xm.lib.manager.PermissionsManager

class App : BaseApp() {
    override fun initThreadPackage() {
        DataStore.init()
    }

    override fun initLoginStateConfig() = object : NetConfig() {
        override fun setLoginOutCallBack() {

        }
    }

    companion object {
        val context: Context by lazy {
            getApp()
        }
    }
}