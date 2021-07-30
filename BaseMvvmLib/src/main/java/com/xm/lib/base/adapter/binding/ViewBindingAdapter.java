package com.xm.lib.base.adapter.binding;

import android.view.View;

import androidx.databinding.BindingAdapter;

/**
 * Created by Juzhengyuan
 *
 * @Author: Jerry.
 * @Date: 2021/2/23 11
 * @Desc:
 */
public class ViewBindingAdapter {

    @BindingAdapter("v_selected")
    public static void setSelected(View mView,boolean isSelected){
        mView.setSelected(isSelected);
    }
}
