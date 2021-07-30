package com.xm.netmodel.helder;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.GzipSource;

/**
 * <pre>
 *     author : Jerry
 *     time   : 2020/04/11
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class ResponseBodyInterceptor<T> implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String url = request.url().toString();
        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            long contentLength = responseBody.contentLength();
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();

            if ("gzip".equals(response.headers().get("Content-Encoding"))) {
                GzipSource gzippedResponseBody = new GzipSource(buffer.clone());
                buffer = new Buffer();
                buffer.writeAll(gzippedResponseBody);
            }

            MediaType contentType = responseBody.contentType();
            Charset charset;
            if (contentType == null || contentType.charset(StandardCharsets.UTF_8) == null) {
                charset = StandardCharsets.UTF_8;
            } else {
                charset = contentType.charset(StandardCharsets.UTF_8);
            }

            if (charset != null && contentLength != 0L) {
                return intercept(response,url, buffer.clone().readString(charset));
            }
        }
        return response;
    }

    protected abstract Response intercept(Response response,String url, String body);
}
