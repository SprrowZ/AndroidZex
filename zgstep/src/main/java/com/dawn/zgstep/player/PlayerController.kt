package com.dawn.zgstep.player

import android.widget.FrameLayout
import com.dawn.zgstep.player.base.IPlayerController
import com.dawn.zgstep.player.service.IMediaService
import com.dawn.zgstep.player.service.PlayerMediaService

/**
 * Create by rye
 * at 2020/12/28
 * @description:
 */
class PlayerController : IPlayerController {

    //播放器操作相关service
    private var mPlayerMediasService: IMediaService? = null

    init {
        registerService()
    }

    /**
     * 播放视频
     */
    override fun goPlay(videoDetail: VideoDetail) {
        mPlayerMediasService?.goPlay(videoDetail)
    }

    override fun goPlayAudio(audioDetail: VideoDetail) {
        mPlayerMediasService?.goPlayAudio(audioDetail.url!!)
    }

    override fun getMediaService(): IMediaService? {
        return mPlayerMediasService
    }

    override fun release() {
        //TODO release掉所有service
        mPlayerMediasService?.release()
        mPlayerMediasService = null
    }

    private fun registerService() {
        mPlayerMediasService = PlayerMediaService.create(this)
    }

    override fun getContext() {
        TODO("Not yet implemented")
    }

}


class VideoDetail {
    var container: FrameLayout? = null
    var url: String? = null
    var name: String? = null
    var duration: Long? = null
}