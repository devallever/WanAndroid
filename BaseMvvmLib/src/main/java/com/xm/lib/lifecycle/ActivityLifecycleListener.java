package com.xm.lib.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xm.lib.BaseApp;
import com.xm.lib.base.config.NetConfig;
import com.xm.lib.base.ui.BaseDataActivity;
import com.xm.lib.manager.CacheManager;
import com.xm.lib.manager.IntentManager;
import com.xm.lib.manager.LifecycleManager;
import com.xm.lib.receiver.NetReceiver;
import com.xm.lib.receiver.NetUtil;


/**
 * @ClassName ActivityLifecycleListener
 * @Description TODO
 * @Author Jerry
 * @Date 2020/5/4 14:55
 * @Version 1.0
 */
public class ActivityLifecycleListener implements Application.ActivityLifecycleCallbacks {

    private NetReceiver netReceiver;
    private Class actLoginOutCls;
    private BaseApp app;

    public ActivityLifecycleListener(BaseApp app) {
        this.app = app;
    }

    public ActivityLifecycleListener(BaseApp app, Class<? extends Activity> actLoginOutCls) {
        this.actLoginOutCls = actLoginOutCls;
        this.app = app;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        ActivityManager.getInstance().put(activity);
        initNetReceiver();
        if (1 == ActivityManager.getInstance().size()) {
            initLoginOutListener();
        }
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        ActivityManager.getInstance().setCurrent(activity);
        if (activity instanceof BaseDataActivity) {
            ((BaseDataActivity) activity).netChanged(0 >= NetUtil.getNetWorkState(activity));
        }
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        if (1 == ActivityManager.getInstance().size()) {
            unregisterNetReceiver();
            LifecycleManager.getManager().clear();
        }
        ActivityManager.getInstance().remove(activity);
    }

    /**
     * 注册网络变化广播接收
     * 判断是否有网络
     * netType 0 移动网络 1 无线网络 -1 没有网络
     */
    private synchronized void initNetReceiver() {
        if (null != netReceiver) {
            return;
        }
        //实例化IntentFilter对象
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        netReceiver = new NetReceiver(status -> {
            Activity current = ActivityManager.getInstance().getCurrent();
            if (null != current && current instanceof BaseDataActivity) {
                ((BaseDataActivity) current).netChanged(0 >= status);
            }
        });
        try {
            app.getApplicationContext().registerReceiver(netReceiver, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 需要实现退出登录监听重写方法
     */
    protected void initLoginOutListener() {
        NetConfig config = app.initLoginStateConfig();
        if (null != config) {
            config.setCallBack(new NetConfig.OnLoginStateCallBack() {
                @Override
                public void onLoginOut(Object obj) {
                    loginOut((String) obj);
                }
            });
        }
    }

    /**
     * 退出登录返回接口
     */
    private void loginOut(String content) {
        Activity current = ActivityManager.getInstance().getCurrent();
        if (null != current && null != actLoginOutCls) {
            toLoginOutForLogin(current, actLoginOutCls);
        }
        app.loginOut(current, content);
    }

    /**
     * 退出登录
     */
    public void toLoginOutForLogin(Activity current, Class actLoginOutCls) {
        CacheManager.clearAllCache(current);
        ActivityManager.getInstance().closeAll();
        IntentManager.startActivity(current, actLoginOutCls);
    }

    /**
     * 撤销注册广播
     */
    private synchronized void unregisterNetReceiver() {
        Log.i("Receiver", "unregisterNetReceiver");
        if (null != netReceiver) {
            try {
                app.getApplicationContext().unregisterReceiver(netReceiver);
                netReceiver = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
