package com.xm.lib.util

import java.text.SimpleDateFormat

object TimeHelper {
    /**
     * 设置时间戳格式
     *
     * @param format            输出时间格式
     * @param currentTimeMillis 时间戳
     */
    fun setTime2Format(format: String?, currentTimeMillis: Long): String {
        val dateFormat = SimpleDateFormat(format)
        return dateFormat.format(currentTimeMillis)
    }

}