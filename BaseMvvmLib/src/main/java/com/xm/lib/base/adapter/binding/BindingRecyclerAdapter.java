package com.xm.lib.base.adapter.binding;

import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.xm.lib.base.adapter.recyclerview.BaseRecyclerAdapter;
import com.xm.lib.manager.LogPrint;
import com.xm.lib.util.LogUtilsKt;

import java.util.List;


/**
 * Created by BaseMvpLibs.
 *
 * @Author: Jerry.
 * Date: 2020/10/11:17:15.
 * Desc:
 */
public class BindingRecyclerAdapter {

    /**
     * 初始化recyclerview，设置adapter，刷新数据
     *
     * @param recyclerView
     * @param adapter
     * @param layoutManager
     * @param mData
     */
    public static void initBaseRecyclerAdapter(RecyclerView recyclerView, BaseRecyclerAdapter adapter
            , RecyclerView.LayoutManager layoutManager, List<?> mData) {
        if (null == adapter) {
            return;
        }
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setRecyclerView(recyclerView);
        adapter.setNewData(mData);
    }

    /**
     * 初始化recyclerview，设置adapter，刷新数据
     *
     * @param recyclerView
     * @param adapter
     * @param layoutManager
     */
    public static void initBaseRecyclerAdapter(RecyclerView recyclerView, BaseRecyclerAdapter adapter
            , RecyclerView.LayoutManager layoutManager) {
        if (null == adapter) {
            return;
        }
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setRecyclerView(recyclerView);
    }

    public static void setupPagingRecyclerView(RecyclerView recyclerView, BaseRecyclerAdapter adapter, RecyclerView.LayoutManager layoutManager, boolean usePaging, LoadStateAdapter footerAdapter) {
        LogUtilsKt.log("setupPagingRecyclerView");
        if (null == adapter) {
            LogUtilsKt.log("adapter == null");
            return;
        }
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter.withLoadStateFooter(footerAdapter));
        adapter.setRecyclerView(recyclerView);
    }

    public static void setupPagingRecyclerView(RecyclerView recyclerView, BaseRecyclerAdapter adapter, RecyclerView.LayoutManager layoutManager, boolean usePaging, LoadStateAdapter headerAdapter,  LoadStateAdapter footerAdapter) {
        LogUtilsKt.log("setupPagingRecyclerView");
        if (null == adapter) {
            LogUtilsKt.log("adapter == null");
            return;
        }
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter.withLoadStateHeaderAndFooter(headerAdapter, footerAdapter));
        adapter.setRecyclerView(recyclerView);
    }


    public static void scrollToPosition(RecyclerView recyclerView, int position) {
        recyclerView.scrollToPosition(position);
    }

}
