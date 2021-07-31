package com.everdeng.android.app.wanandroid.ui.main.model

import com.xm.lib.base.inters.IBaseView
import com.xm.lib.base.model.BaseViewModelKt
import com.xm.lib.datastroe.DataStore
import com.xm.lib.util.log

class MainViewModel : BaseViewModelKt<IBaseView>() {
    override fun onCreated() {
        DataStore.putString("KEY", "TEST MMKE")
        log("text mmkv value = " + DataStore.getString("KEY"))
    }
}