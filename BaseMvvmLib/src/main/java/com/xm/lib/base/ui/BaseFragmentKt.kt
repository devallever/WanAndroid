package com.xm.lib.base.ui

import com.xm.lib.base.inters.IBaseView
import com.xm.lib.base.model.BaseViewModel

abstract class BaseFragmentKt<VM: BaseViewModel<IBaseView>> : BaseFragment<VM>(){
}