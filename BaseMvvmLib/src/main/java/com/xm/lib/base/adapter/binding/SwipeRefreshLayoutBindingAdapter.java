package com.xm.lib.base.adapter.binding;

import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class SwipeRefreshLayoutBindingAdapter {

    @BindingAdapter(value = {"refreshStatus"}, requireAll = true)
    public static void scrollToPosition(SwipeRefreshLayout swipeRefreshLayout, boolean refreshStatus) {
        swipeRefreshLayout.setRefreshing(refreshStatus);
    }
}
