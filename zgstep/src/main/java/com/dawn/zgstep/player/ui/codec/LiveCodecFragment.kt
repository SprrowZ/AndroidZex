package com.dawn.zgstep.player.ui.codec

import android.hardware.Camera
import android.util.Log
import android.view.SurfaceHolder
import com.dawn.zgstep.R
import com.dawn.zgstep.player.media.encode.MediaEncoder
import com.rye.base.BaseFragment
import com.rye.base.media.CameraPreview
import com.rye.base.media.ICameraCallback

/**
 * Create by  [Rye]
 *
 * at 2022/1/7 1:58 下午
 */
class LiveCodecFragment : BaseFragment() {

    private var mCameraView: CameraPreview? = null
    private var mMediaEncoder: MediaEncoder? = null

    companion object {
        @JvmStatic
        fun create(): LiveCodecFragment {
            return LiveCodecFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_codec_live
    }

    override fun initWidget() {
        mCameraView = mRoot?.findViewById(R.id.camera_surface)
    }

    private val mCameraCallback = object : ICameraCallback {
        override fun surfaceCreated(holder: SurfaceHolder) {
            Log.i("RRye","surfaceCreated:$mMediaEncoder")
            if (mMediaEncoder == null) {
                mMediaEncoder = MediaEncoder.create()
                mMediaEncoder?.startVideoEncode()
            }
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {
               mMediaEncoder?.stop()
        }

        override fun onPreviewFrame(data: ByteArray, camera: Camera) {
            Log.e("RRye","data:${data.size}")
            mMediaEncoder?.putVideoData(data)
        }

    }

    override fun initEvent() {
        mCameraView?.setOnClickListener {
            mCameraView?.focus()
        }
        mCameraView?.setCallback(mCameraCallback)
    }
}