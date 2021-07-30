package com.xm.netmodel.model;

import android.text.TextUtils;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.google.gson.Gson;
import com.xm.netmodel.config.Config;
import com.xm.netmodel.dao.CacheBean;
import com.xm.netmodel.dao.RequestConfig;
import com.xm.netmodel.helder.ExceptionHandle;
import com.xm.netmodel.helder.ResultThrowable;
import com.xm.netmodel.helder.RxUtil;
import com.xm.netmodel.inters.ApiRequestListener;
import com.xm.netmodel.inters.IResponseListener;
import com.xm.netmodel.util.HawkUtils;
import com.xm.netmodel.util.HttpDataUtils;
import com.xm.netmodel.util.L;
import com.xm.netmodel.util.StringUtils;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Android Studio.
 *
 * @author Jerry
 * Date: 2020/11/12 21:26
 * @description:
 */
abstract class BaseHttpModel<T, M> extends CacheModel<M> implements IResponseListener<M> {

    private final String TAGS = "HttpApi";
    private CompositeDisposable mCompositeDisposable = null;
    private String codeName = Config.getConfig().getDataConfig().codeName;
    private String responseName = Config.getConfig().getDataConfig().dataName;
    private int responseCode = Config.getConfig().getRequestConfig().responseOk;
    protected ApiRequestListener<M> mApiRequestListener;

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
                }).subscribe((Consumer<T>) response -> {
                    try {
                        // 通过反射获取model的真实类型
                        int code = (int) HttpDataUtils.getValue(response, codeName);
                        String str = StringUtils.getMsgData(response);
                        if (responseCode == code) {
                            M data = (M) HttpDataUtils.getValue(response, responseName);
                            onSuccess(data, null == data);
                        } else {
                            onError(code, new ResultThrowable(code, str));
                        }
                    } catch (Exception e) {
                        onError(RequestConfig.JSON_SYNTAX_ERR, e);
                        e.printStackTrace();
                    }
                }, (Consumer<ResultThrowable>) throwable -> {
                    onError(throwable.code, throwable);
                    onComplete();
                }));
    }

    /**
     * 获取并返回缓存数据
     * @param cacheDataKey
     * @param updateTime
     * @return
     */
    @Override
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
    @Override
    protected void saveCacheData(String cacheDataKey, M data, boolean isFirstPage) {
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
    @Override
    protected boolean checkNetResult(String cacheDataKey, int code) {
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

    /**
     * 注册生命周期监听，在onStop时，取消页面请求
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
        log("cancelRequest end");
    }

    /**
     * 注册回调监听
     *
     * @param apiRequestListener
     */
    public final void register(ApiRequestListener<M> apiRequestListener) {
        this.mApiRequestListener = apiRequestListener;
    }

    /**
     * 打印日志
     *
     * @param log
     */
    protected void log(String log) {
        L.i(TAGS, log);
    }
}
