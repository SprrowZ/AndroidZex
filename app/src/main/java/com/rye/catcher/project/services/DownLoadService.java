package com.rye.catcher.project.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.rye.catcher.activity.fragment.orr.interfaces.DownLoadListener;
import com.rye.catcher.activity.fragment.orr.interfaces.zRetrofitApi;
import com.rye.catcher.utils.DateUtils;
import com.rye.catcher.utils.ExtraUtil.test.utils.OkHttpUtil;
import com.rye.catcher.utils.FileUtils;
import com.rye.catcher.utils.SDHelper;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created at 2019/1/30.
 *
 * @author Zzg
 * @function:
 */
public class DownLoadService extends Service {
    private static  final  String TAG="DownLoadService";
    public static  String BASE_URL="BASE_URL";
    private static DownLoadListener listener;
    private String downLoadUrl;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ...");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ....");
        Log.i(TAG, "intent: "+intent);
        if (intent!=null){
            downLoadUrl=intent.getStringExtra(BASE_URL);
            downLoadRx(downLoadUrl);
        }
        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * Rxjava+Retrofit+Okhttp---测试方法1
     */
    private void downLoadRx(String url){
        Log.i(TAG, "downLoadRx: "+ DateUtils.getCurrentTime(null));
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttpUtil.getInstance().getClient())
                .build();
        zRetrofitApi api=retrofit.create(zRetrofitApi.class);
        listener.onStart();
        Observable<ResponseBody> observable=api.downLoadRx("trailer.mp4");
        //Observer
        Observer<ResponseBody> observer=new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {

            }
            //成功走此
            @Override
            public void onNext(ResponseBody responseBody) {
                Log.i(TAG, "onNext: "+ DateUtils.getCurrentTime(null));
                //执行下载操作
                download(responseBody,"z-Video.mp4");
                //关闭服务
                stopSelf();
            }

            @Override
            public void onError(Throwable e) {
                listener.onFail(e.toString());
                Log.i(TAG, "onError: "+e.toString());
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: ");
            }
        };
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    /**
     * 下载文件
     * @param
     * @param fileName
     */
    private void download(ResponseBody responseBody,String fileName){
        InputStream in=responseBody.byteStream();
        long total=responseBody.contentLength();
        File file= FileUtils.createNewFile(SDHelper.getVideoFolder()+fileName);
        long sum=0L;
        try {
            BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(file));
            byte[] buf=new byte[2048];
            int length;
            while((length=in.read(buf))!=-1){
                bos.write(buf,0,length);
                sum+=length;
                listener.onProgress((int) (100*(double)sum/total));
                Log.i(TAG, "download: "+(double)sum/total);
            }
            listener.onFinish(file.getAbsolutePath());
            bos.flush();
            bos.close();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            listener.onFail(e.toString());
            e.printStackTrace();
        }
    }


   public static void addListener(DownLoadListener mListener){
        listener=mListener;
   }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ...");
    }
}
