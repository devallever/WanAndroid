package com.xm.netmodel.intercepters;

import android.text.TextUtils;

import com.xm.netmodel.config.Config;
import com.xm.netmodel.dao.RequestConfig;
import com.xm.netmodel.util.L;
import com.xm.netmodel.util.TokenUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by MvvmLib
 *
 * @Author: Jerry.
 * @Date: 2020/11/12 17
 * @Desc:
 */
public class HttpHeaderInterceptor implements Interceptor {

    private RequestConfig config;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("Content-Type", "application/json;charset=UTF-8");
        if (null == config) {
            config = Config.getConfig().getRequestConfig();
        }
        //      请求头添加数据
        String mToken = TokenUtils.getUtils().getToken();
        if (!TextUtils.isEmpty(mToken)) {
            if (config.isVerifyToken) {
                //http2 认证
                builder.addHeader("Authorization", mToken);
            } else {
                builder.addHeader("token", mToken);
            }
            L.e(RequestConfig.TAGS, "token:" + mToken);
        }
        return chain.proceed(builder.build());
    }

}
