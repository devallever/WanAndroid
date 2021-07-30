package com.xm.netmodel.model;

import android.text.TextUtils;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.xm.netmodel.helder.ResultThrowable;
import com.xm.netmodel.helder.RxUtil;
import com.xm.netmodel.inters.HttpRequestListener;
import com.xm.netmodel.inters.INetView;
import com.xm.netmodel.inters.IResponseListener;
import com.xm.netmodel.util.L;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by Juzhengyuan
 *
 * @Author: Jerry.
 * @Date: 2021/1/26 13
 * @Desc:
 */
public abstract class BaseNetModel extends CacheModel<String> implements IResponseListener<String>, LifecycleObserver {

    private INetView mView;
    private boolean isLoading = false;
    private String cacheDataKey = null;
    /**
     * 默认5分钟更新一次数据
     */
    private long updateTime = 5 * 60 * 1000;


    private final String TAGS = "HttpApi";
    private CompositeDisposable mCompositeDisposable = null;
    private HttpRequestListener<String> requestListener;


    public BaseNetModel() {
    }

    public BaseNetModel(INetView mView) {
        this.mView = mView;
    }

    public BaseNetModel(String cacheDataKey, long updateTime) {
        initCacheData(cacheDataKey, updateTime);
    }

    public BaseNetModel(INetView mView, String cacheDataKey, long updateTime) {
        this.mView = mView;
        initCacheData(cacheDataKey, updateTime);
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
    }


    /**
     * 开始请求，并把请求加入到CompositeDisposable队列上
     */
    protected void runRequest() {
        Observable request = requestApi();
        if (null == request) {
            throw new NullPointerException("request api is null");
        }
        if (null == mCompositeDisposable) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(request.compose(RxUtil.rxSchedulers())
                .compose(RxUtil.exceptionTransformer())
                .doOnSubscribe(disposable -> {
                    onSubscribe();
                }).doOnComplete(() -> {
                    onComplete();
                }).subscribe((Consumer<ResponseBody>)response -> {
                    String data = response.string();
                    onSuccess(data, TextUtils.isEmpty(data));
                }, (Consumer<ResultThrowable>) throwable -> {
                    onError(throwable.code, throwable);
                    onComplete();
                }));
    }

    public void register(HttpRequestListener<String> requestListener) {
        this.requestListener = requestListener;
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
        isLoading = false;
    }

    @Override
    public void onSuccess(String data, boolean isEmpty) {
        if (null != requestListener) {
            requestListener.onSuccessRequest(this, data, isEmpty);
        }
        isLoading = false;
    }

    @Override
    public void onError(int code, Throwable e) {
        if (null != requestListener) {
            requestListener.onFailRequest(this, e.getMessage());
        }
        isLoading = false;
    }

    @Override
    public void loading() {
        if (isLoading) {
            return;
        }
        isLoading = true;
        //没有设置缓存，请求网络数据
        if (TextUtils.isEmpty(cacheDataKey)) {
            runRequest();
            return;
        }
        String cacheData = getCacheData(cacheDataKey, updateTime);
        if (null != cacheData) {
            //返回缓存数据
            onSuccess(cacheData,null == cacheData);
            isLoading = false;
            return;
        }
        runRequest();
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


    /**
     * 注册生命周期监听，在onStop时，取消页面请求
     *
     * @param owner
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public final void destroy(LifecycleOwner owner) {
        cancelRequest();
    }

    /**
     * 取消请求
     */
    private void cancelRequest() {
        if (null != mCompositeDisposable) {
            mCompositeDisposable.clear();
            mCompositeDisposable.dispose();
            mCompositeDisposable = null;
        }
    }

}
