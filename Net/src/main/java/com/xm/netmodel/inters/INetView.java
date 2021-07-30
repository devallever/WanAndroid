package com.xm.netmodel.inters;

/**
 * Created by BaseMvpLibs.
 * @Author: Jerry.
 * Date: 2020/10/12:16:24.
 * Desc:
 */
public interface INetView {

    /**
     * 显示加载弹窗
     */
    void showLoadingDialog();

    /**
     * 隐藏加载弹窗
     */
    void hideLoadingDialog();

    /**
     * 显示缺省页
     */
    void showDefaultView();
}
