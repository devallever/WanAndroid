package com.xm.lib.base.model;

import android.content.Context;

import com.xm.lib.base.ui.BaseDialog;

import java.lang.ref.SoftReference;

/**
 * Created by BaseMvpLibs.
 * @Author: Jerry.
 * Date: 2020/10/9:15:59.
 * Desc:
 */
public abstract class BaseDialogViewModel {

    protected BaseDialog mView;
    protected Context mCxt;

    /**
     * 初始化事件
     */
    protected void initEvent(){

    }


    /**
     * 销毁事件
     */
    protected abstract void destroyEvent();


    public final void binding(Context mCxt, BaseDialog mView) {
        this.mCxt = mCxt;
        this.mView = mView;
        initEvent();
    }

    public final void unBind() {
        destroyEvent();
        mView = null;
        mCxt = null;
    }
}
