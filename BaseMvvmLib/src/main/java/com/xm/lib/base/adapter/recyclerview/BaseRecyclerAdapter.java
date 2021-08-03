package com.xm.lib.base.adapter.recyclerview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by Android Studio.
 *
 * @author Jerry
 * Date: 2020/11/4 22:36
 * @description:
 */
public abstract class BaseRecyclerAdapter<T, VH extends BaseViewHolder<T>> extends PagingDataAdapter<T, VH> {

    private final String TAG = BaseRecyclerAdapter.class.getSimpleName();

    protected Context mContext;
    private ObservableList<T> mObservableList;
    protected RecyclerView mRvView;
    private OnItemClickedListener<T> onItemClickedListener;
    private OnItemChildViewClickedListener<T> onItemChildViewClickedListener;
    private boolean usePaging = false;

    public BaseRecyclerAdapter(DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
        usePaging = true;
        initObservableList();
    }

    public BaseRecyclerAdapter() {
        super(null);
        initObservableList();
    }

    public BaseRecyclerAdapter(List<T> mData) {
        super(null);
        initObservableList();
        if (null != mData) {
            mObservableList.addAll(mData);
        }
    }

    private void initObservableList() {
        mObservableList = new ObservableArrayList<>();
        mObservableList.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<T>>() {
            @Override
            public void onChanged(ObservableList<T> sender) {
//                LogPrint.e("onChanged -> " + sender, TAG);
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList<T> sender, int positionStart, int itemCount) {
//                LogPrint.i("onItemRangeChanged -> " + sender + "[start -> " + positionStart + " count -> " + itemCount + "]", TAG);
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList<T> sender, int positionStart, int itemCount) {
//                LogPrint.e("onItemRangeInserted -> " + sender + "[start -> " + positionStart + " count -> " + itemCount + "]", TAG);
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList<T> sender, int fromPosition, int toPosition, int itemCount) {
//                LogPrint.e("onItemRangeMoved -> " + sender + "[start -> " + fromPosition +" toPosition -> "+toPosition + " count -> " + itemCount + "]", TAG);
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeRemoved(ObservableList<T> sender, int positionStart, int itemCount) {
//                LogPrint.e("onItemRangeRemoved -> " + sender + "[start -> " + positionStart + " count -> " + itemCount + "]", TAG);
                notifyItemRangeRemoved(positionStart, itemCount);
            }
        });
    }


    public void setRecyclerView(RecyclerView mRvView) {
        this.mRvView = mRvView;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        VH mViewHolder = bindViewHolder(parent, viewType);
        if (null != mViewHolder) {
            mViewHolder.setAdapter(this);
        }
        return mViewHolder;
    }

    /**
     * 初始化ViewModel
     *
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract VH bindViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        if (null != holder) {
            holder.onConvert(getItem(position), position);
        }
    }

    @Override
    public int getItemCount() {
        if (usePaging) {
            //使用分页这个返回值无效
            return 0;
        }
        return null == getData() || getData().isEmpty() ? 0 : getData().size();
    }

    /**
     * 获取列表数据
     *
     * @return
     */
    public List<T> getData() {
        return mObservableList.isEmpty() ? null : mObservableList;
    }

    /**
     * 获取position的数据
     *
     * @param position
     * @return
     */
    public T getItemData(int position) {
        if (usePaging) {
            return getItem(position);
        }

        if (null != getData() && position >= 0 && position < getData().size()) {
            return getData().get(position);
        } else {
            return null;
        }
    }

    /**
     * add one new data in to certain location
     *
     * @param position
     */
    public final void addData(@IntRange(from = 0) int position, @NonNull T data) {
        mObservableList.add(position, data);
    }


    @Override
    public int getItemViewType(int position) {
        if(usePaging) {
            return registerViewType(getItem(position), position);
        }
        if (getData() == null) {
            return 0;
        }
        return registerViewType(getData().get(position), position);
    }

    /**
     * 注册不同的ViewType
     *
     * @param data
     * @param position
     * @return
     */
    protected int registerViewType(T data, int position) {
        return super.getItemViewType(position);
    }

    /**
     * add one new data
     */
    public final void addData(@NonNull T data) {
        mObservableList.add(data);
    }

    /**
     * 设置列表数据
     *
     * @param mData
     */
    public final void setNewData(List<T> mData) {
        mObservableList.clear();
        if (null != mData && mData.size() > 0) {
            mObservableList.addAll(mData);
        }
    }

    /**
     * 刷新列表局部数据
     *
     * @param mData
     */
    @Deprecated
    public final void notifyDataChanged(List<T> mData) {
        if (null == mData && mData.size() > 0) {
            return;
        }
        mObservableList.addAll(mData);
    }

    /**
     * 刷新列表局部数据
     *
     * @param mData
     */
    public final void notifyDataChanged2(List<T> mData) {
        if (null == mData) {
            return;
        }

        if (mData.size() <= 0) {
            return;
        }

        mObservableList.clear();
        mObservableList.addAll(mData);
    }

    public void setOnItemClickedListener(OnItemClickedListener<T> onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    public void setOnItemChildViewClickedListener(OnItemChildViewClickedListener<T> onItemChildViewClickedListener) {
        this.onItemChildViewClickedListener = onItemChildViewClickedListener;
    }

    /**
     * 设置item的点击事件回调
     *
     * @param v
     * @param position
     */
    public void callItemClicked(View v, int position) {
        if (null != onItemClickedListener) {
            onItemClickedListener.onItemClicked(v, position, getItem(position));
        }
    }

    /**
     * 设置item里面的子view点击回调
     *
     * @param v
     * @param position
     */
    public void callChildViewClicked(View v, int position) {
        if (null != onItemChildViewClickedListener) {
            onItemChildViewClickedListener.onChildViewClicked(v, position, getItem(position));
        }
    }

    public interface OnItemClickedListener<T> {
        //        void onItemClicked(BaseRecyclerAdapter rvAdapter, View v, int position);
        void onItemClicked(View v, int position, T item);
    }

    public interface OnItemChildViewClickedListener<T> {
        //        void onChildViewClicked(BaseRecyclerAdapter rvAdapter, View v, int position);
        void onChildViewClicked(View v, int position, T item);
    }
}
