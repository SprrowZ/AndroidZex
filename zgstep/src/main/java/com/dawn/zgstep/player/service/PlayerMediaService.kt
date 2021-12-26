package com.dawn.zgstep.player.service

import android.os.Bundle
import android.os.Looper
import android.view.SurfaceHolder
import com.dawn.zgstep.player.IMediaPlayer
import com.dawn.zgstep.player.PlayerInfoState
import com.dawn.zgstep.player.VideoDetail
import com.dawn.zgstep.player.assist.ProgressCountDownHandler
import com.dawn.zgstep.player.base.ILayerRoot
import com.dawn.zgstep.player.base.IPlayerController
import com.dawn.zgstep.player.base.LayersRoot
import java.util.*
import kotlin.collections.ArrayList

/**
 * Create by  [Rye]
 *
 * at 2021/12/9 8:59 下午
 */
class PlayerMediaService(private val mPlayerController: IPlayerController) : IMediaService {

    private val mPlayerOnPreparedListeners =
        Collections.synchronizedList(ArrayList<IMediaPlayer.OnPreparedListener>())
    private val mPlayerOnCompletionListeners =
        Collections.synchronizedList(ArrayList<IMediaPlayer.OnCompletionListener>())
    private val mPlayerProgressListeners =
        Collections.synchronizedList(ArrayList<IMediaPlayer.OnProgressListener>())

    private var mMediaPlayer: IMediaPlayer? = null
    private var mLayerRoot: ILayerRoot? = null
    private var mProgressCountDownHandler: ProgressCountDownHandler? = null

    private var mCurrentPlayState = PlayerState.STATE_IDLE

    companion object {
        fun create(mPlayerController: IPlayerController): IMediaService {
            return PlayerMediaService(mPlayerController)
        }
    }

    private val mLooper: Looper?
        get() {
            return mLayerRoot?.getRootContainer()?.handler?.looper
        }

    init {
        registerPlayerEventListeners()
    }

    override fun goPlay(videoDetail: VideoDetail) {
        if (mMediaPlayer == null) {
            mMediaPlayer = IMediaPlayer.create()
        }
//        setSurface(videoDetail)
        initLayers(videoDetail)
        mMediaPlayer?.apply {
            reset()
            setDataSource(videoDetail.url)
            prepareAsync()
            setCurrentPlayState(PlayerState.STATE_PREPARING)
            setOnPreparedListener(object : IMediaPlayer.OnPreparedListener {
                override fun onPrepared(mp: IMediaPlayer?) {
                    setCurrentPlayState(PlayerState.STATE_PREPARED)
                    mp?.start()
                    //更新进度
                    updateProgress()
                }
            })
        }
    }

    private fun updateProgress() {
        if (mLooper == null) return
        if (mProgressCountDownHandler == null) {
            mProgressCountDownHandler = ProgressCountDownHandler.create(mLooper!!,this)
        }
        mProgressCountDownHandler?.setListener(mPlayerProgressListeners)
    }

    private fun initLayers(videoDetail: VideoDetail) {
        videoDetail.container?.let {
            if (mLayerRoot == null) {
                mLayerRoot = LayersRoot.create(mPlayerController, it)
            }
        }
    }

    private fun setCurrentPlayState(playerState: PlayerState) {
        this.mCurrentPlayState = playerState
    }

    private fun isInPlayingState(): Boolean {
        return mMediaPlayer != null
                && mCurrentPlayState != PlayerState.STATE_ERROR
                && mCurrentPlayState != PlayerState.STATE_IDLE
                && mCurrentPlayState != PlayerState.STATE_START_ABORT
                && mCurrentPlayState != PlayerState.STATE_PLAYBACK_COMPLETED
    }

    override fun goPlayAudio(url: String) {
        if (mMediaPlayer == null) {
            mMediaPlayer = IMediaPlayer.create()
        }
        mMediaPlayer?.apply {
            setDataSource(url)
            prepareAsync()
            setOnPreparedListener(object : IMediaPlayer.OnPreparedListener {
                override fun onPrepared(mp: IMediaPlayer?) {
                    mp?.start()
                    //TODO 更新进度
                }

            })
        }
    }

    override fun pause() {
        if (isInPlayingState() && mMediaPlayer?.isPlaying() == true) {
            mMediaPlayer?.pause()
            setCurrentPlayState(PlayerState.STATE_PAUSED)
        }
    }

    override fun replay() {
        TODO("Not yet implemented")
    }

    override fun release() {
        mMediaPlayer?.release()
        mMediaPlayer = null
        setCurrentPlayState(PlayerState.STATE_IDLE)
        mProgressCountDownHandler?.release()
    }

    override fun setDisplay(holder: SurfaceHolder?) {
        mMediaPlayer?.setDisplay(holder)
    }

