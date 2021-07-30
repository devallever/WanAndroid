package com.xm.netmodel.dao;

import com.xm.netmodel.inters.LoginStateListener;

/**
 * Created by MvvmLib
 *
 * @Author: Jerry.
 * @Date: 2020/11/12 16
 * @Desc:
 */
public class RequestConfig {

    /**
     * 请求成功码
     */
    public int responseOk = 0;
    /**
     * 登录过期码
     */
    public int responseLoginErr = -1;
    /**
     * json解析异常错误码
     */
    public static final int JSON_SYNTAX_ERR = -10001;
    /**
     * 是否使用Authorization 验证token
     */
    public boolean isVerifyToken = false;
    /**
     * 连接超时时间
     */
    public static final int CONNECT_TIME_OUT = 60;
    /**
     * 读写超时时间
     */
    public static final int WRITE_OR_READ_TIME_OUT = 15;
    /**
     * 保存token秘钥
     */
    public static final String TOKEN_KEY = "http_api_token_key";

    /**
     * 需要设置参数
     */
    public String baseUrl = "";
    public String mToken = "";
    public LoginStateListener loginStateListener;

    public static final String TAGS = "HttpApi";

    public RequestConfig(String baseUrl,int responseLoginErr) {
        this.responseLoginErr = responseLoginErr;
        this.baseUrl = baseUrl;
    }

    public RequestConfig(String baseUrl, int responseOk, int responseLoginErr) {
        this.baseUrl = baseUrl;
        this.responseOk = responseOk;
        this.responseLoginErr = responseLoginErr;
    }

    public RequestConfig(String baseUrl,int responseOk, int responseLoginErr, LoginStateListener loginStateListener) {
        this.responseOk = responseOk;
        this.responseLoginErr = responseLoginErr;
        this.baseUrl = baseUrl;
        this.loginStateListener = loginStateListener;
    }

    public void setVerifyToken(boolean verifyToken) {
        isVerifyToken = verifyToken;
    }

    public void setUserToken(String mToken) {
        this.mToken = mToken;
    }
}
