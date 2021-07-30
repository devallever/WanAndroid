package com.xm.lib.manager;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by NichePlanet.
 * Author: Jerry.
 * Date: 2020/9/15:11:45.
 * Desc:
 */
public class ActivityResult {
    private static ActivityResult activityResult;
    private IDataResultCallBack dataResultCallBack;

    public synchronized static ActivityResult getInstance() {
        if (null == activityResult) {
            synchronized (ActivityResult.class) {
                if (null == activityResult) {
                    activityResult = new ActivityResult();
                }
            }
        }
        return activityResult;
    }

    public void setDataResultCallBack(IDataResultCallBack dataResultCallBack) {
        this.dataResultCallBack = dataResultCallBack;
    }

    public void setActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (null != dataResultCallBack && resultCode == Activity.RESULT_OK) {
            dataResultCallBack.onResult(activity,requestCode, data);
        }
    }

    public interface IDataResultCallBack {
        void onResult(Activity activity, int requestCode, Intent data);
    }
}
