package com.everdeng.android.app.wanandroid

import android.content.Context
import com.xm.lib.BaseApp
import com.xm.lib.base.config.NetConfig
import com.xm.lib.datastroe.DataStore
import com.xm.lib.manager.PermissionsManager
import com.xm.netmodel.config.HttpConfig

class App : BaseApp() {
    override fun initThreadPackage() {

        //初始化网络
        HttpConfig.init(applicationContext, "errorCode", "data")
            .setJsonMsgKeyName("errorMsg").setResponseOk(0)
            .setVerify(true)
            .builder("https://www.wanandroid.com/", 100)

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