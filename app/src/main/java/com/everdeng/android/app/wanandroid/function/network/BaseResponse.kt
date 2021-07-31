package com.everdeng.android.app.wanandroid.function.network

open class BaseResponse<T>() {
    open var data: T? = null
    var errorCode: Int = 0
    var errorMsg: String = ""
}