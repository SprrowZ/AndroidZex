package com.catcher.zzsdk.okhttp.request;

import com.catcher.zzsdk.okhttp.client.OkClient;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhy on 15/12/15.
 * 对OkHttpRequest的封装 --》修订，简化
 */
public class RequestCall {
    //超时时间统一设置为30s
    private static  final int TIME_OUT=30;
    private BaseRequest baseRequest;
    private Request request;
    private Call call;

//    private long readTimeOut;
//    private long writeTimeOut;
//    private long connTimeOut;

    private OkHttpClient okHttpClient;

    public RequestCall(BaseRequest request)
    {
        this.baseRequest = request;
    }

//    public RequestCall readTimeOut(long readTimeOut)
//    {
//        this.readTimeOut = readTimeOut;
//        return this;
//    }
//
//    public RequestCall writeTimeOut(long writeTimeOut)
//    {
//        this.writeTimeOut = writeTimeOut;
//        return this;
//    }
//
//    public RequestCall connTimeOut(long connTimeOut)
//    {
//        this.connTimeOut = connTimeOut;
//        return this;
//    }

//    public Call buildCall(Callback callback)
//    {
//        request = generateRequest(callback);
//
//        if (readTimeOut > 0 || writeTimeOut > 0 || connTimeOut > 0)
//        {
//            readTimeOut = readTimeOut > 0 ? readTimeOut : OkHttpUtils.DEFAULT_MILLISECONDS;
//            writeTimeOut = writeTimeOut > 0 ? writeTimeOut : OkHttpUtils.DEFAULT_MILLISECONDS;
//            connTimeOut = connTimeOut > 0 ? connTimeOut : OkHttpUtils.DEFAULT_MILLISECONDS;
//
//            clone = OkHttpUtils.getInstance().getOkHttpClient().newBuilder()
//                    .readTimeout(readTimeOut, TimeUnit.MILLISECONDS)
//                    .writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS)
//                    .connectTimeout(connTimeOut, TimeUnit.MILLISECONDS)
//                    .build();
//
//            call = clone.newCall(request);
//        } else
//        {
//            call = OkHttpUtils.getInstance().getOkHttpClient().newCall(request);
//        }
//        return call;
//    }

    /**
     * Call构建对象
     * @param callback
     * @return
     */
    private void buildCall(Callback callback){
        request = generateRequest(callback);
        //构建okhttpBuilder对象，设置超时时间
        okHttpClient=OkClient.Companion.getClient().newBuilder()
                .readTimeout(TIME_OUT,TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT,TimeUnit.SECONDS)
                .connectTimeout(TIME_OUT,TimeUnit.SECONDS)
                .build();
       call= okHttpClient.newCall(request);
    }
    private Request generateRequest(Callback callback){
        return baseRequest.generateRequest(callback);
    }

    /**
     * 链式调用执行的
     * @param callback
     */
    public void execute(Callback callback)
    {
        buildCall(callback);
        call.enqueue(callback);
    }

    public Call getCall()
    {
        return call;
    }

    public Request getRequest()
    {
        return request;
    }

    public BaseRequest getBaseRequest()
    {
        return baseRequest;
    }

    public Response execute() throws IOException
    {
        buildCall(null);
        return call.execute();
    }

    public void cancel()
    {
        if (call != null)
        {
            call.cancel();
        }
    }


}
