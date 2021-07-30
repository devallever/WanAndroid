package com.xm.lib.base.model;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;


import com.xm.lib.base.config.DataBindingConfig;

import java.util.Map;


/**
 * <pre>
 *     @author : Jerry
 *     time   : 2020/06/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class BaseActonBarModel extends ViewModel implements LifecycleObserver{

    private ViewDataBinding mDataBinding;
    protected View mBar;
    private int statusBarColor = 0;
    private DataBindingConfig config;
    protected Context mCxt;

    public BaseActonBarModel(DataBindingConfig config) {
        this.config = config;
        if (null == config) {
            throw new NullPointerException("Please initialize DataBindingConfig");
        }
    }

    @ColorRes
    public int initStatusBarColor(){
        return 0;
    }

    @ColorInt
    public final int getStatusBarColor() {
        return statusBarColor;
    }


    /**
     * 初始化actionbar和绑定viewModel
     * @param act
     * @param mContentLayout
     */
    public final void initBaseTitleView(Activity act, ViewGroup mContentLayout) {
        this.mCxt = act;
        ViewDataBinding dataBinding = DataBindingUtil.inflate(act.getLayoutInflater(), config.getLayoutId(), mContentLayout, true);
        dataBinding.setVariable(config.getVmKey(), this);
        Map<Integer, BaseModelHandler> models = config.getDataModels();
        if (null != models && !models.isEmpty()) {
            for (Integer key : models.keySet()) {
                BaseModelHandler modelHandler = models.get(key);
                modelHandler.initEvent();
                mDataBinding.setVariable(key, modelHandler);
            }
        }
        mBar = dataBinding.getRoot();
        int barColor = initStatusBarColor();
        if (0 != barColor) {
            statusBarColor = act.getResources().getColor(barColor);
            mContentLayout.setBackgroundColor(statusBarColor);
            mBar.setBackgroundColor(statusBarColor);
        }else{
            Drawable background = mBar.getBackground();
            if (background instanceof ColorDrawable) {
                statusBarColor = ((ColorDrawable)background).getColor();
            }
        }
        mDataBinding = dataBinding;
        convert(mBar);
    }


    /**
     * 获取对应的ViewDataBinding对象
     * @param <VM>
     * @return
     */
    protected <VM extends ViewDataBinding> VM getDataBinding() {
        return (VM) mDataBinding;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public final void onDestroy(LifecycleOwner owner) {
        destroy();
        mCxt = null;
    }

    protected abstract void destroy();

    protected abstract void convert(View view);

}
