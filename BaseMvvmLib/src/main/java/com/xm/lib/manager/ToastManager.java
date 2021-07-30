package com.xm.lib.manager;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.xm.lib.lifecycle.ActivityManager;


/**
 * Toast统一管理类
 * @author Jerry
 */
public class ToastManager {

    private static Toast toast;

    private static Toast initToast(CharSequence message, int duration) {
        Activity top = ActivityManager.getInstance().getCurrent();
        if (null == top) {
            return null;
        }
        if (toast == null) {
            toast = Toast.makeText(top, message, duration);
        } else {
            toast.setText(message);
            toast.setDuration(duration);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        return toast;
    }

    private static Toast initToast( CharSequence message, int gravity, int duration) {
        Activity top = ActivityManager.getInstance().getCurrent();
        if (null == top) {
            return null;
        }
        if (toast == null) {
            toast = Toast.makeText(top, message, duration);
        } else {
            toast.setText(message);
            toast.setDuration(duration);
        }
        toast.setGravity(gravity, 0, 0);
        return toast;
    }


    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(CharSequence message) {
        initToast( message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     *
     * @param strResId
     */
    public static void showShort(int strResId) {
        Activity top = ActivityManager.getInstance().getCurrent();
        if (null == top) {
            return;
        }
        initToast(top.getResources().getText(strResId), Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(CharSequence message) {
        initToast(message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param strResId
     */
    public static void showLong(int strResId) {
        Activity top = ActivityManager.getInstance().getCurrent();
        if (null == top) {
            return;
        }
        initToast( top.getResources().getText(strResId), Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void show(CharSequence message, int duration) {

        initToast( message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param strResId
     * @param duration
     */
    public static void show(int strResId, int duration) {
        Activity top = ActivityManager.getInstance().getCurrent();
        if (null == top) {
            return;
        }
        initToast( top.getResources().getText(strResId), duration).show();
    }

    /**
     * 短时间显示Toast
     *
     * @param strResId
     */
    public static void showShort( int gravity, int strResId) {
        Activity top = ActivityManager.getInstance().getCurrent();
        if (null == top) {
            return;
        }
        initToast( top.getResources().getText(strResId), gravity, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(int gravity, CharSequence message) {

        initToast( message, gravity,Toast.LENGTH_LONG).show();
    }
    /**
     * 短时间显示Toast
     *
     * @param strResId
     */
    public static void showShort(int gravity, CharSequence message) {

        initToast( message, gravity, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong( int gravity, int strResId) {
        Activity top = ActivityManager.getInstance().getCurrent();
        if (null == top) {
            return;
        }
        initToast(top.getResources().getText(strResId), gravity,Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showDefaultLong(int strResId) {
        Activity top = ActivityManager.getInstance().getCurrent();
        if (null == top) {
            return;
        }
        Toast.makeText(top,top.getResources().getText(strResId),Toast.LENGTH_LONG).show();
    }
    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showDefaultLong(CharSequence message) {
        Activity top = ActivityManager.getInstance().getCurrent();
        if (null == top) {
            return;
        }
        Toast.makeText(top,message,Toast.LENGTH_LONG).show();
    }
    /**
     * 长时间显示Toast
     *
     */
    public static void showDefaultShort(int strResId) {
        Activity top = ActivityManager.getInstance().getCurrent();
        if (null == top) {
            return;
        }
        Toast.makeText(top,top.getResources().getText(strResId),Toast.LENGTH_SHORT).show();
    }
    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showDefaultShort(CharSequence message) {
        Activity top = ActivityManager.getInstance().getCurrent();
        if (null == top) {
            return;
        }
        Toast.makeText(top,message,Toast.LENGTH_SHORT).show();
    }

    public static Toast customToast(View view,int gravity, CharSequence message, int duration) {
        Activity top = ActivityManager.getInstance().getCurrent();
        if (null == top) {
            return null;
        }
        if (toast == null) {
            toast = Toast.makeText(top, message, duration);
        } else {
            toast.setText(message);
            toast.setDuration(duration);
            toast.setView(view);
        }
        toast.setGravity(gravity, 0, 0);
        return toast;
    }

    public static Toast customToast(View view,int gravity, int strResId, int duration) {
        Activity top = ActivityManager.getInstance().getCurrent();
        if (null == top) {
            return null;
        }
        if (toast == null) {
            toast = Toast.makeText(top, top.getResources().getText(strResId), duration);
        } else {
            toast.setText(top.getResources().getText(strResId));
            toast.setDuration(duration);
        }
        if (null != view) {
            toast.setView(view);
        }
        toast.setGravity(gravity, 0, 0);
        return toast;
    }
}
