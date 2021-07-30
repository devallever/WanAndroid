package com.xm.lib.manager;

import android.text.TextUtils;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.util.Map;

/**
 * Created by Android Studio.
 *
 * @author Jerry
 * Date: 2020/8/10 22:30
 * @description:
 */
public class WebViewManager {

    /**
     * 初始化WebView
     */
    public static void initWebView(WebView webView, String appCachePath) {
        if (null == webView) {
            return;
        }
        cleanLocalStorage(webView);
        //声明WebSettings子类
        WebSettings webSettings = webView.getSettings();

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
        //支持插件
        //        webSettings.setPluginsEnabled(true);

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(false); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(false); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        //优先使用缓存:
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //缓存模式如下：
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。

        webSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
        webSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
        webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能
        //不使用缓存:
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webSettings.setDomStorageEnabled(true);  // 开启 DOM storage 功能
//        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);
//        String appCachePath = context.getApplicationContext().getCacheDir().getAbsolutePath();

        if (TextUtils.isEmpty(appCachePath)) {
            appCachePath = webView.getContext().getCacheDir().getAbsolutePath();
        }
        webSettings.setAppCachePath(appCachePath);
        webSettings.setAllowFileAccess(true);    // 可以读取文件缓存
        webSettings.setAppCacheEnabled(true);    //开启H5(APPCache)缓存功能
//        bridgeWebView.addJavascriptInterface(new HomeFragment.JsToAndroid(),"android");

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                LogPrint.e("js Console", "ConsoleMessage:" + consoleMessage.message());
                return super.onConsoleMessage(consoleMessage);
            }
        });
    }

    /**
     * 把数据写入到localStorage里面
     */
    public static void writeDataToLocalStorage(WebView webView, Map<String, Object> maps) {
        if (null != maps && !maps.isEmpty()) {
            for (Map.Entry<String, Object> entry : maps.entrySet()) {
                LogPrint.e("ConsoleMessage map VALUE--->" + entry.getKey() + ":," + entry.getValue());
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    webView.evaluateJavascript("javascript:localStorage.clear()", null);
                    webView.evaluateJavascript("window.localStorage.setItem('" + entry.getKey() + "','" + entry.getValue() + "');", null);
                    webView.evaluateJavascript("window.localStorage.setItem('" + entry.getKey() + "','" + entry.getValue() + "');", null);

                    String js = "window.localStorage.getItem('" + entry.getKey() + "');";
                    webView.evaluateJavascript(js, new ValueCallback() {
                        @Override
                        public void onReceiveValue(Object value) {
                            LogPrint.e("ConsoleMessage VALUE--->" + value);
                        }
                    });
                } else {
                    webView.loadUrl("javascript:localStorage.clear()");
                    webView.loadUrl("javascript:localStorage.setItem('" + entry.getKey() + "','" + entry.getValue() + "');");
                    webView.loadUrl("javascript:localStorage.setItem('" + entry.getKey() + "','" + entry.getValue() + "');");
                }
            }
        }
    }

    private static void cleanLocalStorage(WebView webView){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript("javascript:localStorage.clear()", null);
        }else{
            webView.loadUrl("javascript:localStorage.clear()");
        }
    }

    /**
     * 运行Js的方法
     * */
    public static void execJsMethod(WebView webView, String method) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript("javascript:" + method, null);
        } else {
            webView.loadUrl("javascript:" + method);
        }
    }

    /**
     * 销毁WebView
     */
    public static void destroyWebView(WebView webView) {
        if (null == webView) {
            return;
        }
        //清除网页访问留下的缓存
        //由于内核缓存是全局的因此这个方法不仅仅针对webview而是针对整个应用程序.
        webView.clearCache(true);
        //清除当前webview访问的历史记录
        //只会webview访问历史记录里的所有记录除了当前访问记录
        webView.clearHistory();
        //这个api仅仅清除自动完成填充的表单数据，并不会清除WebView存储到本地的数据
        webView.clearFormData();
        webView.destroy();
    }
}
