package com.xm.netmodel.intercepters;

import android.util.Log;

import com.xm.netmodel.config.Config;
import com.xm.netmodel.dao.DataConfig;
import com.xm.netmodel.dao.RequestConfig;
import com.xm.netmodel.enums.LoginState;
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
public class BaseLoggingInterceptor implements HttpLoggingInterceptor.Logger {

    @Override
    public void log(String message) {
        Log.e(RequestConfig.TAGS, message);
    }
}
