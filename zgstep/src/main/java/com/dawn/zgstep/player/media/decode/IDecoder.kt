package com.dawn.zgstep.player.media.decode

import android.media.MediaFormat

/**
 * Create by  [Rye]
 *
 * at 2021/12/27 7:47 下午
 */
interface IDecoder :Runnable {
     //暂停解码
    fun pause()
    //继续解码
    fun goOn()
    //停止解码
    fun stop()
    //是否正在解码
    fun isDecoding():Boolean
    //是否正在快进
    fun isSeeking():Boolean
    //是否已停止解码
    fun isStop():Boolean
    //TODO 设置状态监听器
//    fun setStateListener(listener:?)
    //获取视频宽
    fun getWidth():Int
    //获取视频高
    fun getHeight():Int
    //获取视频长度
    fun getDuration():Long
    //获取视频旋转角度
    fun getRotationAngle():Int
    //获取音视频对应的格式参数
    fun getMediaFormat():MediaFormat?
    //获取音视频对应的媒体轨道
    fun getTrack():Int
    //获取解码的文件路径
    fun getFilePath():String
}