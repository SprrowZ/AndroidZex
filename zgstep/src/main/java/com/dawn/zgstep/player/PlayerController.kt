package com.dawn.zgstep.player

import android.view.SurfaceHolder
import android.widget.FrameLayout
import com.dawn.zgstep.player.base.ILayerRoot
import com.dawn.zgstep.player.base.IPlayerController
import com.dawn.zgstep.player.base.LayersRoot

/**
 * Create by rye
 * at 2020/12/28
 * @description:
 */
class PlayerController : IPlayerController {

    private var mMediaPlayer: IMediaPlayer? = null
    private var mLayerRoot: ILayerRoot? = null

    /**
     * 播放视频
     */
    override fun goPlay(videoDetail: VideoDetail) {
        if (mMediaPlayer == null) {
            mMediaPlayer = IMediaPlayer.create()
            //registerContainerObserver()
        }
//        setSurface(videoDetail)
        initLayers(videoDetail)
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

    private fun initLayers(videoDetail: VideoDetail) {
        videoDetail.container?.let {
            if (mLayerRoot == null) {
                LayersRoot.create(this, it)
            }
        }
    }


//    //TODO 抽离播放容器逻辑
//    private fun setSurface(videoDetail: VideoDetail) {
//        val container = videoDetail.container ?: return
//        val videoContainer = container.getChildAt(0)
//        if (videoContainer != null) return
//        //SurfaceView
//        val surfaceView = SurfaceView(container.context)
//        surfaceView.layoutParams = FrameLayout.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.MATCH_PARENT
//        )
//        container.addView(surfaceView, 0)
//        setHolder(surfaceView)
//    }

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

//    private fun setHolder(surfaceView: SurfaceView?) {
//        surfaceView?.holder?.apply {
//            addCallback(SurfaceCallback(this@PlayerController))
//        }
//    }

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


class VideoDetail {
    var container: FrameLayout? = null
    var url: String? = null
    var name: String? = null
    var duration: Long? = null
}