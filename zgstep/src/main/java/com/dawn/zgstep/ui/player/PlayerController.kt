package com.dawn.zgstep.ui.player

/**
 * Create by rye
 * at 2020/12/28
 * @description:
 */
class PlayerController : IPlayerController {
    private var mMediaPlayer: IMediaPlayer? = null
    override fun goPlay(videoDetail: VideoDetail) {
        if (mMediaPlayer == null) {
            mMediaPlayer = IMediaPlayer.create()
        }
        mMediaPlayer?.setDataSource(videoDetail.url)
    }


    override fun pause() {
        TODO("Not yet implemented")
    }

    override fun replay() {
        TODO("Not yet implemented")
    }

    override fun release() {
        TODO("Not yet implemented")
    }

}

interface IPlayerController {
    fun goPlay(videoDetail: VideoDetail)
    fun pause()
    fun replay()
    fun release()
}

data class VideoDetail(val url: String)