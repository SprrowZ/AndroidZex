package com.dawn.zgstep.player.service

import com.dawn.zgstep.player.IMediaPlayer
import com.dawn.zgstep.player.base.IPlayerController
import java.util.*
import kotlin.collections.ArrayList

/**
 * Create by  [Rye]
 *
 * at 2021/12/9 8:59 下午
 */
class MediaService(private val mIPlayerController: IPlayerController) : IMediaService {

    private val mPlayerOnPreparedListeners =
        Collections.synchronizedList(ArrayList<OnPreparedListener>())

    private val mMediaPlayer: IMediaPlayer?
        get() {
            return mIPlayerController.getMediaPlayer()
        }



    override fun registerOnPreparedListener(preparedListener: OnPreparedListener) {
        if (mPlayerOnPreparedListeners.contains(preparedListener)) return
        mPlayerOnPreparedListeners.add(preparedListener)
    }

    override fun unregisterOnPreparedListener(preparedListener: OnPreparedListener) {
        if (mPlayerOnPreparedListeners.contains(preparedListener)) {
            mPlayerOnPreparedListeners.remove(preparedListener)
        }
    }


}

interface OnPreparedListener {
    fun onPrepared(mp: IMediaPlayer?)
}

interface IMediaService {
    fun registerOnPreparedListener(preparedListener: OnPreparedListener)
    fun unregisterOnPreparedListener(preparedListener: OnPreparedListener)
}