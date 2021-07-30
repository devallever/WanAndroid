package com.xm.lib.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

object CoroutineHelper {
    private val mJob by lazy {
        Job()
    }

    val mainCoroutine by lazy {
        CoroutineScope(Dispatchers.Main + mJob)
    }

    fun cancelAll() {
        mJob.cancel()
    }

}