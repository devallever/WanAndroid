package com.xm.netmodel.config;

import android.content.Context;
import android.text.TextUtils;

import com.xm.netmodel.dao.DataConfig;
import com.xm.netmodel.dao.RequestConfig;

/**
 * Created by BaseMvpLibs.
 *
 * @Author: Jerry.
 * Date: 2020/10/12:15:41.
 * Desc:
 */
public class Config {

    private Context mCxt;

    private DataConfig dataConfig;
    private RequestConfig requestConfig;

    private static Config config;

    public synchronized static Config getConfig() {
        if (null == config) {
            synchronized (Config.class) {
                if (null == config) {
                    config = new Config();
                }
            }
        }
        return config;
    }

    /**
     * 初始化工具参数
     */
    public Config initConfigData(Context mCxt, String codeName, String dataName) {
        this.mCxt = mCxt;
        if (null == dataConfig) {
            dataConfig = new DataConfig(codeName, dataName);
        } else {
            dataConfig.init(codeName, dataName);
        }
        return this;
    }

    /**
     * 初始化工具参数
     */
    public Config setMsg(String msgName) {
        if (!TextUtils.isEmpty(msgName) && null != dataConfig) {
            dataConfig.setMsgName(msgName);
        }
        return this;
    }

    public void setRequestConfig(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
    }


    public Context getContext() {
        return mCxt;
    }

    public DataConfig getDataConfig() {
        return dataConfig;
    }

    public RequestConfig getRequestConfig() {
        return requestConfig;
    }
}
