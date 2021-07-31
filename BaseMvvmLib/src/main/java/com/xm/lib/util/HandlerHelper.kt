package com.xm.lib.util

import android.os.Handler
import android.os.Looper

object HandlerHelper {
    val mainHandler by lazy {
        Handler(Looper.getMainLooper())
    }
}