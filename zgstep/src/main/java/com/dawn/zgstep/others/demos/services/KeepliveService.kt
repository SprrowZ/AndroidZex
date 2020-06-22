package com.dawn.zgstep.others.demos.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.util.Log
import java.lang.Exception
import java.lang.ref.WeakReference

class KeepliveService :Service() {
    var heartBeatThread: ServiceHeartBeatThread? = null
    companion object{
        const val TAG="KeepliveService"
        const val HEART_BEAT = 1
        const val HEART_BEAT_OK = 2
        const val STOP_HEART_BEAT = 3
        const val HEART_BEAT_INTERVAL = 30000L

    }

    override fun onBind(intent: Intent?): IBinder? {
       return Messenger(MessengerHandler(heartBeatThread, WeakReference(this))).binder
    }

    override fun onCreate() {
        super.onCreate()
        heartBeatThread= ServiceHeartBeatThread(application)
        heartBeatThread?.start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }



    class MessengerHandler(val thread: ServiceHeartBeatThread?, val service:WeakReference<Service>):Handler(){

        var isKeepLive=true
        override fun handleMessage(msg: Message) {
            if (HEART_BEAT ==msg.arg1){//从LatestService
                Log.d(TAG,"client heart beat")
                if (isKeepLive){
                    try {
                       thread?.hasHeartBeat=true
                        val clientMessenger=msg.replyTo  //从replyTo中获取messenger
                        val newMessage=Message.obtain()
                        newMessage.arg1= HEART_BEAT_OK
                        if (clientMessenger?.binder?.pingBinder()==true){
                            clientMessenger.send(newMessage)
                        }
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }else if (STOP_HEART_BEAT ==msg.arg1){//停止心跳
                    thread?.stopRunning()
                    isKeepLive=false
                    val  keepliveService=service.get()
                    keepliveService?.let {
                        keepliveService.stopSelf()
                    }
                }
            }
        }
    }

    class ServiceHeartBeatThread(val context: Context):Thread(){
        var hasHeartBeat=false
        var isRunning=true
        override fun run() {
            super.run()
            while (isRunning){
                try {
                    sleep(HEART_BEAT_INTERVAL)

                }catch (e:Exception){
                    e.printStackTrace()
                }
                Log.e(TAG,"KeepliveService hasHeartBeat:$hasHeartBeat")
                if (!hasHeartBeat&&isRunning){
                    try {
                        LatestService.startService(context)
                    }catch (e:Exception){

                    }
                }else{
                    hasHeartBeat=false
                }

            }

        }

        fun  stopRunning(){
            isRunning=false
        }
    }
}