package com.dawn.zgstep.player.assist

import android.os.Handler
import android.os.Looper
import com.dawn.zgstep.player.IMediaPlayer
import com.dawn.zgstep.player.service.PlayerMediaService

/**
 * Create by  [Rye]
 *
 * at 2021/12/26 11:28 上午
 */
class ProgressCountDownHandler(
    mLooper: Looper,
    private val mMediaService: PlayerMediaService?,
    private val mCountInterval: Long = 1000
) {

    private var mHandler: Handler? = null
    private var mProgressListeners = mutableListOf<IMediaPlayer.OnProgressListener>()

    companion object {
        const val TAG = "ProgressCountDownHandler"
        const val UPDATE_PROGRESS_SIGNAL = 111
        fun create(
            mLooper: Looper,
            mediaService: PlayerMediaService,
            mCountInterval: Long = 1000
        ): ProgressCountDownHandler {
            return ProgressCountDownHandler(mLooper, mediaService, mCountInterval)
        }
    }

    private val mDuration: Long?
        get() {
            return mMediaService?.getMediaPlayer()?.getDuration()
        }

    private val mProgress: Long?
        get() {
            return mMediaService?.getMediaPlayer()?.getCurrentPosition()
        }

    init {
        mHandler = Handler(mLooper) {
            when (it.what) {
                UPDATE_PROGRESS_SIGNAL -> {
                    mProgressListeners.forEach { it ->
                        it.onProgress(mDuration ?: 0, mProgress ?: 0)
                    }
                    //TODO 暂停时，此处还会一直发，待确认需不需要暂停？
                    mHandler?.sendEmptyMessageDelayed(UPDATE_PROGRESS_SIGNAL,mCountInterval)
                }
            }
            true
        }
    }

    fun setListener(listeners: MutableList<IMediaPlayer.OnProgressListener>?) {
        if (listeners == null) return
        mProgressListeners.clear()
        mProgressListeners.addAll(listeners)
        countDown()
    }

    private fun countDown() {
        mHandler?.sendEmptyMessageDelayed(UPDATE_PROGRESS_SIGNAL, mCountInterval)
    }

    fun release() {
        mHandler?.removeCallbacksAndMessages(null)
        mHandler = null
    }
}