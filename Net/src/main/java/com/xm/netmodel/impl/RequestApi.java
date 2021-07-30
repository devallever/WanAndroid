package com.xm.netmodel.impl;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.xm.netmodel.config.Config;
import com.xm.netmodel.dao.DataConfig;
import com.xm.netmodel.dao.RequestConfig;
import com.xm.netmodel.helder.ResponseBodyInterceptor;
import com.xm.netmodel.intercepters.HttpHeaderInterceptor;
import com.xm.netmodel.intercepters.LoggingInterceptor;
import com.xm.netmodel.util.HttpDataUtils;
import com.xm.netmodel.util.L;
import com.xm.netmodel.util.StringUtils;

import org.json.JSONObject;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MvvmLib
 *
 * @Author: Jerry.
 * @Date: 2020/11/12 17
 * @Desc:
 */
class RequestApi {

    private static RequestApi mApi;
    private DataConfig dataConfig;
    private Gson gson;

    public static RequestApi getCreateApi() {
        if (null == mApi) {
            synchronized (RequestApi.class) {
                if (null == mApi) {
                    mApi = new RequestApi();
                }
            }
        }
        return mApi;
    }

    /**
     * 创建请求Api对象
     *
     * @param cls
     * @param dataCls
     * @param <T>
     * @return
     */
    public <T> T createBaseApi(Class<T> cls, Class... dataCls) {
        String baseUrl = Config.getConfig().getRequestConfig().baseUrl;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //        日志拦截
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new LoggingInterceptor());
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);

        //        请求头拦截
        builder.addInterceptor(new HttpHeaderInterceptor());

        //设置json解析错误检测
        if (null != dataCls && dataCls.length > 0) {
            //添加消息拦截头
            builder.addInterceptor(new ResponseBodyInterceptor() {
                /**
                 * 过滤json解析错误的异常
                 * */
                @Override
                protected Response intercept(Response response, String url, String body) {
                    return response.newBuilder().body(checkBody(dataCls[0], response.body().contentType(), body)).build();
                }
            });
        }

        //        设置超时
        builder.connectTimeout(RequestConfig.CONNECT_TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(RequestConfig.WRITE_OR_READ_TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(RequestConfig.WRITE_OR_READ_TIME_OUT, TimeUnit.SECONDS);

        //        错误重连
        builder.retryOnConnectionFailure(true);

        //    线上和测试服的切换在这里
        Retrofit mRetrofit = new Retrofit.Builder().client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                // 测试服
                .baseUrl(baseUrl)
                .build();

        return mRetrofit.create(cls);
    }

    /**
     * 检测json和设置请求返回数据结构是否有冲突
     * @param dataCls
     * @param mediaType
     * @param body
     * @return
     */
    private ResponseBody checkBody(Class dataCls, MediaType mediaType, String body) {
        if (null == gson) {
            gson = new Gson();
        }
        if (null == dataConfig) {
            dataConfig = Config.getConfig().getDataConfig();
        }
        if (TextUtils.isEmpty(body) || !StringUtils.isJsonFormat(body)) {
            //设置空数据的json字符串返回
            return ResponseBody.create(mediaType
                    , gson.toJson(createResultObject(dataCls, RequestConfig.JSON_SYNTAX_ERR, "JSON OBJECT IS EMPTY!")));
        } else {
            try {
                gson.fromJson(body, dataCls);
            } catch (Exception e) {
                L.e(RequestConfig.TAGS, "checkBody error:" + e.getMessage()+"-->"+body);
                try {
                    JSONObject object = new JSONObject(body);
                    int code = object.getInt(dataConfig.codeName);
                    String msgName = dataConfig.msgName;
                    msgName  = object.has(msgName) ? "" :object.getString(msgName);
                    return ResponseBody.create(mediaType
                            , gson.toJson(createResultObject(dataCls, Config.getConfig().getRequestConfig().responseOk == code ? RequestConfig.JSON_SYNTAX_ERR : code, msgName)));
//                            , gson.toJson(createResultObject(dataCls, 200 == code ? RequestConfig.JSON_SYNTAX_ERR : code, msgName)));
                } catch (Exception e1) {
                    L.e("checkBody error more:" + e1.getMessage());
                }
            }
        }
        return ResponseBody.create(mediaType, body);
    }

    /**
     * 生成符合请求返回数据结构的对象
     * @param dataCls
     * @param jsonSyntaxErr
     * @param msg
     * @return
     */
    private Object createResultObject(Class dataCls, int jsonSyntaxErr, String msg) {
        try {
            Object instance = dataCls.newInstance();
            HttpDataUtils.setValue(instance, jsonSyntaxErr,dataConfig.codeName);
            String msgName = dataConfig.msgName;
            HttpDataUtils.setValue(instance, msg, msgName);
            return instance;
        } catch (Exception e) {
            L.e("createResultObject error:" + e.getMessage());
        }
        return null;
    }

    /**
     * 创建MultipartBody
     *
     * @param name 表单名称
     * @param file 文件信息
     */
    public MultipartBody.Part createMultipartBodyPart(String name, File file) {
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return MultipartBody.Part.createFormData(name, file.getName(), requestFile);
    }

}
