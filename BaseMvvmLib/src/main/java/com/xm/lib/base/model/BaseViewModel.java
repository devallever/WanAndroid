package com.xm.lib.base.model;

import android.app.Activity;
import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.xm.lib.base.inters.IBaseModel;
import com.xm.lib.base.inters.IBaseView;
import com.xm.lib.manager.ToastManager;

/**
 * Created by BaseMvpLibs.
 * @Author: Jerry.
 * Date: 2020/10/9:15:59.
 * Desc:
 */
public abstract class BaseViewModel<V extends IBaseView> extends ViewModel implements IBaseModel<V>, LifecycleObserver {

    protected V mView;
    protected Context mCxt;
    protected LifecycleOwner mOwner;

    /**
     * 初始化事件
     */
    protected abstract void onCreated();

    /**
     * 页面销毁事件
     */
    protected abstract void destroyEvent();

    /**
     * 页面显示或隐藏
     * @param isHide
     */
    protected void hideOrDisplay(boolean isHide){

    }

    @Override
    public final void binding(Context mCxt,V mView,LifecycleOwner mOwner) {
        this.mView = mView;
        this.mOwner = mOwner;
        this.mCxt = mCxt;
        onCreated();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void unBind(LifecycleOwner owner) {
        destroyEvent();
        mCxt = null;
        mOwner = null;
        mView = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public final void onPause(LifecycleOwner owner){
        hideOrDisplay(true);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public final void onResume(LifecycleOwner owner){
        hideOrDisplay(false);
    }

    public void finish() {
        if (mCxt instanceof Activity) {
            ((Activity) mCxt).finish();
        }
    }

    public void toast(String msg) {
        if (mCxt instanceof Activity) {
            ((Activity) mCxt).runOnUiThread(() -> {
                ToastManager.showDefaultShort(msg);
            });
        }
    }
}
