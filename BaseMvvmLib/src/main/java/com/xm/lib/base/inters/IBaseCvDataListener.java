package com.xm.lib.base.inters;

import com.xm.lib.base.adapter.recyclerview.BaseRecyclerAdapter;

/**
 * @author Administrator
 */
public interface IBaseCvDataListener<T> {
    /**
     * 执行数据回调
     * @param adapter
     * @param data
     * @param position
     */
    void convert(BaseRecyclerAdapter adapter, T data, int position);
}
