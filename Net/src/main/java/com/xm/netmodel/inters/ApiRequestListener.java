package com.xm.netmodel.inters;

import com.xm.netmodel.model.NetModel;

public interface ApiRequestListener<M> {
    void onSuccessRequest(NetModel netModel,M data, boolean isEmpty, boolean isFirstPage);
    void onFailRequest(NetModel netModel, String msg);
}
