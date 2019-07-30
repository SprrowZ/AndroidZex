package com.rye.catcher.base

import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log

class NetChangeReceiver:BroadcastReceiver() {
    //网络变化时调用
    @TargetApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context?, intent: Intent?) {
       judgeNet(context)
    }

    private fun judgeNet(context: Context?){
        val connectivityManager=context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo=connectivityManager.activeNetworkInfo

        val   networkCapabilities=
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            } else {
                TODO("VERSION.SDK_INT < M")
            }
        if (networkInfo==null){
            Log.e("netWork","没有网络连接...")
            return
        }
        when(networkInfo.subtype){
            ConnectivityManager.TYPE_WIFI->{
                Log.e("netWork","wifi连接...")
            }
            ConnectivityManager.TYPE_MOBILE->{
                Log.e("netWork","移动网络...")
            }

        }
    }
}