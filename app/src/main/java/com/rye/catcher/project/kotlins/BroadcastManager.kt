package com.rye.catcher.project.kotlins

import android.content.Intent

/**
 *Created by 18041at 2019/5/7
 *Function:
 */
object  BroadcastManager {
    const val  BROADCAST_FLAG:String="FLAG_TEST"
    fun sendNoBroadcast(){
        var intent=Intent()
        intent.addFlags(BROADCAST_FLAG)
    }
}