package com.xm.lib.manager;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author Administrator
 */
public class LogPrint {

    private static final String TAG = LogPrint.class.getSimpleName();
    private static final int MAX_LENGTH = 2500;

    /**
     * 保存文件的数据 start
     */
    private static boolean isWriteFile = false;
    private static String savePath;
    private static final String LOGGER_DIR_NAME = "/logCache";
    private static final String LOGGER_FILE_END = "-log.cache";
    /**
     * 日志的时间输出格式
     */
    private static SimpleDateFormat mLogPrintTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
    /**
     * 文件名称输出格式
     */
    private static SimpleDateFormat mLogFileTimeFormat = new SimpleDateFormat("yyyyMMdd");
    private static long mCacheClearTime = 0;
    /**
     * 保存文件的数据 end
     */

    /**
     * 打印v类型的log
     * @param obj
     * @param tags
     */
    public static void v(Object obj, String... tags) {
        String tag = getTags(tags);
        log(tag, LogType.V, obj.toString());
    }

    /**
     * 打印d类型的log
     * @param obj
     * @param tags
     */
    public static void d(Object obj, String... tags) {
        String tag = getTags(tags);
        log(tag, LogType.D, obj.toString());
    }

    /**
     * 打印w类型的log
     * @param obj
     * @param tags
     */
    public static void w(Object obj, String... tags) {
        String tag = getTags(tags);
        log(tag, LogType.W, obj.toString());
    }

    /**
     * 打印i类型的log
     * @param obj
     * @param tags
     */
    public static void i(Object obj, String... tags) {
        String tag = getTags(tags);
        log(tag, LogType.I, obj.toString());
    }

    /**
     * 打印e类型的log
     * @param obj
     * @param tags
     */
    public static void e(Object obj, String... tags) {
        String tag = getTags(tags);
        log(tag, LogType.E, obj.toString());
    }

    /**
     * 初始化保存文件路径
     *
     * @param mCxt
     */
    public static void init(Context mCxt, long... cacheSaveTime) {
        mCacheClearTime = null == cacheSaveTime && 0 >= cacheSaveTime.length ? 2 * 24 * 60 * 60 * 1000 : cacheSaveTime[0];
        StringBuffer buffer = new StringBuffer();
        buffer.append(mCxt.getExternalCacheDir().getAbsolutePath());
        buffer.append(LOGGER_DIR_NAME);
        File file = new File(buffer.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        buffer.append("/");
        savePath = buffer.toString();
        isWriteFile = true;
        clearCache(file);
    }

    /**
     * 清除日志
     *
     * @param file
     */
    private static void clearCache(File file) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File[] listFiles = file.listFiles();
                if (null != listFiles && listFiles.length > 0) {
                    for (int i = 0; i < listFiles.length; i++) {
                        File file = listFiles[i];
                        if (file.isFile() && file.getName().endsWith(LOGGER_FILE_END)) {
                            if (System.currentTimeMillis() - file.lastModified() >= mCacheClearTime) {
                                file.delete();
                            }
                        }
                    }
                }
            }
        }).start();

    }

    /**
     * 打开日志文件并写入日志
     *
     * @param mLogType
     * @param tag
     * @param text
     */
    private static synchronized void writeLogToFile(String mLogType, String tag, String text) {
        // 新建或打开日志文件
        Date currentTime = new Date(System.currentTimeMillis());
        String fileName = mLogFileTimeFormat.format(currentTime) + LOGGER_FILE_END;
        StringBuffer buffer = new StringBuffer();
        buffer.append(mLogPrintTimeFormat.format(currentTime));
        buffer.append("  ");
        buffer.append(mLogType);
        buffer.append("-->");
        buffer.append(tag);
        buffer.append("===>");
        buffer.append(text);

        File file = new File(savePath, fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            // 后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
            FileWriter filerWriter = new FileWriter(file, true);
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(buffer.toString());
            bufWriter.write("\n");
            bufWriter.close();
            filerWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成tag
     *
     * @param tags
     * @return
     */
    private static String getTags(String... tags) {
        return null != tags && tags.length > 0 ? tags[0] : TAG;
    }

    /**
     * 根据打印的数据长度进行处理打印
     *
     * @param tag
     * @param type
     * @param content
     */
    private static void log(String tag, LogType type, String content) {
        if (content.length() > MAX_LENGTH) {
            int count = content.length() / MAX_LENGTH;
            if (content.length() % MAX_LENGTH != 0) {
                count += 1;
            }
            for (int i = 0; i < count; i++) {
                int start = i * MAX_LENGTH;
                int end = (i + 1) * MAX_LENGTH;
                if (end > content.length()) {
                    print(tag, type, ">>" + content.substring(start));
                } else {
                    print(tag, type, ">>" + content.substring(start, end));
                }
            }
        } else {
            //直接打印
            print(tag, type, content);
        }
    }

    /**
     * 打印数据，并保存数据
     *
     * @param tag
     * @param type
     * @param content
     */
    private static void print(String tag, LogType type, String content) {
        String tagType;
        switch (type) {
            case V:
                tagType = "v";
                Log.v(tag, content);
                break;
            case D:
                tagType = "d";
                Log.d(tag, content);
                break;
            case W:
                tagType = "w";
                Log.w(tag, content);
                break;
            case I:
                tagType = "i";
                Log.i(tag, content);
                break;
            case E:
            default:
                tagType = "e";
                Log.e(tag, content);
                break;
        }
        if (isWriteFile) {
            writeLogToFile(tagType, tag, content);
        }
    }

    /**
     * 打印类型
     */
    private enum LogType {
        E, V, W, I, D
    }
}
