package com.xm.netmodel.inters;

import com.xm.netmodel.enums.LoginState;

/**
 * Created by MvvmLib
 *
 * @Author: Jerry.
 * @Date: 2020/11/12 16
 * @Desc:
 */
public interface LoginStateListener {

    /**
     * 登录状态改变
     * @param state
     * @param message
     */
    void onStateChanged(LoginState state, String message);

}
