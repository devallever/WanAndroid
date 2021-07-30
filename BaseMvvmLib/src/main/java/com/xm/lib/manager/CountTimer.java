package com.xm.lib.manager;

import android.os.CountDownTimer;

/**
 * <pre>
 *     @author : Jerry
 *     time   : 2020/06/06
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class CountTimer<T> extends CountDownTimer {

    private T obj;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public CountTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public T getTag() {
        return obj;
    }

    public void setTag(T obj) {
        this.obj = obj;
    }
}
