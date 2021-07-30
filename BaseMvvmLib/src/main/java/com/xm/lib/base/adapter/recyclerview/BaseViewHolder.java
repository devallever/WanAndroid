package com.xm.lib.base.adapter.recyclerview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

import com.xm.lib.manager.MeasureManager;


/**
 * Created by Android Studio.
 *
 * @author Jerry
 * Date: 2020/11/4 22:39
 * @description:
 */
public class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    protected Context mContext;
    private BaseRvCustomView mView = null;
    protected BaseRecyclerAdapter mAdapter;

    public BaseViewHolder(@NonNull View view) {
        super(view);
        mContext = view.getContext();
        if (view instanceof BaseRvCustomView) {
            this.mView = (BaseRvCustomView) view;
        }
    }

    protected void onConvert(T item, int position) {
        if (null != mView) {
            mView.convert(mAdapter, item, position);
            mView.getDataBinding().executePendingBindings();
        }
    }

    protected final void setAdapter(BaseRecyclerAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    protected <V extends View> V getItemView() {
        return (V) mView;
    }

    /**
     * 获取字符串资源
     *
     * @param stringId
     * @return
     */
    public String getString(@StringRes int stringId) {
        return mContext.getResources().getString(stringId);
    }

    /**
     * 获取Drawable资源
     *
     * @param resId
     * @param theme
     * @return
     */
    public Drawable getDrawable(@DrawableRes int resId, Resources.Theme theme) {
        return mContext.getResources().getDrawable(resId, theme);
    }

    /**
     * 获取颜色资源
     *
     * @param colorId
     * @return
     */
    public int getColor(@ColorRes int colorId) {
        return mContext.getResources().getColor(colorId);
    }

    /**
     * dp转px尺寸
     *
     * @param value
     * @return
     */
    public int dp2px(int value) {
        return MeasureManager.dip2px(mContext, value);
    }

    /**
     * sp转px尺寸
     *
     * @param value
     * @return
     */
    public int sp2px(int value) {
        return MeasureManager.sp2px(mContext, value);
    }

}
