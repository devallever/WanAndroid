package com.xm.lib.base.inters;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;


/**
 * Created by BaseMvpLibs.
 * @Author: Jerry.
 * Date: 2020/10/9:18:10.
 * Desc:
 */
public interface IBaseModel<V extends IBaseView> {

    /**
     * 绑定数据
     * @param mCxt
     * @param mView
     * @param mOwner
     */
    void binding(Context mCxt, V mView, LifecycleOwner mOwner);

}
