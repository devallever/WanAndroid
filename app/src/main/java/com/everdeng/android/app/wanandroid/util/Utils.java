package com.everdeng.android.app.wanandroid.util;

import android.graphics.Point;

import com.xm.lib.util.LogUtilsKt;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static void main(String[] args) {

    }

    public static void test() {
        List<String> pixelList = new ArrayList<>();
        pixelList.add("1*1");
        pixelList.add("1*8");
        pixelList.add("1*8");
        pixelList.add("1*1");
        List<Point> pointList = new ArrayList<>();

        int totalX = 0;
        int totalY = 0;

        for (String pixel : pixelList) {
            String[] pixelArr = pixel.split("\\*");
            Point point = new Point();
            int xLength = Integer.parseInt(pixelArr[1]);
            int yLength = Integer.parseInt(pixelArr[0]);
            point.x = xLength;
            point.y = yLength;
            pointList.add(point);

            totalX += xLength;
            totalY += yLength;
        }

        String allX = appendZero(Integer.toBinaryString(totalX - 1), 12);
        String allY = appendZero("0", 12);
        LogUtilsKt.log("全阵列X：" + (totalX - 1) + " -> " + allX);
        LogUtilsKt.log("全阵列Y：" + 0 + " -> " + allY);


        int startX = 0;
        for (int i = 0; i < pointList.size(); i++) {
            Point point = pointList.get(i);
            LogUtilsKt.log(point.toString());
            String singleX = appendZero(Integer.toBinaryString(startX), 12);
            String singleY = appendZero("0", 12);
            LogUtilsKt.log("第 " + i + " 个灯具开始位置 (" + 0 + ", " + startX + ")" + " -> " + "(" + singleY + ", " + singleX + ")");
            String positionBinary = allY + " " + allX + " " + singleY + " " + singleX;

            int position = Integer.parseInt(positionBinary.replace(" ", ""), 2);
            String positionHex = Integer.toHexString(position);
            LogUtilsKt.log("第 " + i + " 个灯具position位置 : " + positionBinary + " -> " + appendZero(positionHex, 12));

            LogUtilsKt.log("");
            startX += point.x;
        }


        LogUtilsKt.log("10 -> " + appendZero(Integer.toBinaryString(10), 12));
        LogUtilsKt.log("000000001010 -> " + Integer.parseInt("000000001010", 2));

    }

    public static String appendZero(String value, int length) {
        int valueLength = value.length();
        if (valueLength >= length) {
            return value;
        }

        //需要补几个0
        int leftLength = length - valueLength;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < leftLength; i++) {
            builder.append("0");
        }

        return builder.append(value).toString();

    }
}
