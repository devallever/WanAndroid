package com.xm.netmodel.helder;

import androidx.annotation.Nullable;

/**
 * @ClassName RsThrowable
 * @Description TODO
 * @Author Jerry
 * @Date 2020/5/4 0:17
 * @Version 1.0
 */
public class ResultThrowable extends Exception {

    public int code;
    public String message;

    public ResultThrowable(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    public ResultThrowable(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    @Nullable
    @Override
    public String getMessage() {
        return message;
    }
}
