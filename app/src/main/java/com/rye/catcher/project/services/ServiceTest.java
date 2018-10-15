package com.rye.catcher.project.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.rye.catcher.R;

/**
 * Created by Zzg on 2018/2/25.
 */

public class ServiceTest extends Service {
    public static final String  TAG="ServiceTest";
    //用来将Service与Activity绑定
    private MyBinder mBinder=new MyBinder();
    //不要忘了注册Service
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate:--------ServiceStart-------- ");
        System.out.print("----------StartService---------");
        //创建前台Service
        NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent notificationIntent=new Intent(this,ServiceMainActivity.class);//点击意图
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,notificationIntent,0);
        Notification notification=new Notification.Builder(this)
                .setContentIntent(pendingIntent)//设置点击意图
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("前台Service")
                .setContentTitle("Title").build();
        notificationManager.notify(1,notification);
        startForeground(1,notification);//让ServiceTest变成一个前台Service
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //这个是为了防止ANR（程序无响应）
        new Thread(new Runnable() {
            @Override
            public void run() {
                //开始执行后台任务
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: --------stopService-------");
        System.out.print("----------StopService---------");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //此方法是用来将Service和Activity进行绑定
        return mBinder;
    }
    //用来绑定Activity
    class  MyBinder extends Binder{
        public void startDownload(){
            //执行下载逻辑
            Log.i(TAG, "startDownload: ------Service与Activity绑定成功，开始执行方法-------");
            //跟onStartCommand里的Thread一样的作用
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //执行具体的下载任务
                }
            }).start();
        }
    }
}
