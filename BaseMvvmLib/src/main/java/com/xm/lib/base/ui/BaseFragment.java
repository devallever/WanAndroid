package com.xm.lib.base.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleObserver;

import com.xm.lib.base.config.DataBindingConfig;
import com.xm.lib.base.inters.IBaseView;
import com.xm.lib.base.model.BaseModelHandler;
import com.xm.lib.base.model.BaseViewModel;
import com.xm.lib.manager.LifecycleManager;
import com.xm.lib.manager.LogPrint;
import com.xm.lib.manager.MeasureManager;
import com.xm.lib.manager.OpenManager;
import com.xm.lib.manager.ReflectionUtils;

import java.util.Map;

/**
 * Created by BaseMvpLibs.
 * Author: Jerry.
 * Date: 2020/10/11:11:24.
 * Desc:
 */
public abstract class BaseFragment<DB extends ViewDataBinding, T extends BaseViewModel> extends Fragment implements IBaseView {

    private DataBindingConfig bindingConfig;
    protected T mViewModel;
    protected DB mBinding;
    private View mRoot;
    protected Context mCxt;
    private boolean isLoadFirst;
    private boolean isInitMarginTop = false;

    protected abstract DataBindingConfig initDataBindingConfig();

    protected abstract void initDataAndEvent();

    protected abstract void destroyView();

    @Override
    public <V extends IBaseView> V getBaseView() {
        return (V) this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bindingConfig = initDataBindingConfig();
        if (null == bindingConfig) {
            throw new NullPointerException("Please initialize DataBindingConfig");
        }
        mBinding = DataBindingUtil.inflate(inflater, bindingConfig.getLayoutId(), container, false);
        this.mRoot = mBinding.getRoot();
        return mBinding.getRoot();
    }

    protected void onConfig(Bundle savedInstanceState){

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("BaseFragment", "onViewCreated: " + this.hashCode());
        this.mCxt = getActivity();
        if (isUseImmersionBar()) {
            View mTopView = getTopView();
            if (null != mTopView && !isInitMarginTop) {
                try{
                    isInitMarginTop = true;
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mTopView.getLayoutParams();
                    params.topMargin += MeasureManager.getStatusBarHeight(getContext());
                    mTopView.setLayoutParams(params);
                }catch (Exception e){
                    LogPrint.d("bar error:"+e.getMessage());
                    mTopView.setPadding(0, MeasureManager.getStatusBarHeight(getContext()), 0, 0);
                }
            }
        }
        initDataBinding();
        if (!isUseLazyLoading()) {
            initDataAndEvent();
        }
        onConfig(savedInstanceState);
    }

    private void initDataBinding() {
        mViewModel = initViewModel();
        mBinding.setLifecycleOwner(this);
        mBinding.setVariable(bindingConfig.getVmKey(), mViewModel);
        Map<Integer, BaseModelHandler> models = bindingConfig.getDataModels();
        if (null != models && !models.isEmpty()) {
            for (Integer key : models.keySet()) {
                BaseModelHandler modelHandler = models.get(key);
                modelHandler.setViewModel(mViewModel);
                modelHandler.initEvent();
                mBinding.setVariable(key, modelHandler);
            }
        }
        mViewModel.binding(mCxt, this, this);
        registerLifecycleObserver(mViewModel);
    }

    protected final void setStatusBarDarkFont(boolean isDarkFont){
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity)getActivity()).mImmersionBar
                    .statusBarDarkFont(isDarkFont).init();
        }
    }

    public <V extends View> V getItemView(@IdRes int viewId) {
        return mRoot.findViewById(viewId);
    }

    //获取对应的ViewDataBinding对象
    protected <VM extends ViewDataBinding> VM getDataBinding() {
        return (VM) mBinding;
    }

    /**
     * 通过反射获取activity的ViewModel对象
     */
    private T initViewModel() {
        try {
            // 通过反射获取model的真实类型
            Class<T> clazz = ReflectionUtils.getGenericCls(this.getClass(), 1);
            // 通过反射创建model的实例
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 是否使用ImmersionBar
     * 默认开启使用
     */
    protected boolean isUseImmersionBar() {
        return true;
    }

    /**
     * 是否使用异加载
     */
    protected boolean isUseLazyLoading() {
        return false;
    }

    //=======================初始化状态栏方法 start==============//


    protected View getTopView() {
        return 0 == topView() ? mRoot : getItemView(topView());
    }

    /**
     * 顶部barView id
     *
     * @return
     */
    @IdRes
    protected int topView() {
        return 0;
    }

    //=======================初始化状态栏方法 end==============//

    @Override
    public void onResume() {
        super.onResume();
        LifecycleManager.getManager().putOwner(getClass().getSimpleName(),this);
        if (isUseLazyLoading() && !isLoadFirst && !isHidden() && needReInitData()) {
            isLoadFirst = true;
            initDataAndEvent();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        isLoadFirst = false;
        onHide();
    }

    protected void onHide() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        destroyView();
        LifecycleManager.getManager().remove(getClass().getSimpleName());
        if (null != mViewModel) {
            unregisterLifecycleObserver(mViewModel);
            mViewModel = null;
        }
        if (null != bindingConfig) {
            Map<Integer, BaseModelHandler> models = bindingConfig.getDataModels();
            if (null != models && !models.isEmpty()) {
                for (Integer key : models.keySet()) {
                    BaseModelHandler modelHandler = models.get(key);
                    modelHandler.destroyEvent();
                }
            }
        }
        isInitMarginTop = false;
        if (null != mBinding) {
            mBinding.unbind();
        }
    }

    @Override
    public String getStringRes(@StringRes int stringId) {
        return getActivity().getResources().getString(stringId);
    }

    @Override
    public Drawable getDrawableRes(@DrawableRes int resId, Resources.Theme theme) {
        return getActivity().getResources().getDrawable(resId, theme);
    }

    @Override
    public int getColorRes(@ColorRes int colorId) {
        return getActivity().getResources().getColor(colorId);
    }

    @Override
    public int dp2px(int value) {
        return MeasureManager.dip2px(getActivity(), value);
    }

    @Override
    public int sp2px(int value) {
        return MeasureManager.sp2px(getActivity(), value);
    }

    @Override
    public int px2dp(int value) {
        return MeasureManager.px2dp(mCxt, value);
    }

    @Override
    public void open(Class<? extends Activity> clz, boolean isCloseActivity) {
        OpenManager.getOpenManager().open(getActivity(), clz, isCloseActivity);
    }

    @Override
    public void open(Intent intent, boolean isCloseActivity) {
        OpenManager.getOpenManager().open(getActivity(), intent, isCloseActivity);
    }

    @Override
    public void open(int resultCode, Intent intent) {
        OpenManager.getOpenManager().open(getActivity(), intent, resultCode);
    }

    @Override
    public <V extends View> V getChildView(int viewId) {
        return mRoot.findViewById(viewId);
    }

    @Override
    public int getResultCode() {
        return OpenManager.getOpenManager().getResultCode(getActivity());
    }

    @Override
    public void registerLifecycleObserver(LifecycleObserver observer) {
        if (null == observer) {
            return;
        }
        getLifecycle().addObserver(observer);
    }

    @Override
    public void unregisterLifecycleObserver(LifecycleObserver observer) {
        if (null == observer) {
            return;
        }
        getLifecycle().removeObserver(observer);
    }

    protected boolean needReInitData() {
        return true;
    }
}
