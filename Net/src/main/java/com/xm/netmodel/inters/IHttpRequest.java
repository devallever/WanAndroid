package com.xm.netmodel.inters;

import java.io.File;

import okhttp3.MultipartBody;

/**
 * Created by MvvmLib
 *
 * @Author: Jerry.
 * @Date: 2020/11/12 16
 * @Desc:
 */
public interface IHttpRequest {

    /**
     * 创建Api对象
     * @param apiClz
     * @param requestClz
     * @param <C>
     * @return
     */
    <C> C createApi(Class<C> apiClz,Class... requestClz);

    /**
     * 创建单个文件上传表单
     * @param name
     * @param file
     * @return
     */
    MultipartBody.Part createMultipartBodyPart(String name, File file);

    /**
     * 设置用户的登录token
     * @param userToken
     */
    void setUserToken(String userToken);

    /**
     * 用户是否在登录状态
     * @return
     */
    boolean isLogged();

}
