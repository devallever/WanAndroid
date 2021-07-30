package com.xm.netmodel.dao;

import android.text.TextUtils;

/**
 * Created by MvvmLib
 *
 * @Author: Jerry.
 * @Date: 2020/11/12 16
 * @Desc:
 */
public class DataConfig {

    public String codeName;
    public String dataName;
    public String msgName = "msg";

    public DataConfig(String codeName, String dataName) {
        init(codeName,dataName);
    }

    public DataConfig(String codeName, String dataName, String msgName) {
        init(codeName,dataName);
        this.msgName = msgName;
    }

    public void init(String codeName, String dataName) {
        if (TextUtils.isEmpty(codeName)) {
            throw new NullPointerException("code name is empty,init failure");
        }
        if (TextUtils.isEmpty(dataName)) {
            throw new NullPointerException("code name is empty,init failure");
        }
        this.codeName = codeName;
        this.dataName = dataName;
    }

    public void setMsgName(String msgName) {
        this.msgName = msgName;
    }

    @Override
    public String toString() {
        return "DataConfig{" +
                "codeName='" + codeName + '\'' +
                ", dataName='" + dataName + '\'' +
                ", msgName='" + msgName + '\'' +
                '}';
    }
}
