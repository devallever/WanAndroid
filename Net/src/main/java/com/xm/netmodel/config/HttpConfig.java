package com.xm.netmodel.config;

import android.content.Context;

import com.orhanobut.hawk.Hawk;
import com.xm.netmodel.dao.RequestConfig;
import com.xm.netmodel.inters.LoginStateListener;
import com.xm.netmodel.util.TokenUtils;

/**
 * @ClassName BuildLibConfig
 * @Description TODO
 * @Author Jerry
 * @Date 2020/5/4 20:41
 * @Version 1.0
 */
public class HttpConfig {

    private static HttpConfig config;
    private int responseOk = 0;
    private boolean isVerify = false;

    private HttpConfig() {
    }

    public static HttpConfig init(Context mContext, String jsonCodeKey, String jsonDataKey) {
        Hawk.init(mContext).build();
        Config.getConfig().initConfigData(mContext, jsonCodeKey, jsonDataKey);
        if (null == config) {
            config = new HttpConfig();
        }
        return config;
    }

    public HttpConfig setJsonMsgKeyName(String msg) {
        Config.getConfig().setMsg(msg);
        return config;
    }

    public HttpConfig setResponseOk(int responseOk) {
        this.responseOk = responseOk;
        return this;
    }

    public HttpConfig setVerify(boolean verify) {
        isVerify = verify;
        return this;
    }

    public void setLoginStateListener(LoginStateListener loginStateListener){
        RequestConfig config = Config.getConfig().getRequestConfig();
        if (null != config) {
            config.loginStateListener = loginStateListener;
        }
    }


    public HttpConfig builder(String baseUrl, int loginOutErr) {
        RequestConfig config = new RequestConfig(baseUrl, responseOk, loginOutErr);
        config.setVerifyToken(isVerify);
        Config.getConfig().setRequestConfig(config);
        return this;
    }

    public HttpConfig builder(String baseUrl, int loginOutErr, LoginStateListener loginStateListener) {
        RequestConfig config = new RequestConfig(baseUrl, responseOk, loginOutErr, loginStateListener);
        config.setVerifyToken(isVerify);
        Config.getConfig().setRequestConfig(config);
        return this;
    }

    public void cleanToken(){
        TokenUtils.getUtils().setToken("");
    }

}
