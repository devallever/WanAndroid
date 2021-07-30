package com.xm.netmodel.dao;

public class CacheBean {
    public long cacheTime;
    public String data;

    public CacheBean(long cacheTime, String data) {
        this.cacheTime = cacheTime;
        this.data = data;
    }

    @Override
    public String toString() {
        return "CacheBean{" +
                "cacheTime=" + cacheTime +
                ", data='" + data + '\'' +
                '}';
    }
}
