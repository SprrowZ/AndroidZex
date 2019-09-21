package com.rye.appupdater.updater.net;



import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created By RyeCatcher
 * at 2019/9/2
 * 网络请求由Okhttp来做，重新跟着老师思路写一遍
 */
public class OkhttpNetManager implements INetManager {
    private final String TAG=this.getClass().getSimpleName();
    private static OkHttpClient sOkHttpClient;
    //主线程handler
    private static Handler sHandler=new  Handler(Looper.getMainLooper());
    static {
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        builder.connectTimeout(15, TimeUnit.SECONDS);
        sOkHttpClient=builder.build();
    }


    @Override
    public void get(String url, INetCallback callback,Object tag) {
          dealGet(url,callback,tag);
    }

    @Override
    public void download(String url, File targetFile, INetDownloadCallBack callBack,Object tag) {
          dealDownload(url,targetFile,callBack,tag);
    }

    @Override
    public void cancel(Object tag) {
         //需要获取进行中--排队中的网络请求
        List<Call> queuedCalls=sOkHttpClient.dispatcher().queuedCalls();
        for (Call call:queuedCalls){
            if (tag.equals(call.request().tag())){
                Log.d(TAG,"find call:"+tag);
                call.cancel();
            }
        }
        List<Call> runningCalls=sOkHttpClient.dispatcher().runningCalls();
        for (Call call:runningCalls){
            if (tag.equals(call.request().tag())){
                Log.d(TAG,"find call:"+tag);
                call.cancel();
            }
        }
    }

    /**
     * 处理get请求
     * @param mUrl
     * @param callback
     */
    private void dealGet(String mUrl,INetCallback callback,Object tag){
        Request.Builder builder=new Request.Builder();
        Request request= builder.url(mUrl).get().tag(tag).build();
        Call call=sOkHttpClient.newCall(request);
        call.enqueue(new Callback() {//子线程中
            @Override
            public void onResponse(Call call, Response response) throws IOException {
               final  String result=response.body().string();
                   //try catch 一下
                sHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.success(result);
                    }
                });
            }


            @Override
            public void onFailure(Call call, IOException e) {
                  sHandler.post(new Runnable() {
                      @Override
                      public void run() {
                        callback.failed(e);
                      }
                  });
            }

        });
    }

    /**
     * 下载
     * @param url
     * @param targetFile
     * @param callBack
     */
    private void dealDownload(String url, File targetFile, INetDownloadCallBack callBack,Object tag){
        if (!targetFile.exists()){
            targetFile.getParentFile().mkdirs();
        }
        Request request=new Request.Builder()
                .get()
                .tag(tag)
                .url(url)
                .build();
        Call call=sOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                 sHandler.post(new Runnable() {//运行在主线程中
                     @Override
                     public void run() {
                         callBack.failed(e);
                     }
                 });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream in=null;
                OutputStream os=null;
                try{


                in=response.body().byteStream();
                os=new FileOutputStream(targetFile);
                //文件总大小
                long totalLen=response.body().contentLength();
                //文件已下载大小
                long currentLen=0;
                int bufferLen;
                byte[] buffer=new byte[8*1024];
                while ((bufferLen = in.read(buffer)) != -1) {
                    os.write(buffer,0,bufferLen);
                    currentLen+=bufferLen;

                    long finalCurrentLen = currentLen;
                    sHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.progress((int) (finalCurrentLen *1.0f/totalLen*100));
                        }
                    });
                }
                try{//将文件暴露给其他进程--这里是安装apk进程，
                    targetFile.setExecutable(true,false);
                    targetFile.setReadable(true,false);
                    targetFile.setWritable(true,false);
                }catch (Exception e){

                }

                sHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.success(targetFile);
                    }
                });
                }catch (Exception e){
                    Log.e("error","downloadFile error:"+e.toString());
                    if (call.isCanceled()){
                        return;
                    }
                    sHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.failed(e);
                        }
                    });
                }finally {
                     if (in!=null){
                         in.close();
                     }
                     if (os!=null){
                         os.close();
                     }
                }


            }
        });
    }
}
