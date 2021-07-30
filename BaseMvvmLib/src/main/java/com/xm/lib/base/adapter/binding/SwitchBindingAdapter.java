package com.xm.lib.base.adapter.binding;

import android.widget.Switch;

import androidx.appcompat.widget.SwitchCompat;
import androidx.databinding.BindingAdapter;

public class SwitchBindingAdapter {
    @BindingAdapter(
            value = {"select"}
            , requireAll = false)
    public void setSwitch(SwitchCompat switchCompat, boolean checked) {
        switchCompat.setChecked(checked);
    }

    @BindingAdapter(
            value = {"select"}
            , requireAll = false)
    public void setSwitch(Switch switchView, boolean checked) {
        switchView.setChecked(checked);
    }
}
