package com.xm.netmodel.model;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.xm.netmodel.dao.CacheBean;
import com.xm.netmodel.helder.ExceptionHandle;
import com.xm.netmodel.util.HawkUtils;
import com.xm.netmodel.util.HttpDataUtils;

/**
 * Created by Juzhengyuan
 *
 * @Author: Jerry.
 * @Date: 2021/1/26 13
 * @Desc:
 */
public class CacheModel<M> {
    /**
     * 获取并返回缓存数据
     * @param cacheDataKey
     * @param updateTime
     * @return
     */
    protected M getCacheData(String cacheDataKey, long... updateTime) {
        CacheBean cacheData = HawkUtils.getInstance().getCacheData(cacheDataKey);
        if (null != cacheData) {
            if (null != updateTime && updateTime.length > 0) {
                long time = updateTime[0];
                //请求数据
                if (System.currentTimeMillis() - cacheData.cacheTime >= time) {
                    return null;
                }
            }
            //读取缓存
            M data = new Gson().fromJson(cacheData.data
                    , HttpDataUtils.getGenericType(this.getClass(), 1));
            return data;
        }
        //没有缓存数据，请求数据
        return null;
    }

    /**
     * 缓存请求数据
     *
     * @param data
     */
    protected void saveCacheData(String cacheDataKey,M data,boolean isFirstPage) {
        //key和第一页的数据进行缓存，不是第一页的不进行缓存
        if (TextUtils.isEmpty(cacheDataKey) && !isFirstPage) {
            return;
        }
        String json = new Gson().toJson(data);
        CacheBean cache = new CacheBean(System.currentTimeMillis(), json);
        HawkUtils.getInstance().saveCacheData(cacheDataKey, cache);
    }

    /**
     * 检查是否有网络，没有网络则查看是否有缓存，有缓存则拿出缓存返回给应用
     *
     * @param code
     */
    protected boolean checkNetResult(String cacheDataKey,int code) {
        if (ExceptionHandle.INTERNAL_SERVER_ERROR == code || ExceptionHandle.ERROR.NETWORD_ERROR == code
                || ExceptionHandle.ERROR.TIMEOUT_ERROR == code || ExceptionHandle.ERROR.TIMEOUT_ERROR == code) {
            //网络错误，查看是否存在缓存，有则提取缓存，没有则不处理
            //没有设置缓存，请求网络数据
            if (TextUtils.isEmpty(cacheDataKey)) {
                return false;
            }
            return true;
        }
        return false;
    }

}
