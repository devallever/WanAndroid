package com.xm.netmodel.inters;


import com.xm.netmodel.model.BaseNetModel;

public interface HttpRequestListener<T> {
    void onFailRequest(BaseNetModel netModel, String msg);
    void onSuccessRequest(BaseNetModel netModel, T data, boolean isEmpty);
}
