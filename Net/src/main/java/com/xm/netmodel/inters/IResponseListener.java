package com.xm.netmodel.inters;

import io.reactivex.Observable;

/**
 * Created by Android Studio.
 *
 * @author Jerry
 * Date: 2020/11/12 21:24
 * @description:
 */
public interface IResponseListener<T> {

    void onSubscribe();

    void onComplete();

    void onSuccess(T data,boolean isEmpty);

    void onError(int code, Throwable e);

    void loading();

    Observable requestApi();
}
