package com.xm.lib.util

import android.util.Log

private val LOG_TAG = "ILogger"
fun log(msg: String) {
    Log.d(LOG_TAG, msg)
}

fun loge(msg: String) {
    Log.e(LOG_TAG, msg)
}