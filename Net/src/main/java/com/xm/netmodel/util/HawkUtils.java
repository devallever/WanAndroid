package com.xm.netmodel.util;

import android.text.TextUtils;

import com.orhanobut.hawk.Hawk;
import com.xm.netmodel.dao.CacheBean;

/**
 * Created by Android Studio.
 *
 * @author Jerry
 * Date: 2020/11/3 23:20
 * @description:
 */
public class HawkUtils {

    private static HawkUtils hawk;

    public synchronized static HawkUtils getInstance(){
        if (null == hawk) {
            synchronized (HawkUtils.class){
                if (null == hawk) {
                    hawk = new HawkUtils();
                }
            }
        }
        return hawk;
    }

    /**
     * 存放缓存
     * @param key
     * @param data
     */
    public void saveCacheData(String key, CacheBean data){
        //解决key should not be null
        if (TextUtils.isEmpty(key)) {
            return;
        }
        Hawk.put(key,data);
    }

    /**
     * 提取缓存
     * @param key
     * @param <M>
     * @return
     */
    public <M> M getCacheData(String key){
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        return Hawk.get(key);
    }

    /**
     * 删除缓存
     * @param key
     */
    public void delete(String key){
        Hawk.delete(key);
    }
}
