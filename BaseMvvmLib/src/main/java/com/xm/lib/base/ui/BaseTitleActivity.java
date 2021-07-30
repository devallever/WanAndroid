package com.xm.lib.base.ui;

import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.xm.lib.base.model.BaseActonBarModel;
import com.xm.lib.base.model.BaseViewModel;
import com.xm.lib.manager.LogPrint;

/**
 * Created by BaseMvpLibs.
 *
 * @Author: Jerry.
 * Date: 2020/10/9:14:05.
 * Desc:
 */
public abstract class BaseTitleActivity<DB extends ViewDataBinding, T extends BaseViewModel, TM extends BaseActonBarModel>
            extends BaseDataActivity<DB, T>{

    protected TM mActionBarModel;

    /**
     * 自定义标题栏ViewModel
     *
     * @return
     */
    protected abstract TM initCustomTitleBar();

    /**
     * 初始化布局文件
     */
    @Override
    protected void initBaseRootView() {
        //初始化自定义标题栏
        mActionBarModel = initCustomTitleBar();

        //初始化标题
        mActionBarModel.initBaseTitleView(this, (ViewGroup) mTitleLayout);

        //初始化内容
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mContentLayout.getLayoutParams();
        params.addRule(RelativeLayout.BELOW, mTitleLayout.getId());

        ViewDataBinding dataBinding = DataBindingUtil.inflate(getLayoutInflater()
                , bindingConfig.getLayoutId(), (ViewGroup) mContentLayout, true);
        initDataBinding(dataBinding);
        registerLifecycleObserver(mActionBarModel);
        if (0 != mActionBarModel.getStatusBarColor()) {
            mBgLayout.setBackgroundColor(mActionBarModel.getStatusBarColor());
        }
    }

}
