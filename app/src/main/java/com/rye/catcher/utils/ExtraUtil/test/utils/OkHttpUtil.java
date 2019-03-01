package com.rye.catcher.utils.ExtraUtil.test.utils;

import android.util.Log;

import com.rye.catcher.project.services.FileResponseBody;
import com.rye.catcher.project.services.RetrofitCallBack;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;

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
        private static final OkHttpUtil INSTANCE=new OkHttpUtil();
    }
    public static  OkHttpUtil getInstance(){
        return  SingleOKhttp.INSTANCE;
    }

    public OkHttpClient getClient() {
        return client;
    }

    /**
     *下载专用
     * @return
     */
    private RetrofitCallBack  callBack=new RetrofitCallBack() {
        @Override
        public void onSuccess(Call call, retrofit2.Response response) {
            Log.i(TAG, "onSuccess: ");
        }

        @Override
        public void onLoading(long total, long progress) {
            Log.i(TAG, "onLoading: "+progress);
        }

        @Override
        public void onFailure(Call call, Throwable t) {
            Log.i(TAG, "onFailure: ");
        }
    };
    public <T> OkHttpClient getDownloadClient( ) {
        return downloadClient;
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
    /**
     * 下载拦截器
     */
    private Interceptor dInterceptor=new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response response=chain.proceed(chain.request());
            //将ResponBody转换成我们自定义的FileResponseBody

            return response.newBuilder().body(new FileResponseBody(response.body(),callBack)).build();
        }
    };
    /**
     * 一般情况下的客户端
     */
    private OkHttpClient client=new OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT,TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME_OUT,TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build();
    /**
     * 下载专用客户端
     */
    private OkHttpClient downloadClient=new OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT,TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME_OUT,TimeUnit.SECONDS)
            .addInterceptor(dInterceptor)
            .build();
}
