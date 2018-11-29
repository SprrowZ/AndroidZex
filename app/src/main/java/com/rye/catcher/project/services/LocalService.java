package com.rye.catcher.project.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.rye.catcher.R;

/**
 * Created by Zzg on 2018/2/25.
 */

public class LocalService extends Service {
    public static final String  TAG="LocalService";
    //用来将Service与Activity绑定
    private MyBinder mBinder=new MyBinder();

    //
    private int i;








    /**
     * 创建
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate:--------创建-------- ");
        System.out.print("----------StartService---------");
     //   createNotification();
        new Thread(()->{
            for (i = 0; i <100 ; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void createNotification(){
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
    /**
     * 开启Service
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: 开启");
        //这个是为了防止ANR（程序无响应）
        new Thread(()->{
            //开始后台任务
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }
       
    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: --------stopService-------");
        System.out.print("----------StopService---------");
        super.onDestroy();
    }

    /**
     * 绑定的Service主要用于跟踪任务进度
     * @param intent
     * @return 如果返回为空，那么ServiceConnection里的回调不会被调用
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //此方法是用来将Service和Activity进行绑定
        Log.i(TAG, "onBind: 绑定");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind: ...");
        return super.onUnbind(intent);
    }

    /**
     * onBind绑定方法需要返回一个IBinder，而Binder类又实现了IBinder接口
     */
    class  MyBinder extends Binder{
        public void startDownload(){
            //执行下载逻辑
            Log.i(TAG, "startDownload: ------Service与Activity绑定成功，开始执行方法-------");
            //跟onStartCommand里的Thread一样的作用
            new Thread(()->{
                
            }).start();
        }

        //        //模拟监控进度
        public int getProcess(){
            return i;
        }
    }
}
