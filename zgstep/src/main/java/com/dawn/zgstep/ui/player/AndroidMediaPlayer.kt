package com.dawn.zgstep.ui.player

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.Surface
import android.view.SurfaceHolder
import java.io.FileDescriptor

/**
 * Create by rye
 * at 2020/12/28
 * @description:后续重写系统播放器逻辑
 */
class AndroidMediaPlayer : AbstractMediaPlayer {
    private val mMediaPlayer: MediaPlayer = MediaPlayer()
    private var mIsPrepared = false
    private var mDataSource: String? = null
    private var mIsReleased = false
    private var mSurface: Surface? = null

    constructor() {
        setPlayerListener()
    }

    companion object {
        private const val TAG = "AndroidMediaPlayer"
    }

    private fun setPlayerListener() {
        mMediaPlayer.setOnPreparedListener(mPreparedListener)
        mMediaPlayer.setOnCompletionListener(mCompletionListener)
        mMediaPlayer.setOnBufferingUpdateListener(mBufferingUpdateListener)
        mMediaPlayer.setOnSeekCompleteListener(mSeekCompletionListener)
        mMediaPlayer.setOnVideoSizeChangedListener(mVideoSizeChangedListener)
        mMediaPlayer.setOnErrorListener(mErrorListener)
        mMediaPlayer.setOnInfoListener(mInfoListener)
    }

    private val mPreparedListener = MediaPlayer.OnPreparedListener {
        mIsPrepared = true
        notifyOnPrepared()
    }
    private val mCompletionListener = MediaPlayer.OnCompletionListener {
        notifyOnCompletion()
    }
    private val mBufferingUpdateListener = MediaPlayer.OnBufferingUpdateListener { _, percent ->
        notifyOnBufferingUpdate(percent)
    }
    private val mSeekCompletionListener = MediaPlayer.OnSeekCompleteListener {
        notifyOnSeekComplete()
    }
    private val mVideoSizeChangedListener = MediaPlayer.OnVideoSizeChangedListener { _, width, height ->
        notifyOnVideoSizeChanged(width, height, 1, 1)
    }
    private val mErrorListener = MediaPlayer.OnErrorListener { _, what, extra ->
        notifyOnError(what, extra)
    }
    private val mInfoListener = MediaPlayer.OnInfoListener { _, what, extra ->
        notifyOnInfo(what, extra, null)
    }


    override fun setDisplay(holder: SurfaceHolder?) {
        try {
            mSurface = if (holder == null) {
                mMediaPlayer.setSurface(null)
                null
            } else {
                mMediaPlayer.setDisplay(holder)
                holder.surface
            }
        } catch (e: Exception) {
            Log.e(TAG, "setDisplay error:$e")
        }
    }

    override fun setDataSource(context: Context?, uri: Uri?) {
        mMediaPlayer.setDataSource(context!!, uri!!)
    }

    override fun setDataSource(context: Context?, uri: Uri?, var3: Map<String?, String?>?) {
        mMediaPlayer.setDataSource(context!!, uri!!, var3)
    }

    override fun setDataSource(fileDescriptor: FileDescriptor?) {
        mMediaPlayer.setDataSource(fileDescriptor)
    }

    override fun setDataSource(url: String?) {//可以传入URI或者URL
        if (url.isNullOrEmpty()) {
            throw  IllegalArgumentException("uri is empty or null!")
        }
        mIsPrepared = false
        mDataSource = url
        val uri = Uri.parse(url)
        val scheme = uri.scheme
        Log.i(TAG, "setDataSource url:$url")
        if (!scheme.isNullOrEmpty() && scheme.equals("file", ignoreCase = true)) {
            mMediaPlayer.setDataSource(uri.path)
        } else {
            mMediaPlayer.setDataSource(url)
        }
    }

    override fun getDataSource(): String? {
        return mDataSource
    }

    override fun prepareAsync() {
        try {
            mMediaPlayer.prepareAsync()
        } catch (e: Exception) {
            Log.e(TAG, "prepare Async exception e:$e")
        }
    }

    override fun start() {
        mMediaPlayer.start()
    }

    override fun stop() {
        mMediaPlayer.stop()
    }

    override fun pause() {
        mMediaPlayer.pause()
    }

    override fun setScreenOnWhilePlaying(scrrenOn: Boolean) {//播放的时候保持屏幕常亮
        mMediaPlayer.setScreenOnWhilePlaying(scrrenOn)
    }

    override fun getVideoWidth(): Int {
        return mMediaPlayer.videoWidth
    }

    override fun getVideoHeight(): Int {
        return mMediaPlayer.videoHeight
    }

    override fun isPlaying(): Boolean {
        return try {
            mMediaPlayer.isPlaying
        } catch (e: IllegalStateException) {
            Log.w(TAG, "isPlaying:$e")
            false
        }
    }

    override fun seekTo(seekPos: Long) {
        val duration = getDuration()
        var targetPos: Int = 0
        if (duration < 0) return
        if (seekPos >= duration) {
            targetPos = (duration - 1000).toInt()
        }
        mMediaPlayer.seekTo(targetPos)
    }

    override fun getCurrentPosition(): Long {
        return if (mIsPrepared) {
            try {
                mMediaPlayer.currentPosition
            } catch (e: Exception) {
                Log.e(TAG, "getCurrentPosition error :$e")
                0
            } as Long
        } else {
            -1
        }
    }

    override fun getDuration(): Long {
        return try {
            mMediaPlayer.duration.toLong()
        } catch (e: IllegalStateException) {
            Log.w(TAG, "getDuration:$e")
            0
        }

    }

    override fun release() {
        if (mIsReleased) return
        mIsReleased = true
        try {
            mMediaPlayer.release()
        } catch (e: SecurityException) {
            Log.e(TAG, "WTF...MediaPlayer.release write settings without permission")
        }
    }

    override fun reset() {
        try {
            mMediaPlayer.reset()
        } catch (e: Exception) {
            Log.d(TAG, "reset:$e")
        } catch (e: Error) {
            Log.d(TAG, "reset:$e")
        }
    }

    override fun setVolume(leftVolume: Float, rightVolume: Float) {
        mMediaPlayer.setVolume(leftVolume, rightVolume)
    }

    override fun getAudioSessionId(): Int {
        return mMediaPlayer.audioSessionId
    }

    override fun setAudioStreamType(streamMusic: Int) {
        mMediaPlayer.setAudioStreamType(streamMusic)
    }

    override fun getVideoSarNum(): Int {
        return 1
    }

    override fun getVideoSarDen(): Int {
        return 1
    }

    override fun setLooping(looping: Boolean) {
        mMediaPlayer.isLooping = looping
    }

    override fun isLooping(): Boolean {
        return mMediaPlayer.isLooping
    }

    override fun setSurface(surface: Surface?) {
        if (surface == null) {
            mMediaPlayer.setSurface(null)
            mSurface = null
        } else {
            mSurface = surface
            mMediaPlayer.setSurface(surface)
        }
    }

}

