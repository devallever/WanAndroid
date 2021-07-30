package com.everdeng.android.app.wanandroid

import android.content.Context
import com.xm.lib.BaseApp
import com.xm.lib.base.config.NetConfig

class App : BaseApp() {
    override fun initThreadPackage() {
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