    override fun prepareSync() {
        mMediaPlayer?.prepareAsync()
    }

    override fun start() {
        mMediaPlayer?.start()
        setCurrentPlayState(PlayerState.STATE_PLAYING)
    }

    override fun resume() {
        if (isInPlayingState() && mMediaPlayer?.isPlaying() == false) {
            mMediaPlayer?.start()
            setCurrentPlayState(PlayerState.STATE_PLAYING)
        }
    }

    override fun getMediaPlayer(): IMediaPlayer? {
        return mMediaPlayer
    }


    private fun registerPlayerEventListeners() {
        mMediaPlayer?.setOnPreparedListener(object : IMediaPlayer.OnPreparedListener {
            override fun onPrepared(mp: IMediaPlayer?) {
                notifyOnPreparedEvent(mp)
            }
        })
        mMediaPlayer?.setOnErrorListener(object : IMediaPlayer.OnErrorListener {
            override fun onError(var1: IMediaPlayer?, what: Int, extra: Int): Boolean {

                return false
            }
        })
        mMediaPlayer?.setOnCompletionListener(object : IMediaPlayer.OnCompletionListener {
            override fun onCompletion(mp: IMediaPlayer?) {
                notifyOnCompletionEvent(mp)
            }
        })
        mMediaPlayer?.setOnInfoListener(object : IMediaPlayer.OnInfoListener {
            override fun onInfo(
                var1: IMediaPlayer?,
                what: Int,
                extra: Int,
                var4: Bundle?
            ): Boolean {
                when (what) {
                    PlayerInfoState.MEDIA_INFO_BUFFERING_START.state -> {
                        setCurrentPlayState(PlayerState.STATE_BUFFERING)
                    }
                    PlayerInfoState.MEDIA_INFO_BUFFERING_END.state -> {
                        setCurrentPlayState(PlayerState.STATE_BUFFERED)
                    }
                }
                return true
            }
        })
    }


    fun notifyOnPreparedEvent(mp: IMediaPlayer?) {
        mPlayerOnPreparedListeners.forEach {
            it.onPrepared(mp)
        }
    }

    fun notifyOnCompletionEvent(mp: IMediaPlayer?) {
        mPlayerOnCompletionListeners.forEach {
            it.onCompletion(mp)
        }
    }

    override fun registerOnCompletionListener(completionLister: IMediaPlayer.OnCompletionListener) {
        if (mPlayerOnCompletionListeners.contains(completionLister)) return
        mPlayerOnCompletionListeners.add(completionLister)
    }

    override fun unregisterOnCompletionListener(completionLister: IMediaPlayer.OnCompletionListener) {
        if (mPlayerOnCompletionListeners.contains(completionLister)) {
            mPlayerOnCompletionListeners.remove(completionLister)
        }
    }

    override fun registerOnPreparedListener(preparedListener: IMediaPlayer.OnPreparedListener) {
        if (mPlayerOnPreparedListeners.contains(preparedListener)) return
        mPlayerOnPreparedListeners.add(preparedListener)
    }

    override fun unregisterOnPreparedListener(preparedListener: IMediaPlayer.OnPreparedListener) {
        if (mPlayerOnPreparedListeners.contains(preparedListener)) {
            mPlayerOnPreparedListeners.remove(preparedListener)
        }
    }

    override fun registerOnProgressListener(progressListener: IMediaPlayer.OnProgressListener?) {
        if (mPlayerProgressListeners.contains(progressListener)) return
        mPlayerProgressListeners.add(progressListener)
    }

}

enum class PlayerState(val state: Int) {
    STATE_ERROR(-1),
    STATE_IDLE(0),
    STATE_PREPARING(1),
    STATE_PREPARED(2),
    STATE_PLAYING(3),
    STATE_PAUSED(4),
    STATE_PLAYBACK_COMPLETED(5),
    STATE_BUFFERING(6),
    STATE_BUFFERED(7),
    STATE_START_ABORT(8)
}


interface IMediaService {

    fun goPlay(videoDetail: VideoDetail)
    fun pause()
    fun replay()
    fun release()
    fun setDisplay(holder: SurfaceHolder?)
    fun prepareSync()
    fun start()
    fun goPlayAudio(url: String)
    fun getMediaPlayer(): IMediaPlayer?
    fun resume()

    fun registerOnPreparedListener(preparedListener: IMediaPlayer.OnPreparedListener)
    fun unregisterOnPreparedListener(preparedListener: IMediaPlayer.OnPreparedListener)

    fun registerOnCompletionListener(completionLister: IMediaPlayer.OnCompletionListener)
    fun unregisterOnCompletionListener(completionLister: IMediaPlayer.OnCompletionListener)

    fun registerOnProgressListener(progressListener: IMediaPlayer.OnProgressListener?)
}