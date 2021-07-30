package com.xm.lib.widget.adapter;

import android.view.View;

/**
 * Created by BaseMvpLibs.
 * Author: Jerry.
 * Date: 2020/9/21:11:16.
 * Desc:
 */
public interface DataLoadAdapter<V extends View,T> {
     void onLoading(V v, T t, int position);
}
