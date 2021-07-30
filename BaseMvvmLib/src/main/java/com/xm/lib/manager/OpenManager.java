package com.xm.lib.manager;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by BaseMvpLibs.
 * Author: Jerry.
 * Date: 2020/9/22:17:48.
 * Desc:
 */
public class OpenManager {

    private static OpenManager manager;
    private final String RESULT_CODE_NAME = "result_code";

    public synchronized static OpenManager getOpenManager() {
        if (null == manager) {
            synchronized (OpenManager.class) {
                if (null == manager) {
                    manager = new OpenManager();
                }
            }
        }
        return manager;
    }

    public void open(Activity activity, Class<? extends Activity> clz, boolean isCloseActivity) {
        com.xm.lib.manager.IntentManager.startActivity(activity, clz);
        close(activity,isCloseActivity);
    }

    public void open(Activity activity, Intent intent, boolean isCloseActivity) {
        com.xm.lib.manager.IntentManager.startActivity(activity, intent);
        close(activity,isCloseActivity);
    }

    public void open(Activity activity, Intent intent, int resultCode) {
        intent.putExtra(RESULT_CODE_NAME, resultCode);
        IntentManager.startActivityFroResultWithAnim(activity, intent, resultCode);
    }

    private void close(Activity activity, boolean isCloseActivity) {
        if (isCloseActivity) {
            activity.finish();
        }
    }

    public int getResultCode(Activity activity) {
        return activity.getIntent().getIntExtra(RESULT_CODE_NAME, 0);
    }
}
