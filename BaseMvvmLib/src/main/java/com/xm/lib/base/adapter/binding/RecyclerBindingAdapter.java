package com.xm.lib.base.adapter.binding;

import androidx.databinding.BindingAdapter;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.xm.lib.base.adapter.recyclerview.BaseRecyclerAdapter;
import com.xm.lib.util.LogUtilsKt;

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

//    @BindingAdapter(value = {"rv_adapter", "rv_layoutManager"}, requireAll = true)
//    public static void initAdapter(RecyclerView recyclerView, BaseRecyclerAdapter adapter
//            , RecyclerView.LayoutManager layoutManager) {
//        BindingRecyclerAdapter.initBaseRecyclerAdapter(recyclerView, adapter, layoutManager);
//    }

    @BindingAdapter(value = {"rv_adapter", "rv_layoutManager", "usePaging", "footerAdapter"}, requireAll = true)
    public static void initPagingAdapter(RecyclerView recyclerView, BaseRecyclerAdapter adapter
            , RecyclerView.LayoutManager layoutManager, boolean usePaging, LoadStateAdapter footerAdapter) {
        LogUtilsKt.log("initPagingAdapter");
        BindingRecyclerAdapter.setupPagingRecyclerView(recyclerView, adapter, layoutManager, usePaging, footerAdapter);
    }

    @BindingAdapter(value = {"rv_adapter", "rv_layoutManager", "usePaging", "headerAdapter", "footerAdapter"}, requireAll = true)
    public static void initPagingAdapter(RecyclerView recyclerView, BaseRecyclerAdapter adapter
            , RecyclerView.LayoutManager layoutManager, boolean usePaging,LoadStateAdapter headerAdapter, LoadStateAdapter footerAdapter) {
        LogUtilsKt.log("initPagingAdapter");
        BindingRecyclerAdapter.setupPagingRecyclerView(recyclerView, adapter, layoutManager, usePaging, headerAdapter, footerAdapter);
    }


    @BindingAdapter(value = {"scrollToPosition"}, requireAll = true)
    public static void scrollToPosition(RecyclerView recyclerView, int position) {
        BindingRecyclerAdapter.scrollToPosition(recyclerView, position);
    }

}
