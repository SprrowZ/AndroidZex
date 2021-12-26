package com.dawn.zgstep.player.base

import com.dawn.zgstep.player.PlayerController
import com.dawn.zgstep.player.VideoDetail
import com.dawn.zgstep.player.service.IMediaService

/**
 * Create by  [Rye]
 *
 * at 2021/12/7 3:42 下午
 */
interface IPlayerController {
    fun goPlay(videoDetail: VideoDetail)
    fun getMediaService():IMediaService?
    fun release()
    fun goPlayAudio(audioDetail: VideoDetail)
    fun getContext()

    companion object {
        fun create(): IPlayerController {
            return PlayerController()
        }
    }
}