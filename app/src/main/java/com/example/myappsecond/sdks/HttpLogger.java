package com.example.myappsecond.sdks;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created at 2018/9/12.
 *
 * @author Zzg
 */
public class HttpLogger implements HttpLoggingInterceptor.Logger {
    private static final String TAG = "HttpLogger";
    @Override
    public void log(String message) {
        Log.i(TAG, message);
    }

    /**
     * okHttp使用
     * @return
     */
    public static OkHttpClient getOkHttpClient() {
        //日志显示级别
        HttpLoggingInterceptor.Level level= HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("Retrofit请求：","OkHttp====Message:"+message);
            }
        });
        loggingInterceptor.setLevel(level);
        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient
                .Builder();
        //OkHttp进行添加拦截器loggingInterceptor
        httpClientBuilder.addInterceptor(loggingInterceptor);
        return httpClientBuilder.build();
    }


}
