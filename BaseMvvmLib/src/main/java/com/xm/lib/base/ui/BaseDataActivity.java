package com.xm.lib.base.ui;

import android.graphics.drawable.Drawable;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.xm.lib.base.config.DataBindingConfig;
import com.xm.lib.base.model.BaseViewModel;
import com.xm.lib.base.model.BaseModelHandler;
import com.xm.lib.manager.ReflectionUtils;

import java.util.Map;

/**
 * Created by BaseMvpLibs.
 *
 * @Author: Jerry.
 * Date: 2020/10/9:14:05.
 * Desc:
 */
public abstract class BaseDataActivity<DB extends ViewDataBinding, T extends BaseViewModel> extends BaseActivity{

    protected DB mBinding;
    protected DataBindingConfig bindingConfig;
    protected T mViewModel;

    protected abstract DataBindingConfig initDataBindingConfig();


    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        if (null !=  mBinding) {
            mBinding.unbind();
        }
    }

    //=======================初始化DataBinding和初始化view方法 start==============//

    /**
     * 获取对应的ViewDataBinding对象
     *
     * @param <VM>
     * @return
     */
    protected <VM extends ViewDataBinding> VM getDataBinding() {
        return (VM) mBinding;
    }

    @Override
    protected final int getLayoutId() {
        return 0;
    }

    @Override
    protected void initConfig() {
        bindingConfig = initDataBindingConfig();
        if (null == bindingConfig) {
            throw new NullPointerException("Please initialize DataBindingConfig");
        }
    }

    /**
     * 初始化绑定ViewModel
     *
     * @param dataBinding
     */
    protected void initDataBinding(ViewDataBinding dataBinding) {
        mViewModel = initViewModel();
        dataBinding.setVariable(bindingConfig.getVmKey(), mViewModel);
        mViewModel.binding(this, this, this);
        registerLifecycleObserver(mViewModel);
        dataBinding.setLifecycleOwner(this);
        Map<Integer, BaseModelHandler> models = bindingConfig.getDataModels();
        if (null != models && !models.isEmpty()) {
            for (Integer key : models.keySet()) {
                BaseModelHandler modelHandler = models.get(key);
                dataBinding.setVariable(key, modelHandler);
                modelHandler.setViewModel(mViewModel);
                modelHandler.initEvent();
            }
        }
        mBinding = (DB) dataBinding;
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
     * 重写initBaseRootView方法实现自定义的
     * 初始化布局文件
     */
    @Override
    protected void initBaseRootView() {
        //初始化自定义标题栏
        ViewGroup.LayoutParams params = mTitleLayout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        mTitleLayout.setLayoutParams(params);
        mTitleLayout = null;

        //初始化内容
        ViewDataBinding dataBinding = DataBindingUtil.inflate(getLayoutInflater()
                , bindingConfig.getLayoutId(), (ViewGroup) mContentLayout, true);
        initDataBinding(dataBinding);
        Drawable background = dataBinding.getRoot().getBackground();
        if (null != background) {
            mContentLayout.setBackground(background);
            mBgLayout.setBackground(background);
        }
    }


}
