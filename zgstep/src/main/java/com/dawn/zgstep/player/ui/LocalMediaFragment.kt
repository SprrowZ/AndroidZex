package com.dawn.zgstep.player.ui

import android.graphics.SurfaceTexture
import android.os.Build
import android.view.TextureView
import androidx.annotation.RequiresApi
import com.dawn.zgstep.R
import com.dawn.zgstep.player.base.IPlayerController
import com.dawn.zgstep.player.media.localplay.LocalMediaManager
import com.rye.base.BaseFragment
import com.rye.base.utils.SDHelper

/**
 * |VideoExtractor+MediaCodec
 */
class LocalMediaFragment : BaseFragment() {
    private var mTextureView: TextureView? = null
    private lateinit var mLocalMediaManager: LocalMediaManager


    override fun getLayoutId(): Int {
        return R.layout.fragment_local_media
    }

    override fun initWidget() {
        super.initWidget()
        mTextureView = view?.findViewById(R.id.surface)
        mLocalMediaManager = LocalMediaManager()
    }

    override fun initEvent() {
        super.initEvent()
       mTextureView?.surfaceTextureListener = object :TextureView.SurfaceTextureListener{
           @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
           override fun onSurfaceTextureAvailable(
               surface: SurfaceTexture,
               width: Int,
               height: Int
           ) {
              mLocalMediaManager.setDataSource(getLocalVideoFilePath())
              mLocalMediaManager.prepare(surface)
              mLocalMediaManager.start()
           }

           override fun onSurfaceTextureSizeChanged(
               surface: SurfaceTexture,
               width: Int,
               height: Int
           ) {
           }

           override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
               return false
           }

           override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
           }

       }
    }

    //TODO 固定地址替换
    private fun getLocalVideoFilePath(): String {
        return SDHelper.storagePath + "长视频测试.mp4"
    }

    override fun onDestroy() {
        super.onDestroy()

    }


}