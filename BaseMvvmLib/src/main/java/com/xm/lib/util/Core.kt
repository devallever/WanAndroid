package com.xm.lib.util

import android.widget.Toast
import com.xm.lib.BaseApp

fun toast(msg: String?) {
    msg?:return
    HandlerHelper.mainHandler.post {
        Toast.makeText(BaseApp.getApp(), msg, Toast.LENGTH_SHORT).show()
    }
}