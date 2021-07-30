package com.xm.netmodel.helder;


import com.xm.netmodel.helder.ExceptionHandle;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxUtil {

    //compose简化线程
    public static <T> ObservableTransformer<T, T> rxSchedulers() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static ObservableTransformer exceptionTransformer() {
        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable observable) {
                return observable
                        .onErrorResumeNext(new HttpResponseFunc());
            }
        };
    }

    private static class HttpResponseFunc<T> implements Function<Throwable, Observable<T>> {
        @Override
        public Observable<T> apply(Throwable t) {
            try{
                t.printStackTrace();
                return Observable.error(ExceptionHandle.handleException(t));
            }catch (Exception e){
                return Observable.error(ExceptionHandle.handleException(e));
            }
        }
    }
}
