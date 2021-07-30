package com.xm.lib;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.xm.lib.base.config.NetConfig;
import com.xm.lib.lifecycle.ActivityLifecycleListener;
import com.xm.lib.manager.CacheManager;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * @ClassName BaseApp
 * @Description TODO
 * @Author Jerry
 * @Date 2020/6/13 23:56
 * @Version 1.0
 */
public abstract class BaseApp extends Application {

    private static BaseApp mApp = null;

    protected abstract void initThreadPackage();

    public abstract NetConfig initLoginStateConfig();

    /**
     * 设置登录页activity
     * @return
     */
    protected Class<? extends Activity> loginCls(){
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        registerActivityLifecycleCallbacks(getCallBacks());
        handleSSLHandshake();
        initThreadPackage();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(getApplicationContext());
    }

    /**
     * 获取Application对象
     * @param <T>
     * @return
     */
    public final static <T extends BaseApp> T getApp(){
        if (null == mApp) {
            return null;
        }
        return (T) mApp;
    }

    /**
     * 获取app的FileProvider路径
     * @return
     */
    public final String getFileProvider(){
        return getPackageName() + ".imageSelectorProvider";
    }


    private ActivityLifecycleListener getCallBacks() {
        return null == loginCls()
                ? new ActivityLifecycleListener(this)
                : new ActivityLifecycleListener(this,loginCls());
    }

    /**
     * 退出登录回调方法
     * @param current
     * @param content
     */
    public void loginOut(Activity current, String content) {
        //清除应用缓存
        CacheManager.clearAllCache(getApplicationContext());
    }

    /**
     * 忽略https的证书校验
     * 避免Glide加载https图片报错：
     * javax.net.ssl.SSLHandshakeException: java.security.cert.CertPathValidatorException: Trust
     * anchor for certification path not found.
     */
    private final void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("TLS");
            // trustAllCerts信任所有的证书
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }

}
