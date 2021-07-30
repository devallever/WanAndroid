package com.xm.lib.base.ui

import androidx.databinding.ViewDataBinding
import com.xm.lib.base.inters.IBaseView
import com.xm.lib.base.model.BaseViewModel

abstract class BaseFragmentKt<DB: ViewDataBinding, VM: BaseViewModel<IBaseView>> : BaseFragment<DB, VM>(){
}