package com.xm.lib.base.model

import com.xm.lib.base.inters.IBaseView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class BaseViewModelKt<V: IBaseView> : BaseViewModel<V>(){

    private val mJob by lazy {
        Job()
    }

    val mainCoroutine by lazy {
        CoroutineScope(Dispatchers.Main + mJob)
    }

    override fun destroyEvent() {
        mJob.cancel()
    }
}