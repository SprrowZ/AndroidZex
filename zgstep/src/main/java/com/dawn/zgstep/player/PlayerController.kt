package com.dawn.zgstep.player

import android.view.SurfaceHolder
import android.view.SurfaceView

/**
 * Create by rye
 * at 2020/12/28
 * @description:
 */
class PlayerController : IPlayerController {

    private var mMediaPlayer: IMediaPlayer? = null
    private var mSurfaceHolder: SurfaceHolder? = null
    override fun goPlay(videoDetail: VideoDetail, surfaceView: SurfaceView?) {
        if (mMediaPlayer == null) {
            mMediaPlayer = IMediaPlayer.create()
        }
        setHolder(surfaceView)
        mMediaPlayer?.apply {
            setDataSource(videoDetail.url)
            prepareAsync()
            setOnPreparedListener(object : IMediaPlayer.OnPreparedListener {
                override fun onPrepared(var1: IMediaPlayer?) {
                    mMediaPlayer?.start()
                }
            })
        }
    }

    override fun start() {
        mMediaPlayer?.start()
    }

    private fun setHolder(surfaceView: SurfaceView?) {
        mSurfaceHolder = surfaceView?.holder

        mSurfaceHolder?.apply {
            setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
            addCallback(SurfaceCallback(this@PlayerController))
        }
    }

    override fun prepareSync() {
        mMediaPlayer?.prepareAsync()
    }

    override fun pause() {
        mMediaPlayer?.pause()
    }

    override fun replay() {

    }

    override fun release() {
        mMediaPlayer?.release()
        mMediaPlayer = null
    }

    override fun setDisplay(holder: SurfaceHolder?) {
        mMediaPlayer?.setDisplay(holder)
    }

}

interface IPlayerController {
    fun goPlay(videoDetail: VideoDetail, surfaceView: SurfaceView?)
    fun pause()
    fun replay()
    fun release()
    fun setDisplay(holder: SurfaceHolder?)
    fun prepareSync()
    fun start()

    companion object {
        fun create(): IPlayerController {
            return PlayerController()
        }
    }
}

data class VideoDetail(val url: String)