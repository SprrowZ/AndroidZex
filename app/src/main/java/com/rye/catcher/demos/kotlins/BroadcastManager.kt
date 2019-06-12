package com.rye.catcher.demos.kotlins

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.support.v4.content.LocalBroadcastManager
import com.rye.catcher.utils.EchatAppUtil

/**
 *Created by 18041at 2019/5/7
 *Function:静态类，发送广播用
 */
object  BroadcastManager {
    const val  BROADCAST_FLAG:String="FLAG_TEST"
    const val BROADCAST_KEY:String="key"
    const val BROADCAST_VALUE:String="value001"
    private lateinit var filter: IntentFilter
    fun registerReceiver (receiver:BroadcastReceiver,filter: IntentFilter){
        LocalBroadcastManager.getInstance(EchatAppUtil.getAppContext()!!).registerReceiver(receiver,filter)
    }
    fun unregisterReceiver(receiver: BroadcastReceiver){
        LocalBroadcastManager.getInstance(EchatAppUtil.getAppContext()!!).unregisterReceiver(receiver)
    }

    /**
     * 发送登陆成功的广播
     */
    fun sendLoginSuccessBroadcast(){
        val intent=Intent()
        intent.setAction(BROADCAST_FLAG)
        intent.putExtra(BROADCAST_KEY, BROADCAST_VALUE)
        LocalBroadcastManager.getInstance(EchatAppUtil.getAppContext()).sendBroadcast(intent)
    }

    /**
     * 注册登陆成功的事件
     */
    fun registerLoginSuccessReceiver(receiver: BroadcastReceiver){
        filter=IntentFilter()
        filter.addAction(BROADCAST_FLAG)
        registerReceiver(receiver, filter)
    }

}