package com.xm.netmodel.util;

public class L {

    private static final int MAX_LENGTH = 2500;

    public static void e(String tag, String content) {
        if (content.length() > MAX_LENGTH) {
            int count = content.length() / MAX_LENGTH;
            if (content.length() % MAX_LENGTH != 0) {
                count += 1;
            }
            for (int i = 0; i < count; i++) {
                int start = i * MAX_LENGTH;
                int end = (i + 1) * MAX_LENGTH;
                if (end > content.length()) {
                    android.util.Log.e(tag, "====>" + content.substring(start));
                } else {
                    android.util.Log.e(tag, "====>" + content.substring(start, end));
                }
            }
        } else {
            //直接打印
            android.util.Log.e(tag, content);
        }
    }

    public static void i(String tag, String content) {
        if (content.length() > MAX_LENGTH) {
            int count = content.length() / MAX_LENGTH;
            if (content.length() % MAX_LENGTH != 0) {
                count += 1;
            }
            for (int i = 0; i < count; i++) {
                int start = i * MAX_LENGTH;
                int end = (i + 1) * MAX_LENGTH;
                if (end > content.length()) {
                    android.util.Log.i(tag, "====>" + content.substring(start));
                } else {
                    android.util.Log.i(tag, "====>" + content.substring(start, end));
                }
            }
        } else {
            //直接打印
            android.util.Log.i(tag, content);
        }
    }

    public static void e(String content) {
        e("Logger", content);
    }
}
