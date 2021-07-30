package com.xm.netmodel.util;

import android.text.TextUtils;

import com.orhanobut.hawk.Hawk;
import com.xm.netmodel.config.Config;
import com.xm.netmodel.dao.RequestConfig;
import com.xm.netmodel.enums.LoginState;

/**
 * Created by MvvmLib
 *
 * @Author: Jerry.
 * @Date: 2020/11/12 18
 * @Desc:
 */
public class TokenUtils {

    private static TokenUtils utils;

    private RequestConfig config;
    private String mToken;

    public synchronized static TokenUtils getUtils() {
        if (null == utils) {
            synchronized (TokenUtils.class) {
                if (null == utils) {
                    utils = new TokenUtils();
                    utils.config = Config.getConfig().getRequestConfig();
                }
            }
        }
        return utils;
    }

    /**
     * 获取http请求token
     */
    public String getToken() {
        mToken = config.mToken;
        if (TextUtils.isEmpty(mToken) && null != Hawk.get(RequestConfig.TOKEN_KEY)) {
            mToken = Hawk.get(RequestConfig.TOKEN_KEY);
        }
        L.e("mToken:" + mToken);
        return mToken;
    }

    /**
     * 设置http请求token，保存到缓存
     */
    public void setToken(String userToken) {
        if (TextUtils.isEmpty(userToken)) {
            config.mToken = userToken;
            Hawk.delete(RequestConfig.TOKEN_KEY);
            return;
        }
        RequestConfig config = Config.getConfig().getRequestConfig();
        config.mToken = userToken;
        if (null != config.loginStateListener) {
            config.loginStateListener.onStateChanged(LoginState.LOGGED, "logged success");
        }
        //保存token
        Hawk.put(RequestConfig.TOKEN_KEY, userToken);
    }

    /**
     * 设置http请求token，保存到缓存
     */
    public void setBearerToken(String userToken) {
        if (TextUtils.isEmpty(userToken)) {
            Hawk.delete(RequestConfig.TOKEN_KEY);
            return;
        }
        setToken("Bearer " + userToken);
    }
}
