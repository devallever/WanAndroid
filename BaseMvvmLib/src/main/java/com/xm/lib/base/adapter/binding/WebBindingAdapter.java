package com.xm.lib.base.adapter.binding;

import android.text.TextUtils;
import android.webkit.WebView;

import androidx.databinding.BindingAdapter;

import com.xm.lib.manager.ObjectUtils;
import com.xm.lib.manager.WebViewManager;

/**
 * Created by Juzhengyuan
 *
 * @Author: Jerry.
 * @Date: 2020/12/5 10
 * @Desc:
 */
public class WebBindingAdapter {

    @BindingAdapter("cache_path")
    public static void initWeb(WebView mWeb, String mCachePath) {
        WebViewManager.initWebView(mWeb, mCachePath);
    }

    @BindingAdapter(value = {"loadUrl","loadData"},requireAll = false)
    public static void load(WebView mWeb,String url,String data){
        if (!TextUtils.isEmpty(data)) {
            mWeb.loadData(ObjectUtils.setWebViewContent(data)
                    ,"text/html; charset=UTF-8", null);
        }
        if (!TextUtils.isEmpty(url)) {
            mWeb.loadUrl(url);
        }
    }
}
