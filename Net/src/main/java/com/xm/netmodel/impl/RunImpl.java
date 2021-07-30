package com.xm.netmodel.impl;

import com.xm.netmodel.helder.ExceptionHandle;
import com.xm.netmodel.helder.ResultThrowable;
import com.xm.netmodel.helder.RxUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * <pre>
 *     author : Jerry
 *     time   : 2020/06/03
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class RunImpl {

    private static RunImpl impl;

    public synchronized static RunImpl getImpl() {
        if (null == impl) {
            impl = new RunImpl();
        }
        return impl;
    }

    private <T> Observable<T> createObserver(RxMessageImpl run) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext((T) run.running());
                }catch (Exception e){
                    e.printStackTrace();
                    ResultThrowable throwable = ExceptionHandle.handleException(e);
                    run.onFailure(throwable.code,throwable.message);
                }
                emitter.onComplete();
            }
        });
    }

    public Disposable createRxRunning(RxMessageImpl run) {
        if (null == run) {
            throw new NullPointerException("CALL BACK IS NULL");
        }
        return createObserver(run).compose(RxUtil.exceptionTransformer())
                .compose(RxUtil.rxSchedulers())
                .doOnSubscribe(disposable -> run.onSubscribe())
                .doOnComplete(() -> run.onComplete())
                .doOnError((Consumer<ResultThrowable>) throwable -> {
                        run.onFailure(throwable.code, throwable.message);
                    run.onComplete();
                }).doOnNext(run.getConsumer())
                .subscribe();
    }


    public abstract static class RxMessageImpl<T> {

        protected abstract T running();

        protected abstract void onSuccess(T t);

        protected void onFailure(int code, String msg){

        }

        //请求执行方法
        public void onSubscribe() {

        }

        //请求完成执行方法
        public void onComplete() {

        }

        public final io.reactivex.functions.Consumer getConsumer() {
            return (Consumer<T>) response -> {
                onSuccess(response);
            };
        }

    }
}
