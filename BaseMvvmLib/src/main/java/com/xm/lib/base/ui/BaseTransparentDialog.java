package com.xm.lib.base.ui;

import android.content.Context;

import androidx.annotation.NonNull;

import com.xm.lib.R;
import com.xm.lib.base.config.DataBindingConfig;
import com.xm.lib.base.model.BaseDialogViewModel;


/**
 * <pre>
 *     @author : Jerry
 *     time   : 2020/06/10
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class BaseTransparentDialog<T extends BaseDialogViewModel> extends BaseDialog<T> {

    /**
     * 透明背景弹窗
     * @param context
     * @param config
     */
    public BaseTransparentDialog(@NonNull Context context, DataBindingConfig config) {
        super(context, config, R.style.CustomTransparentDialogTheme);
    }


}
