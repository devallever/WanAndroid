package com.xm.lib.base.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.xm.lib.base.config.DataBindingConfig;
import com.xm.lib.base.model.BaseCvModel;
import com.xm.lib.base.model.BaseModelHandler;
import com.xm.lib.manager.MeasureManager;

import java.util.Map;

/**
 * Created by Juzhengyuan
 *
 * @Author: Jerry.
 * @Date: 2020/11/13 11
 * @Desc:
 */
public abstract class BaseCustomView<VM extends BaseCvModel> extends LinearLayout {

    private Context mCxt;
    private ViewDataBinding mDataBinding;
    protected VM mViewModel;

    public BaseCustomView(Context context) {
        super(context);
        init();
    }

    public BaseCustomView(Context context,Object... objects) {
        super(context);
        config(objects);
        init();
    }

    public BaseCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BaseCustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        this.mCxt = getContext();
        DataBindingConfig config = getDataBindingConfig();
        if (null == config) {
            throw new NullPointerException("DataBindingConfig need to init");
        }
        LayoutInflater mLayoutInflater = (LayoutInflater) mCxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDataBinding = DataBindingUtil.inflate(mLayoutInflater, config.getLayoutId(), this, false);
        VM viewModel = getViewModel();
        if (null == viewModel) {
            throw new NullPointerException("viewModel need to init");
        }
        viewModel.setContextAndView(mCxt, this);
        mViewModel = viewModel;
        mDataBinding.setVariable(config.getVmKey(), viewModel);
        Map<Integer, BaseModelHandler> models = config.getDataModels();
        if (null != models && !models.isEmpty()) {
            for (Integer key : models.keySet()) {
                BaseModelHandler modelHandler = models.get(key);
                modelHandler.initEvent();
                mDataBinding.setVariable(key, modelHandler);
            }
        }
        setOrientation(LinearLayout.VERTICAL);
        addView(mDataBinding.getRoot());

        convert(mDataBinding.getRoot());
    }

    protected void config(Object... objects) {

    }

    public final <VH extends ViewDataBinding> VH getDataBinding() {
        return (VH) mDataBinding;
    }

    /**
     * 获取字符串资源
     *
     * @param stringId
     * @return
     */
    public String getString(@StringRes int stringId) {
        return mCxt.getResources().getString(stringId);
    }

    /**
     * 获取Drawable资源
     *
     * @param resId
     * @param theme
     * @return
     */
    public Drawable getDrawable(@DrawableRes int resId, Resources.Theme theme) {
        return mCxt.getResources().getDrawable(resId, theme);
    }

    /**
     * 获取颜色资源
     *
     * @param colorId
     * @return
     */
    public int getColor(@ColorRes int colorId) {
        return mCxt.getResources().getColor(colorId);
    }

    /**
     * dp转px尺寸
     *
     * @param value
     * @return
     */
    public int dp2px(int value) {
        return MeasureManager.dip2px(mCxt, value);
    }

    /**
     * sp转px尺寸
     *
     * @param value
     * @return
     */
    public int sp2px(int value) {
        return MeasureManager.sp2px(mCxt, value);
    }

    /**
     * 获取ViewModel对象
     * @return
     */
    protected abstract VM getViewModel();

    /**
     * 获取DataBindingConfig对象
     * @return
     */
    protected abstract DataBindingConfig getDataBindingConfig();

    /**
     * 初始化方法
     * @param root
     */
    protected abstract void convert(View root);
}
