package com.xm.lib.base.adapter.binding;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.xm.lib.base.adapter.recyclerview.BaseRecyclerAdapter;

import java.util.List;


/**
 * Created by BaseMvpLibs.
 * @Author: Jerry.
 * Date: 2020/10/11:17:15.
 * Desc:
 */
public class RecyclerBindingAdapter {

    @BindingAdapter(value = {"rvAdapter", "rvLayoutManager", "rvData"}, requireAll = true)
    public static void initBaseRecyclerAdapter(RecyclerView recyclerView, BaseRecyclerAdapter adapter
            , RecyclerView.LayoutManager layoutManager, List<?> mData) {
        BindingRecyclerAdapter.initBaseRecyclerAdapter(recyclerView, adapter, layoutManager, mData);
    }

    @BindingAdapter(value = {"rv_adapter", "rv_layoutManager"}, requireAll = true)
    public static void initAdapter(RecyclerView recyclerView, BaseRecyclerAdapter adapter
            , RecyclerView.LayoutManager layoutManager) {
        BindingRecyclerAdapter.initBaseRecyclerAdapter(recyclerView, adapter, layoutManager);
    }

}
