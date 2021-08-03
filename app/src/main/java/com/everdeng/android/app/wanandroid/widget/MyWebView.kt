package com.everdeng.android.app.wanandroid.widget

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.webkit.WebView

class MyWebView: WebView {
    constructor( context: Context): super(context)
    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, themeRes: Int): super(context, attributeSet, themeRes)

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && canGoBack()) {
            goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event)
    }
}