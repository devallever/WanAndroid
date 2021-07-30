package com.xm.lib.base.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.WindowManager;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;


import com.xm.lib.R;
import com.xm.lib.base.config.DataBindingConfig;
import com.xm.lib.base.model.BaseDialogViewModel;
import com.xm.lib.base.model.BaseModelHandler;
import com.xm.lib.manager.MeasureManager;

import java.util.Map;


/**
 * <pre>
 *     @author : Jerry
 *     time   : 2020/06/10
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class BaseDialog<T extends BaseDialogViewModel> extends Dialog {

    protected Context mCxt;
    private DataBindingConfig bindingConfig;
    private ViewDataBinding mBinding;
    protected T mViewModel;

    protected abstract T initViewModel();

    protected abstract void initView();

    public BaseDialog(@NonNull Context context, DataBindingConfig config) {
        super(context, R.style.CustomDialogTheme);
        this.mCxt = context;
        this.bindingConfig = config;
        if (null == bindingConfig) {
            throw new NullPointerException("Please initialize DataBindingConfig");
        }
    }

    public BaseDialog(@NonNull Context context, DataBindingConfig config, @StyleRes int styleId) {
        super(context, styleId);
        this.mCxt = context;
        this.bindingConfig = config;
        if (null == bindingConfig) {
            throw new NullPointerException("Please initialize DataBindingConfig");
        }
    }

    /**
     * 获取对应的ViewDataBinding对象
     * @param <VM>
     * @return
     */
    protected <VM extends ViewDataBinding> VM getDataBinding() {
        return (VM) mBinding;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        initAttributes();
        initView();
    }

    private void initAttributes() {
        DisplayMetrics metrics = mCxt.getResources().getDisplayMetrics();
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        setLayoutAttributes(attributes,metrics.widthPixels,metrics.heightPixels);
        getWindow().setAttributes(attributes);
    }

    protected void setLayoutAttributes(WindowManager.LayoutParams attributes, int widthPixels, int heightPixels){

    }

    /**
     * 初始化绑定ViewModel
     */
    private void initDataBinding() {
        mViewModel = initViewModel();
        ViewDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(mCxt)
                , bindingConfig.getLayoutId(), null, false);
        setContentView(dataBinding.getRoot());
        dataBinding.setVariable(bindingConfig.getVmKey(), mViewModel);
        Map<Integer, BaseModelHandler> models = bindingConfig.getDataModels();
        if (null != models && !models.isEmpty()) {
            for (Integer key : models.keySet()) {
                BaseModelHandler modelHandler = models.get(key);
                modelHandler.initEvent();
                mBinding.setVariable(key, modelHandler);
            }
        }
        mBinding = dataBinding;
        mViewModel.binding(mCxt, this);
    }

    public BaseDialog showDialog(){
        if (!isShowing()) {
            show();
        }
        return this;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (null != mViewModel) {
            mViewModel.unBind();
            mViewModel = null;
        }
        if (null != mBinding) {
            mBinding.unbind();
        }
    }

    //=======================资源获取和测量尺寸方法 start==============//

    public String getStringRes(@StringRes int stringId) {
        return mCxt.getResources().getString(stringId);
    }

    public Drawable getDrawableRes(@DrawableRes int resId, Resources.Theme theme) {
        return  mCxt.getResources().getDrawable(resId, theme);
    }

    public int getColorRes(@ColorRes int colorId) {
        return mCxt.getResources().getColor(colorId);
    }

    public int dp2px(int value) {
        return MeasureManager.dip2px(mCxt, value);
    }

    public int px2dp(int value) {
        return MeasureManager.px2dp(mCxt, value);
    }

    public int sp2px(int value) {
        return MeasureManager.sp2px(mCxt, value);
    }

    //=======================资源获取和测量尺寸方法 end==============//

}
