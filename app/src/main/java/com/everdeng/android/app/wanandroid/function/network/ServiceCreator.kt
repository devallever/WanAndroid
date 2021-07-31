package com.everdeng.android.app.wanandroid.function.network

import com.xm.netmodel.intercepters.HttpHeaderInterceptor
import com.xm.netmodel.intercepters.LoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {

    private const val BASE_URL = "https://www.wanandroid.com/"

    private val mRetrofit by lazy {

        val builder = OkHttpClient.Builder()
        //日志拦截
        val loggingInterceptor = HttpLoggingInterceptor(LoggingInterceptor())
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(loggingInterceptor)

        //请求头拦截
        builder.addInterceptor(HttpHeaderInterceptor())
        Retrofit.Builder()
            .client(builder.build())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> create(apiServiceClass: Class<T>): T = mRetrofit.create(apiServiceClass)

}