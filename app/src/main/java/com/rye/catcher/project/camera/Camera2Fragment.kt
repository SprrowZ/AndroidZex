package com.rye.catcher.project.camera

import android.content.Context
import android.graphics.BitmapFactory
import android.hardware.camera2.*
import android.os.Build
import androidx.annotation.RequiresApi
import android.view.TextureView
import android.widget.ImageView
import com.dawn.zgstep.tests.runOnMainThread
import com.rye.base.BaseFragment
import com.rye.base.utils.Handler_
import com.rye.catcher.R
import java.lang.Exception


/**
 * camera2 相机使用
 *
 */
class Camera2Fragment : BaseFragment() {
    //看来得手动findViewById才行
    private lateinit var mTextureView: TextureView

    private lateinit var mReverseView: ImageView
    private lateinit var takePhoto: ImageView
    private lateinit var mPictureView: ImageView
    private var mCameraManager: Camera2Manager? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_camera2
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initWidget() {
        super.initWidget()
        view?.apply {
            mTextureView = findViewById(R.id.textureView)
            takePhoto = findViewById(R.id.takePhoto)
            mPictureView = findViewById(R.id.picture)
            mReverseView = findViewById(R.id.reverse)
        }

        takePhoto.setOnClickListener {
            mCameraManager?.capture()
        }

        mReverseView.setOnClickListener {
            //switchCamera()
            mCameraManager?.switchCamera()
        }
        initManager()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initManager() {
        mCameraManager = Camera2Manager(activity!!, mTextureView)
        mCameraManager?.setCameraCallback(mCameraCallback)
    }

    private val mCameraCallback = object : ICameraEventCallback {
        override fun onCaptureImg(byteArray: ByteArray) {
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size, null)

            Handler_.getInstance().post{
                mPictureView.setImageBitmap(bitmap)
            }
        }

    }


    /**
     * 是否支持Camera2，后期适配可用                =。=
     */
    private fun supportCamera2(mContext: Context): Boolean {
        if (mContext == null) return false
        //api小于21
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return false
        var support = true
        try {
            val manager = mContext.getSystemService(Context.CAMERA_SERVICE) as CameraManager
            val idList = manager.cameraIdList

            if (idList.isEmpty()) {
                support = false
            } else {
                for (id in idList) {
                    if (id == null || id.isEmpty()) {
                        support = false
                        break
                    }
                    val characteristics = manager.getCameraCharacteristics(id)
                    val supportLevel =
                        characteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)
                    if (supportLevel == CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY) {//只支持camera1
                        support = false
                        break
                    }

                }
            }
        } catch (e: Exception) {

        } finally {

        }
        return support
    }

}
