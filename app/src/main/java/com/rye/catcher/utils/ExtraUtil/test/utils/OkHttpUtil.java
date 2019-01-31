package com.rye.catcher.utils.ExtraUtil.test.utils;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created at 2019/1/7.
 *
 * @author Zzg
 * @function: 自己测试用OKhttp工具类
 */
public class OkHttpUtil {
    private static final String TAG="OkHttpUtil";
    //超时时间
    private static  final int CONNECTION_TIME_OUT=60;
    private static  final int READ_TIME_OUT=100;
    private static  final int WRITE_TIME_OUT=100;

    /**
     *静态内部类单例模式
     */
    private static class SingleOKhttp{
        private static OkHttpUtil INSTANCE=new OkHttpUtil();
    }
    public static  OkHttpUtil getInstance(){
        return  SingleOKhttp.INSTANCE;
    }

    public OkHttpClient getClient() {
        return client;
    }

    /**
     * 拦截器，用来看日志
     */
    private HttpLoggingInterceptor interceptor= new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            try {
                //替换单独出现的百分号
                message = message.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
                String logs=URLDecoder.decode(message,"utf-8");
                Log.i("OkHttp",  logs);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Log.i("OkHttp","error:"+e.toString());
            }
        }
    }).setLevel(HttpLoggingInterceptor.Level.BODY);

    private OkHttpClient client=new OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT,TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME_OUT,TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build();

}
