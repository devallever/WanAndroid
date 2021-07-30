package com.xm.netmodel.model;

import android.text.TextUtils;

import androidx.lifecycle.LifecycleObserver;

import com.xm.netmodel.helder.ResultThrowable;
import com.xm.netmodel.inters.INetView;

/**
 * Created by Android Studio.
 *
 * @author Jerry
 * Date: 2020/11/12 22:10
 * @description:
 */
public abstract class NetModel<T,M> extends BaseHttpModel<T,M> implements LifecycleObserver {

    private INetView mView;
    private boolean isLoading = false;

    protected int mPage = -1;
    private int initPage = 0;

    private String cacheDataKey;
    /**
     * 默认5分钟更新一次数据
     */
    private long updateTime = 5 * 60 * 1000;

    public NetModel(int... startPage) {
        initPage(startPage);
    }

    public NetModel(String cacheDataKey, int... startPage) {
        initCacheData(cacheDataKey, updateTime);
        initPage(startPage);
    }

    public NetModel(INetView mView, int... startPage) {
        this.mView = mView;
        initPage(startPage);
    }

    public NetModel(String cacheDataKey, long updateTime, int... startPage) {
        initCacheData(cacheDataKey, updateTime);
        initPage(startPage);
    }

    public NetModel(INetView mView, String cacheDataKey, long updateTime, int... startPage) {
        this.mView = mView;
        initCacheData(cacheDataKey, updateTime);
        initPage(startPage);
    }

    /**
     * 设置缓存的key和更新时间，莫扔的更新时间为5分钟更新一下缓存
     *
     * @param cacheDataKey
     * @param updateTime
     */
    private void initCacheData(String cacheDataKey, long updateTime) {
        this.cacheDataKey = "api_cache_" + cacheDataKey;
        if (updateTime > 0) {
            this.updateTime = updateTime;
        }
        log("this.cacheDataKey ->"+this.cacheDataKey+" :: -> "+this.updateTime);
    }

    /**
     * 初始化分页页码
     *
     * @param startPage
     */
    private void initPage(int... startPage) {
        if (null != startPage && startPage.length > 0) {
            this.initPage = startPage[0];
            this.mPage = initPage;
        }
    }

    /**
     * 加载时显示等待框
     */
    @Override
    public final void onSubscribe() {
        if (null != mView) {
            mView.showLoadingDialog();
        }
    }

    /**
     * 请求结束，关闭等待框
     */
    @Override
    public final void onComplete() {
        if (null != mView) {
            mView.hideLoadingDialog();
        }
    }

    /**
     * 显示缺省页
     * 如果没有实现INetView接口，可以复写这一方法
     */
    protected void showDefaultView() {
        if (null != mView) {
            mView.showDefaultView();
        }
    }

    @Override
    public void onSuccess(M data, boolean isEmpty) {
        saveCacheData(cacheDataKey,data,isFirstPage());
        notifySuccessData(data,isEmpty);
    }

    @Override
    public void onError(int code, Throwable e) {
        log("onFailure code:" + code + "-->>" + e.getMessage());
        if (!TextUtils.isEmpty(cacheDataKey) && isFirstPage() && checkNetResult(cacheDataKey,code)) {
            M cacheData = getCacheData(cacheDataKey);
            if (null != cacheData) {
                notifySuccessData(cacheData,null == cacheData);
                return;
            }
        }
        if (null != mApiRequestListener) {
            mApiRequestListener.onFailRequest(this, e.getMessage());
        }
        loadEnd(true);
    }

    /**
     * 请求结束后，数据处理
     *
     * @param isEmpty
     */
    private void loadEnd(boolean isEmpty) {
        isLoading = false;
        //显示缺省页
        if (isEmpty && isFirstPage()) {
            showDefaultView();
        }
        //设置了分页
        if (mPage > 0) {
            mPage++;
        }
    }

    /**
     * 回调成功数据数据
     *
     * @param data
     * @param isEmpty
     */
    private void notifySuccessData(M data, boolean isEmpty) {
        if (null != mApiRequestListener) {
            mApiRequestListener.onSuccessRequest(this,data, isEmpty, isFirstPage());
        }
        loadEnd(isEmpty);
    }

    /**
     * 加载数据
     */
    @Override
    public final void loading() {
        //避免重复加载
        log("loading -> "+isLoading);
        if (isLoading) {
            return;
        }
        isLoading = true;
        //没有设置缓存，请求网络数据
        if (TextUtils.isEmpty(cacheDataKey)) {
            runRequest();
            return;
        }
        //如果分页，第一页获取缓存，其他请求服务器有缓存数据，先取缓存再判断时候获取数据
        if (isFirstPage()) {
            M cacheData = getCacheData(cacheDataKey, updateTime);
            if (null != cacheData) {
                //返回缓存数据
                onSuccess(cacheData,null == cacheData);
                return;
            }
        }
        runRequest();
    }

    /**
     * 是否是第一页数据
     * @return
     */
    private boolean isFirstPage() {
        return -1 == mPage || mPage == initPage;
    }

    /**
     * 刷新第一页
     */
    public final void refresh() {
        mPage = initPage;
        loading();
    }

}
