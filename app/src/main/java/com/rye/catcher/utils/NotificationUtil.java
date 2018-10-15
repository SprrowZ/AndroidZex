package com.rye.catcher.utils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

/**
 * Created by changshuchao on 2017/9/27.
 */

public class NotificationUtil {

            private static String Ticker="";//貌似看不到啊
            private static String subText="";
            private static String Title="";// 最主要的还是这个title和content
            private static String Content="";//
            private static int SmallIcon;//小米上看不到
            private static int LargeIcon;


    public void setMust(String title,String content,int smallIcon){
        this.Title=title;
        this.Content=content;
        this.SmallIcon=smallIcon;
    }
    public void setInfo(String title,String content,String ticker,
                        String subText,int smallIcon,int largeIcon){
        this.Title=title;
        this.Content=content;
        this.Ticker=ticker;
        this.subText=subText;
        this.SmallIcon=smallIcon;
        this.LargeIcon=largeIcon;
    }
              //这个构造方法是可以跳转的
        public NotificationUtil(Activity activity,Activity activity2){
                long[] vivrates={0,1000,1000,1000};
                NotificationManager manager= (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
                Notification noti = new NotificationCompat.Builder(activity)//实例化Builder
                                  .setTicker(Ticker)//在状态栏显示的标题
                                  .setSubText(subText)//标题栏
                                  .setWhen(System.currentTimeMillis())//设置显示的时间，默认就是currentTimeMillis()
                                  .setContentTitle(Title)//设置标题
                                  .setContentText(Content)//设置内容
                                //.setProgress(100,50,false)
                                  .setSmallIcon(SmallIcon)//设置状态栏显示时的图标
                                  .setLargeIcon(BitmapFactory.decodeResource(activity.getResources(), LargeIcon))//设置显示的大图标
                                  .setContentIntent(PendingIntent.getActivity(activity, 0, new Intent(activity,activity2.getClass()), 0))//设置点击时的意图
                                //.setDeleteIntent(PendingIntent.getActivity(Notification_test.this, 0, new Intent(Settings.ACTION_SETTINGS), 0))//设置删除时的意图
                                //.setFullScreenIntent(PendingIntent.getActivity(Notification_test.this, 0, new Intent(Settings.ACTION_SETTINGS), 0), true)//这个将直接打开意图，而不经过状态栏显示再按下
                                  .setAutoCancel(true)//设置是否自动按下过后取消
                                  .setOngoing(false)// true，设置他为一个正在进行的通知。如一个文件下载；网络连接设置为true时就不能删除  除非使用notificationManager.cancel(1)方法
                                  .setDefaults(Notification.DEFAULT_SOUND)//默认声音
                                  .build();
                                   manager.notify(2,noti);
            /*
            //把通知放在正在运行栏目中
 notification.flags|=Notification.FLAG_ONGOING_EVENT;
 //设定默认声音
 notification.defaults|=Notification.DEFAULT_SOUND;
 //设定默认震动
 notification.defaults|=Notification.DEFAULT_VIBRATE;
 //设定默认LED灯提醒
 notification.defaults|=Notification.DEFAULT_LIGHTS;
 //设置点击后通知自动清除
 notification.defaults|=Notification.FLAG_AUTO_CANCEL;
 //自定义通知的声音
 notification.sound=Uri.parse(Environment.getExternalStorageDirectory()+"/non.mp3");
 //自定义震动参数分别为多长时间开始震动、第一次震动的时间、停止震动的时间
 long[] vibrate={0,100,200,300};
 notification.vibrate=vibrate;
 //自定义闪光灯的方式
 notification.ledARGB=0xff00ff00;
 notification.ledOnMS=500;
 notification.ledOffMS=500;
 notification.flags|=Notification.FLAG_SHOW_LIGHTS;



 */
            }

}
