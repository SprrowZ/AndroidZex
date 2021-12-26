package com.dawn.zgstep.player.layers

import android.view.SurfaceHolder
import com.dawn.zgstep.player.base.IPlayerController


/**
 * Create by  [Rye]
 *
 * at 9/4/21 5:35 PM
 */
class SurfaceCallback(private val playerController: IPlayerController?) : SurfaceHolder.Callback {
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        playerController?.release()

    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        playerController?.getMediaService()?.setDisplay(holder)
    }
}