package com.everdeng.android.app.wanandroid.base

import android.view.Menu
import androidx.appcompat.widget.Toolbar
import androidx.databinding.ViewDataBinding
import com.everdeng.android.app.wanandroid.R
import com.xm.lib.base.inters.IBaseView
import com.xm.lib.base.model.BaseViewModel
import com.xm.lib.base.ui.BaseDataActivityKt

abstract class BaseDataActivity2<DB : ViewDataBinding, T : BaseViewModel<IBaseView>> :
    BaseDataActivityKt<DB, T>() {
//    override fun isStatusBarDark(): Boolean = true

    //使用了这个就不用重写initTopView
    override fun isPaddingTop(): Boolean = true
    override fun statusColor(): Int = R.color.colorPrimary

    open fun getToolbarMenu(): Int = 0

    protected open fun initToolbar(toolbar: Toolbar, title: String?, menuId: Int = 0) {
        toolbar.title = title
        initToolbar(toolbar, menuId)
    }

    protected open fun initToolbar(toolbar: Toolbar, strId: Int, menuId: Int) {
        toolbar.setTitle(strId)
        initToolbar(toolbar, menuId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuId = getToolbarMenu()
        if (menuId != 0) {
            val inflater = menuInflater
            inflater.inflate(menuId, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    protected open fun initToolbar(toolbar: Toolbar, menuId: Int = 0) {
        if (menuId != 0) {
            toolbar.inflateMenu(menuId)
        }
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setTitleTextColor(resources.getColor(R.color.white))
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }
}