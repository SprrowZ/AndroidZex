package com.dawn.zgstep.player.base

import android.view.SurfaceHolder
import com.dawn.zgstep.player.IMediaPlayer
import com.dawn.zgstep.player.PlayerController
import com.dawn.zgstep.player.VideoDetail

/**
 * Create by  [Rye]
 *
 * at 2021/12/7 3:42 下午
 */
interface IPlayerController {
    fun goPlay(videoDetail: VideoDetail)
    fun pause()
    fun replay()
    fun release()
    fun setDisplay(holder: SurfaceHolder?)
    fun prepareSync()
    fun start()
    fun goPlayAudio(url: String)
    fun getMediaPlayer(): IMediaPlayer?

    //监听


    companion object {
        fun create(): IPlayerController {
            return PlayerController()
        }
    }
}