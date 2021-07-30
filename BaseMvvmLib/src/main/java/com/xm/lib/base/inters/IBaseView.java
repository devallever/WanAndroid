package com.xm.lib.base.inters;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.lifecycle.LifecycleObserver;

/**
 * Created by BaseMvpLibs.
 * @Author: Jerry.
 * Date: 2020/10/9:18:08.
 * Desc:
 */
public interface IBaseView {

    /**
     * 获取View对象
     * @param <T>
     * @return
     */
    <T extends IBaseView> T getBaseView();

    /**
     * 打开Activity
     * @param clz
     * @param isCloseActivity
     */
    void open(Class<? extends Activity> clz, boolean isCloseActivity);

    /**
     * 打开Activity
     * @param intent
     * @param isCloseActivity
     */
    void open(Intent intent, boolean isCloseActivity);

    /**
     *  打开Activity
     * @param intent
     * @param resultCode
     */
    void open(int resultCode, Intent intent);

    /**
     * 返回code
     * @return
     */
    int getResultCode();

    /**
     * 获取文本资源
     * @param stringId
     * @return
     */
    String getStringRes(@StringRes int stringId);

    /**
     * 获取Drawable资源
     * @param resId
     * @param theme
     * @return
     */
    Drawable getDrawableRes(@DrawableRes int resId, Resources.Theme theme);

    /**
     * 获取某一个View
     * @param viewId
     * @param <V>
     * @return
     */
    <V extends View> V getChildView(@IdRes int viewId);

    /**
     * 获取颜色资源
     * @param colorId
     * @return
     */
    int getColorRes(@ColorRes int colorId);

    /**
     * 获取dp转px值
     * @param value
     * @return
     */
    int dp2px(int value);

    /**
     * 获取sp转px值
     * @param value
     * @return
     */
    int sp2px(int value);

    /**
     * 获取px转dp值
     * @param value
     * @return
     */
    int px2dp(int value);

    /**
     * 添加LifecycleObserver
     * @param observer
     */
    void registerLifecycleObserver(LifecycleObserver observer);

    /**
     * 移除LifecycleObserver
     * @param observer
     */
    void unregisterLifecycleObserver(LifecycleObserver observer);
}
