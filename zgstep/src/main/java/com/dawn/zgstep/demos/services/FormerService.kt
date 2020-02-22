package com.dawn.zgstep.demos.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import java.lang.Exception
import java.lang.ref.WeakReference

/**
 * 整个的流程就是先通过FormerService开启前台服务，调用startForeground，
 * 之后顶部栏应该是有通知的，然后过一段时间后结束这个Service，在结束前/结束后，开启
 * LatestService，这时候不调用startForeground方法，LatestService中开启并绑定keepLiveService
 * 两者通过Messenger来进行通信，相互调用。latestService中handler，postDelayed30秒.然后KeepLiveService
 * 中睡眠三十秒，这样一来心跳就是一分钟跳一次。
 * 理想的情况：顶部没有通知，且心跳可以维持很长一段时间(即使是息屏)
 *
 * 调用方式：先调用FormerService，再调用LatestService。至于是FormerService关闭后再调用，还是调用之后直接调用
 * LatestService。这里分两种情况测试
 */
class FormerService : Service() {

    companion object {
        const val  TAG="FormerService"
        const val CHANNEL_ID="333"
        const val CHANNEL_NAME="android"
        fun startService(context: Context) {
            val intent = Intent(context, FormerService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        val notification=if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
           val manager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
           val notificationChannel=NotificationChannel(CHANNEL_ID, CHANNEL_NAME,NotificationManager.IMPORTANCE_HIGH)
           manager.createNotificationChannel(notificationChannel)
           Notification.Builder(this, CHANNEL_ID).build()
        }else{
            Notification()
        }
        startForeground(666,notification)
        doSomething()
    }

    private fun doSomething(){
         Thread(TimeConsumingTask(3000, WeakReference(this))).start()
    }

    /**
     * 模拟耗时任务
     */
   class TimeConsumingTask(private val time:Long,val weakReference: WeakReference<FormerService>) :Runnable{
       override fun run() {
           try {
               Thread.sleep(time)
           }catch (e:Exception){
               e.printStackTrace()
           }
          val formerService=weakReference.get()
          formerService.let {
              Log.d(TAG,"formerService is null ? ${formerService==null}")
              formerService?.stopSelf()
          }
       }

   }

}