package com.charles.common.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * com.charles.common.network.ProgressInterceptor
 *
 * @author Just.T
 * @since 16/12/28
 */
public class ProgressInterceptor implements Interceptor {

    private ProgressListener listener;

    public ProgressInterceptor(ProgressListener listener) {
        this.listener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        okhttp3.Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder().body(
                new ProgressResponseBody(originalResponse.body(), listener))
                .build();

    }

    public interface ProgressListener {
        /**
         * @param progress 已经下载或上传字节数
         * @param total    总字节数
         * @param done     是否完成
         */
        void onProgress(long progress, long total, boolean done);
    }
}
