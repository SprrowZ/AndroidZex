package com.dawn.zgstep.player

import android.view.SurfaceHolder

/**
 * Create by  [Rye]
 *
 * at 9/4/21 5:35 PM
 */
class SurfaceCallback(private val playerController: PlayerController?) : SurfaceHolder.Callback {
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        playerController?.setDisplay(holder)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        playerController?.release()

    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        playerController?.prepareSync()
    }
}