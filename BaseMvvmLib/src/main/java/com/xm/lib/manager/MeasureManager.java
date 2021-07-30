package com.xm.lib.manager;

import android.content.Context;

/**
 * 测量工具类
 * */
public class MeasureManager {

    /**
     * dip转px
     * @param dpValue dip长度
     * */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * sp转px
     * @param spValue sp长度
     * */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
    /**
     * px转sp
     * @param spValue sp长度
     * */
    public static int px2sp(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue / fontScale + 0.5f);
    }

    /**
     * px转sp
     * @param spValue sp长度
     * */
    public static int px2dp(Context context, float dpValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (dpValue / fontScale + 0.5f);
    }

    /**
     * 获取设备屏幕长度
     * */
    public static int getScreenWidth(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取设备屏幕高度
     * */
    public static int getScreenHeight(Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
}
