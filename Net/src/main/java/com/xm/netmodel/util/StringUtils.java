package com.xm.netmodel.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.xm.netmodel.config.Config;

/**
 * 公共工具类
 *
 * @Author Jerry
 * @create at 2020.03.04 12:17
 */
public class StringUtils {

    /**
     * 判断字符串是否是json字符串格式
     *
     * @param json 检查字符串
     */
    public static boolean isJsonFormat(String json) {
        JsonElement jsonElement;
        try {
            jsonElement = new JsonParser().parse(json);
            return jsonElement == null ? false : (jsonElement.isJsonObject() || jsonElement.isJsonArray());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 返回接送字符串的msg数据
     * @param response
     * @return
     */
    public static String getMsgData(Object response){
        String msgName = Config.getConfig().getDataConfig().msgName;
        String str = (String)HttpDataUtils.getValue(response, msgName);
        return str;
    }


}
