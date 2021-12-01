package com.dawn.zgstep.player

import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.ViewGroup
import android.widget.FrameLayout

/**
 * Create by rye
 * at 2020/12/28
 * @description:
 */
class PlayerController : IPlayerController {

    private var mMediaPlayer: IMediaPlayer? = null


    /**
     * 播放视频
     */
    override fun goPlay(videoDetail: VideoDetail) {
        if (mMediaPlayer == null) {
            mMediaPlayer = IMediaPlayer.create()
        }
        setSurface(videoDetail)
        mMediaPlayer?.apply {
            reset()
            setDataSource(videoDetail.url)
            prepareAsync()
            setOnPreparedListener(object : IMediaPlayer.OnPreparedListener {
                override fun onPrepared(var1: IMediaPlayer?) {
                    mMediaPlayer?.start()
                }
            })
        }
    }

    //TODO 抽离播放容器逻辑
    private fun setSurface(videoDetail: VideoDetail) {
        val container = videoDetail.container ?: return
        val videoContainer = container.getChildAt(0)
        if (videoContainer != null) return
        //SurfaceView
        val surfaceView = SurfaceView(container.context)
        surfaceView.layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        container.addView(surfaceView, 0)
        setHolder(surfaceView)
    }

    /**
     * 播放音频
     */
    override fun goPlayAudio(url: String) {
        if (mMediaPlayer == null) {
            mMediaPlayer = IMediaPlayer.create()
        }
        mMediaPlayer?.apply {
            setDataSource(url)
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
        surfaceView?.holder?.apply {
            addCallback(SurfaceCallback(this@PlayerController))
        }
    }

    override fun prepareSync() {
        mMediaPlayer?.prepareAsync()
    }

    override fun getMediaPlayer(): IMediaPlayer? {
        return mMediaPlayer
    }

    override fun pause() {
        if (mMediaPlayer?.isPlaying() == true) {
            mMediaPlayer?.pause()
        }
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
    fun goPlay(videoDetail: VideoDetail)
    fun pause()
    fun replay()
    fun release()
    fun setDisplay(holder: SurfaceHolder?)
    fun prepareSync()
    fun start()
    fun goPlayAudio(url: String)
    fun getMediaPlayer():IMediaPlayer?

    companion object {
        fun create(): IPlayerController {
            return PlayerController()
        }
    }
}


class VideoDetail {
    var container: FrameLayout? = null
    var url: String? = null
    var name: String? = null
    var duration: Long? = null
}