package com.xm.lib.base.config;

/**
 * Created by BaseMvpLibs.
 * @Author: Jerry.
 * Date: 2020/10/12:15:20.
 * Desc:
 */
public abstract class NetConfig {

    private OnLoginStateCallBack callBack;

    /**
     * 设置退出登录方法
     */
    protected abstract void setLoginOutCallBack();

    public NetConfig() {
        setLoginOutCallBack();
    }

    protected final void callLoginOut(Object obj){
        if (null != callBack) {
            callBack.onLoginOut(obj);
        }
    }

    public final void setCallBack(OnLoginStateCallBack callBack) {
        this.callBack = callBack;
    }


    public interface OnLoginStateCallBack{
        /**
         * 出登录回调
         * @param obj
         */
        void onLoginOut(Object obj);
    }
}
