package com.xm.lib.base.model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.lifecycle.ViewModel;

import com.xm.lib.base.ui.BaseDataActivity;
import com.xm.lib.manager.MeasureManager;
import com.xm.lib.manager.OpenManager;

import java.lang.ref.WeakReference;

/**
 * Created by Juzhengyuan
 *
 * @Author: Jerry.
 * @Date: 2020/12/10 17
 * @Desc:
 */
public abstract class BaseModelHandler<VM extends BaseViewModel> extends ViewModel {

    protected WeakReference<Context> mCxtWr;

    private VM mViewModel;

    public BaseModelHandler(Context mCxt) {
        this.mCxtWr = new WeakReference<>(mCxt);
    }

    public void setViewModel(VM mViewModel) {
        this.mViewModel = mViewModel;
    }

    public VM getViewModel() {
        return mViewModel;
    }

    public void initEvent(){

    }

    public void destroyEvent(){

    }

    //=======================资源获取和测量尺寸方法 start==============//

    public final String getStringRes(@StringRes int stringId) {
        return mCxtWr.get().getResources().getString(stringId);
    }

    public final  Drawable getDrawableRes(@DrawableRes int resId, Resources.Theme theme) {
        return mCxtWr.get().getResources().getDrawable(resId, theme);
    }

    public final int getColorRes(@ColorRes int colorId) {
        return mCxtWr.get().getResources().getColor(colorId);
    }

    public final int dp2px(int value) {
        return MeasureManager.dip2px(mCxtWr.get(), value);
    }

    public final int px2dp(int value) {
        return MeasureManager.px2dp(mCxtWr.get(), value);
    }

    public final int sp2px(int value) {
        return MeasureManager.sp2px(mCxtWr.get(), value);
    }

    //=======================资源获取和测量尺寸方法 end==============//


}
