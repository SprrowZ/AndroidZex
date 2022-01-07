package com.rye.base.media

import android.hardware.Camera
import android.view.SurfaceHolder

/**
 * Create by  [Rye]
 *
 * at 2022/1/7 2:24 下午
 */
interface ICameraCallback {
    fun surfaceCreated(holder: SurfaceHolder)
    fun surfaceChanged(holder: SurfaceHolder,format:Int,width:Int,height:Int)
    fun surfaceDestroyed(holder: SurfaceHolder)
    fun onPreviewFrame(data:ByteArray,camera:Camera)
}