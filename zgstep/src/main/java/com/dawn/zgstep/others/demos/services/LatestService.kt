package com.dawn.zgstep.others.demos.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import com.dawn.zgstep.others.demos.services.KeepliveService.Companion.HEART_BEAT
import com.dawn.zgstep.others.demos.services.KeepliveService.Companion.HEART_BEAT_INTERVAL
import com.dawn.zgstep.others.demos.services.KeepliveService.Companion.HEART_BEAT_OK
import java.lang.Exception
import java.lang.ref.WeakReference

class LatestService : Service() {
    //两个Messenger
    var clientMessenger=Messenger(ClientMessengerHandler(this))
    var serviceMessenger:Messenger?=null

    private var connection: ServiceConnection? = null
    companion object {
        const val  TAG="LatestService"
        const val CHANNEL_ID="555"
        const val CHANNEL_NAME="android"
        var stopKeepLive=false
        fun startService(context: Context) {
            val intent = Intent(context, LatestService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }
    }

    /**
     * 在当前服务中 开启并绑定保活服务
     */
    private fun bindLiveService(){
          application.startService(Intent(this, KeepliveService::class.java))
          connection=object:ServiceConnection{
            override fun onServiceDisconnected(name: ComponentName?) {//断开后重新尝试
               Log.e(TAG,"onServiceDisconnected:$name")
                if (!stopKeepLive){
                    try {
                        bindLiveService()
                    }catch (e:Exception){

                    }
                }
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                serviceMessenger= Messenger(service)
                Log.e(TAG,"onServiceConnected:$name   ---sendHeartBeat")
                sendHeartBeat()
            }

        }
        application.bindService(Intent(this, KeepliveService::class.java),
                connection as ServiceConnection,Context.BIND_AUTO_CREATE)
    }

    fun  sendHeartBeat(){
        try {
            val message=Message.obtain()
            message.arg1=HEART_BEAT
            if (clientMessenger.binder?.pingBinder()!=true){
                clientMessenger= Messenger(ClientMessengerHandler(this))
            }
            //这句话很重要
            message.replyTo=clientMessenger
            if (serviceMessenger?.binder?.pingBinder()==true){
                serviceMessenger?.send(message)
            }
        }catch (e:Exception){

        }
    }



    class ClientMessengerHandler(service: LatestService):Handler(){
        val WRS=WeakReference(service)
        override fun handleMessage(msg: Message) {

            if (HEART_BEAT_OK==msg?.arg1){
                postDelayed({//这里又延迟了三十秒
                    val service=WRS.get()
                    service?.sendHeartBeat()
                }, HEART_BEAT_INTERVAL)
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
        bindLiveService()
        val notification=if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
           val manager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
           val notificationChannel=NotificationChannel(CHANNEL_ID, CHANNEL_NAME,NotificationManager.IMPORTANCE_HIGH)
           manager.createNotificationChannel(notificationChannel)
           Notification.Builder(this, CHANNEL_ID).build()
        }else{
            Notification()
        }
//        startForeground(666,notification)

    }



}