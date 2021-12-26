/*
 * Copyright (c) 2015-2020 BiliBili Inc.
 */

package com.dawn.zgstep.player

import android.content.Context
import android.os.Bundle
import android.os.Looper


abstract class AbstractMediaPlayer : IMediaPlayer {
    private var mOnPreparedListener: IMediaPlayer.OnPreparedListener? = null
    private var mOnCompletionListener: IMediaPlayer.OnCompletionListener? = null
    private var mOnBufferingUpdateListener: IMediaPlayer.OnBufferingUpdateListener? = null
    private var mOnSeekCompleteListener: IMediaPlayer.OnSeekCompleteListener? = null
    private var mOnVideoSizeChangedListener: IMediaPlayer.OnVideoSizeChangedListener? = null
    private var mOnErrorListener: IMediaPlayer.OnErrorListener? = null
    private var mOnInfoListener: IMediaPlayer.OnInfoListener? = null
    private var mOnTimedTextListener: IMediaPlayer.OnTimedTextListener? = null
    private var mPlayerClockChangedListener: IMediaPlayer.OnPlayerClockChangedListener? = null

    override fun setLogEnabled(p0: Boolean) {
        //Deprecated method, ignore
    }

    override fun setWakeMode(context: Context?, mode: Int) {
        //Deprecated method ignore
    }

    override fun setKeepInBackground(p0: Boolean) {
        //Deprecated method ignore
    }

    override fun isPlayable(): Boolean {
        return true
    }

    override fun setOnPreparedListener(listener: IMediaPlayer.OnPreparedListener?) {
        this.mOnPreparedListener = listener
    }

    override fun setOnCompletionListener(listener: IMediaPlayer.OnCompletionListener?) {
        this.mOnCompletionListener = listener
    }

    override fun setOnBufferingUpdateListener(listener: IMediaPlayer.OnBufferingUpdateListener?) {
        this.mOnBufferingUpdateListener = listener
    }

    override fun setOnSeekCompleteListener(listener: IMediaPlayer.OnSeekCompleteListener?) {
        this.mOnSeekCompleteListener = listener
    }

    override fun setOnVideoSizeChangedListener(listener: IMediaPlayer.OnVideoSizeChangedListener?) {
        this.mOnVideoSizeChangedListener = listener
    }

    override fun setOnErrorListener(listener: IMediaPlayer.OnErrorListener?) {
        this.mOnErrorListener = listener
    }

    override fun setOnInfoListener(listener: IMediaPlayer.OnInfoListener?) {
        this.mOnInfoListener = listener
    }

    override fun setOnTimedTextListener(listener: IMediaPlayer.OnTimedTextListener?) {
        this.mOnTimedTextListener = listener
    }

    override fun setOnPlayerClockChangedListener(looper: Looper?, listener: IMediaPlayer.OnPlayerClockChangedListener?) {
        this.mPlayerClockChangedListener = listener
    }

    open fun releaseListeners() {
        this.mOnPreparedListener = null
        this.mOnBufferingUpdateListener = null
        this.mOnCompletionListener = null
        this.mOnSeekCompleteListener = null
        this.mOnVideoSizeChangedListener = null
        this.mOnErrorListener = null
        this.mOnInfoListener = null
        this.mOnTimedTextListener = null
        this.mPlayerClockChangedListener = null
    }

    protected fun notifyOnPrepared() {
        this.mOnPreparedListener?.onPrepared(this)
    }

    protected fun notifyOnCompletion() {
        this.mOnCompletionListener?.onCompletion(this)
    }

    protected fun notifyOnBufferingUpdate(percent: Int) {
        this.mOnBufferingUpdateListener?.onBufferingUpdate(this, percent)
    }

    protected fun notifyOnSeekComplete() {
        this.mOnSeekCompleteListener?.onSeekComplete(this)
    }

    protected fun notifyOnVideoSizeChanged(width: Int, height: Int, sarNum: Int, sarDen: Int) {
        this.mOnVideoSizeChangedListener?.onVideoSizeChanged(this, width, height, sarNum, sarDen)
    }

    protected fun notifyOnError(what: Int, extra: Int): Boolean {
        return this.mOnErrorListener?.onError(this, what, extra) ?: true
    }

    protected fun notifyOnInfo(what: Int, extra: Int, args: Bundle?): Boolean {
        try {
            this.mOnInfoListener?.onInfo(this, what, extra, args)
        } catch (var5: NullPointerException) {
            return false
        }
        return true
    }

    protected fun notifyOnPlayerClockChanged(speed: Float, position: Long) {
        mPlayerClockChangedListener?.onPlayerClockChanged(this, speed, position)
    }

//    override fun setDataSource(mediaDataSource: IMediaPlayer.IMediaDataSource) {
//        throw UnsupportedOperationException()
//    }
}
enum class PlayerInfoState(val state:Int) {
    MEDIA_INFO_VIDEO_RENDERING_START(10000),
    MEDIA_INFO_BUFFERING_START(10001),
    MEDIA_INFO_BUFFERING_END(10002),
    MEDIA_INFO_UNKNOWN(10003)
}