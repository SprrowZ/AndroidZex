package com.example.myappsecond.project.review;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import com.example.myappsecond.MainActivity;
import com.example.myappsecond.R;

/**
 * Created by ZZG on 2017/9/27.
 */

public class KeepJava extends Activity {
    private Button btn1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_test);
        btn1=findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long[] vivrates={0,1000,1000,1000};
                NotificationManager manager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Notification noti = new NotificationCompat.Builder(KeepJava.this)//实例化Builder
                               .setTicker("新同志，新同志！！！！！")//在状态栏显示的标题
                                .setSubText("ddsafsa")
                                 .setWhen(System.currentTimeMillis())//设置显示的时间，默认就是currentTimeMillis()
                                 .setContentTitle("New mail from ")//设置标题
                                  .setContentText("111")//设置内容
                               //.setProgress(100,50,false)

                                 .setSmallIcon(R.mipmap.ic_launcher)//设置状态栏显示时的图标
                                  .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.animation_a))//设置显示的大图标
                                  .setContentIntent(PendingIntent.getActivity(KeepJava.this, 0, new Intent(KeepJava.this,MainActivity.class), 0))//设置点击时的意图
                                  //.setDeleteIntent(PendingIntent.getActivity(Notification_test.this, 0, new Intent(Settings.ACTION_SETTINGS), 0))//设置删除时的意图
                                   // .setFullScreenIntent(PendingIntent.getActivity(Notification_test.this, 0, new Intent(Settings.ACTION_SETTINGS), 0), true)//这个将直接打开意图，而不经过状态栏显示再按下
                                   .setAutoCancel(true)//设置是否自动按下过后取消
                                   .setOngoing(false)//设置为true时就不能删除  除非使用notificationManager.cancel(1)方法
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .build();
                   manager.notify(1,noti);
            }
        });
    }
}
