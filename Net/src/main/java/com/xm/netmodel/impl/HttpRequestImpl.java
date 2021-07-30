package com.xm.netmodel.impl;

import android.text.TextUtils;

import com.xm.netmodel.inters.IHttpRequest;
import com.xm.netmodel.util.TokenUtils;

import java.io.File;

import okhttp3.MultipartBody;

/**
 * Created by MvvmLib
 *
 * @Author: Jerry.
 * @Date: 2020/11/12 16
 * @Desc:
 */
public class HttpRequestImpl implements IHttpRequest {

    private static IHttpRequest impl = null;

    public static IHttpRequest getRequest(){
        if (null == impl) {
            synchronized (HttpRequestImpl.class){
                if (null == impl) {
                    impl = new HttpRequestImpl();
                }
            }
        }
        return impl;
    }

    private HttpRequestImpl() {

    }

    @Override
    public <C> C createApi(Class<C> apiClz, Class... requestClz) {
        return RequestApi.getCreateApi().createBaseApi(apiClz,requestClz);
    }

    @Override
    public MultipartBody.Part createMultipartBodyPart(String name, File file) {
        return RequestApi.getCreateApi().createMultipartBodyPart(name, file);
    }

    @Override
    public void setUserToken(String userToken) {
        TokenUtils.getUtils().setToken(userToken);
    }

    @Override
    public boolean isLogged() {
        return !TextUtils.isEmpty(TokenUtils.getUtils().getToken());
    }

}
