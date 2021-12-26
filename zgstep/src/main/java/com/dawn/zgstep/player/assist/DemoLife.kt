package com.dawn.zgstep.player.assist

import android.util.Log
import com.dawn.zgstep.player.IMediaPlayer

/**
 * Create by  [Rye]
 *
 * at 2021/12/26 4:56 下午
 */
class DemoLife {

    private val mInstance = object : IMediaPlayer.OnProgressListener {
        override fun onProgress(duration: Long, progress: Long) {
        }
    }

    init {
        Log.i("RRye", "init")
        logs()
    }

    private val mInstance3 = object : IMediaPlayer.OnProgressListener {
        override fun onProgress(duration: Long, progress: Long) {
        }
    }

    private fun logs() {
        Log.i("RRye", "mInstance:$mInstance,mInstance3:$mInstance3")
    }
}