package com.xm.lib.manager;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;

import com.xm.lib.lifecycle.ActivityManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created by Juzhengyuan
 *
 * @Author: Jerry.
 * @Date: 2020/12/23 17
 * @Desc:
 */
public class DeviceManager {

    private static DeviceManager manager;

    public synchronized static DeviceManager getManager() {
        if (null == manager) {
            synchronized (DeviceManager.class) {
                if (null == manager) {
                    manager = new DeviceManager();
                }
            }
        }
        return manager;
    }

    public String getDeviceId() {
        Activity current = ActivityManager.getInstance().getCurrent();
        StringBuffer buffer = new StringBuffer();
        buffer.append(android.os.Build.MODEL);
        buffer.append("_");
        buffer.append(android.os.Build.DEVICE);
        buffer.append("_");
        buffer.append(getMacAddress(current).replaceAll(":", ""));
        return buffer.toString();
    }


    /**
     * 获取MAC地址
     *
     * @param context
     * @return
     */
    public String getMacAddress(Context context) {
        String mac = "02:00:00:00:00:00";
        if (context == null) {
            return mac;
        }
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                WifiManager wifi = (WifiManager) context.getApplicationContext()
                        .getSystemService(Context.WIFI_SERVICE);
                if (null != wifi) {
                    WifiInfo info = wifi.getConnectionInfo();
                    mac = info.getMacAddress();
                    if (!TextUtils.isEmpty(mac)) {
                        return mac.toUpperCase(Locale.ENGLISH);
                    }
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                mac = new BufferedReader(new FileReader(new File("/sys/class/net/wlan0/address")))
                        .readLine();
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
                for (NetworkInterface nif : all) {
                    if (!nif.getName().equalsIgnoreCase("wlan0")) {
                        continue;
                    }
                    byte[] macBytes = nif.getHardwareAddress();
                    if (macBytes == null) {
                        return mac;
                    }
                    StringBuilder res1 = new StringBuilder();
                    for (byte b : macBytes) {
                        res1.append(String.format("%02X:", b));
                    }
                    if (res1.length() > 0) {
                        res1.deleteCharAt(res1.length() - 1);
                    }
                    return res1.toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mac;
    }
}
