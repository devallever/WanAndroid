package com.xm.lib.base.config;

import androidx.annotation.LayoutRes;
import androidx.lifecycle.ViewModel;

import com.xm.lib.base.model.BaseModelHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BaseMvpLibs.
 * @Author: Jerry.
 * Date: 2020/10/9:14:07.
 * Desc:
 */
public class DataBindingConfig {

    private Map<Integer, BaseModelHandler> dataModels = new HashMap<>();

    private int layoutId = 0;
    private int vmKey = 0;
    private ViewModel mViewModel;


    public DataBindingConfig(int vmKey, ViewModel mViewModel) {
        this.vmKey = vmKey;
        this.mViewModel = mViewModel;
    }

    public DataBindingConfig(@LayoutRes int layoutId, int vmKey) {
        this.layoutId = layoutId;
        this.vmKey = vmKey;
    }

    public DataBindingConfig(@LayoutRes int layoutId, int vmKey, ViewModel mViewModel) {
        this.layoutId = layoutId;
        this.vmKey = vmKey;
        this.mViewModel = mViewModel;
    }

    public DataBindingConfig addModel(int key, BaseModelHandler handler) {
        if (null != handler){
            dataModels.put(key, handler);
        }
        return this;
    }

    public int getVmKey() {
        return vmKey;
    }

    public ViewModel getViewModel() {
        return mViewModel;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public Map<Integer, BaseModelHandler> getDataModels() {
        return dataModels;
    }
}
