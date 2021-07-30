package com.xm.lib.base.adapter.recyclerview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.lifecycle.ViewModel;

import com.xm.lib.manager.MeasureManager;


/**
 * @author Administrator
 */
public class BaseRvCustomViewModel extends ViewModel {

    protected Context mCxt;
    protected BaseRvCustomView mRoot;

    protected final void setContextAndView(Context mCxt, BaseRvCustomView root) {
        this.mCxt = mCxt;
        this.mRoot = root;
        initEvent();
    }

    protected void initEvent() {
    }

    public <V extends BaseRvCustomView> V getCustomView() {
        return (V) mRoot;
    }

    /**
     * 获取字符串资源
     * @param stringId
     * @return
     */
    public String getString(@StringRes int stringId) {
        return mCxt.getResources().getString(stringId);
    }

    /**
     * 获取Drawable资源
     * @param resId
     * @param theme
     * @return
     */
    public Drawable getDrawable(@DrawableRes int resId, Resources.Theme theme) {
        return mCxt.getResources().getDrawable(resId, theme);
    }

    /**
     * 获取颜色资源
     * @param colorId
     * @return
     */
    public int getColor(@ColorRes int colorId) {
        return mCxt.getResources().getColor(colorId);
    }

    /**
     * dp转px尺寸
     * @param value
     * @return
     */
    public int dp2px(int value) {
        return MeasureManager.dip2px(mCxt, value);
    }

    /**
     * sp转px尺寸
     * @param value
     * @return
     */
    public int sp2px(int value) {
        return MeasureManager.sp2px(mCxt, value);
    }
}
