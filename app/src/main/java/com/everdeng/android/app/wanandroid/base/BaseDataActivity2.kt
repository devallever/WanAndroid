package com.everdeng.android.app.wanandroid.base

import androidx.databinding.ViewDataBinding
import com.everdeng.android.app.wanandroid.R
import com.xm.lib.base.inters.IBaseView
import com.xm.lib.base.model.BaseViewModel
import com.xm.lib.base.ui.BaseDataActivityKt

abstract class BaseDataActivity2<DB : ViewDataBinding, T : BaseViewModel<IBaseView>> :
    BaseDataActivityKt<DB, T>() {
//    override fun isStatusBarDark(): Boolean = true

    //使用了这个就不用重写initTopView
//    override fun isPaddingTop(): Boolean = true
    override fun statusColor(): Int = R.color.colorPrimary
}