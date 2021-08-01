package com.xm.netmodel.intercepters;

import android.util.Log;

import com.xm.netmodel.config.Config;
import com.xm.netmodel.dao.DataConfig;
import com.xm.netmodel.dao.RequestConfig;
import com.xm.netmodel.enums.LoginState;
import com.xm.netmodel.inters.LoginStateListener;
import com.xm.netmodel.util.L;
import com.xm.netmodel.util.StringUtils;
import com.xm.netmodel.util.TokenUtils;

import org.json.JSONObject;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by MvvmLib
 *
 * @Author: Jerry.
 * @Date: 2020/11/12 17
 * @Desc:
 */
public class LoggingInterceptor extends BaseLoggingInterceptor {

    private int maxLen = 2500;
    private RequestConfig config;
    private DataConfig dataConfig;

    @Override
    public void log(String message) {
        Log.e(RequestConfig.TAGS, message);
//        if (message.length() > maxLen) {
//            L.i(RequestConfig.TAGS, message);
//        } else {
//            L.e(RequestConfig.TAGS, message);
//        }
        if (null == config) {
            config = Config.getConfig().getRequestConfig();
        }
        if (null == dataConfig) {
            dataConfig = Config.getConfig().getDataConfig();
        }
        if (null != config.loginStateListener && StringUtils.isJsonFormat(message)) {
            try {
                JSONObject object = new JSONObject(message);
                int code = object.getInt(dataConfig.codeName);
                if (code == config.responseLoginErr) {
                    String msgName = dataConfig.msgName;
                    msgName = object.has(msgName) ? "" : object.getString(msgName);
                    TokenUtils.getUtils().setToken("");
                    config.loginStateListener.onStateChanged(LoginState.OUT,msgName);
                }
            }catch (Exception e){
                L.e("error:"+e.getMessage());
            }
        }
    }
}
