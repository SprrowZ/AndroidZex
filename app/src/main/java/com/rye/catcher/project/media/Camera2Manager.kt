package com.rye.catcher.project.media

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.*
import android.hardware.camera2.*
import android.media.ImageReader
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.util.Size
import android.view.Surface
import android.view.TextureView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.rye.catcher.utils.ImageUtils
import java.util.*
import kotlin.collections.ArrayList

/**
 * Create by  [Rye]
 *
 * at 2021/11/6 12:21 下午
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class Camera2Manager(private val mActivity: Activity, private val mTextureView: TextureView) {
    companion object {
        private const val TAG = "Camera2Manager"
        private const val PREVIEW_WIDTH = 720
        private const val PREVIEW_HEIGHT = 1280
        private const val CAMERA_ID_BACK = "0"
        private const val CAMERA_ID_FRONT = "1"

    }

    private lateinit var mCameraManager: CameraManager
    private var mCameraCallback: ICameraEventCallback? = null

    //默认使用后置摄像头
    private var mCameraFacing = CameraCharacteristics.LENS_FACING_BACK

    //当前相机的各种特性
    private lateinit var mCameraCharacteristics: CameraCharacteristics

    //摄像头传感器方向，用于设置保存照片的方向！！
    private var mCameraSensorOrientation = 0

    private var mDisplayRotation = 0 //mActivity.windowManager.defaultDisplay.rotation  //手机方向

    //默认预览、存储尺寸
    private var mPreviewSize = Size(PREVIEW_WIDTH, PREVIEW_HEIGHT)

    private var mImageReader: ImageReader? = null

    private var mBackgroundHandler: Handler? = null

    private val mBackgroundThread = HandlerThread("CameraThread")

    private var mCameraId = CAMERA_ID_BACK

    private var mCameraDevice: CameraDevice? = null

    private var mCameraCaptureSession: CameraCaptureSession? = null

    private var mCreateCaptureRequest: CaptureRequest.Builder? = null

    init {
        mBackgroundThread.start()
        mBackgroundHandler = Handler(mBackgroundThread.looper)
        mTextureView.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {
                this@Camera2Manager.openCamera()
            }

            override fun onSurfaceTextureSizeChanged(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {
            }

            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
                releaseCamera()
                return true
            }

            override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
            }

        }
    }


    fun openCamera() {
        mCameraManager = mActivity.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        setUpCameraOutput(mCameraManager)
        if (!checkPermission()) return
        mCameraManager.openCamera(mCameraId, mDeviceStateCallback, mBackgroundHandler)
    }

    private fun setUpCameraOutput(cameraManager: CameraManager) {
        if (configCameraParams(cameraManager, mCameraId)) return
        for (cameraId in cameraManager.cameraIdList) {
            if (configCameraParams(cameraManager, cameraId)) return
        }
    }

    private fun configCameraParams(cameraManager: CameraManager, cameraId: String): Boolean {
        mCameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId)
        val map = mCameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
            ?: return false
        mCameraSensorOrientation =
            mCameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION) ?: 0
        mPreviewSize = getBestSize(
            mPreviewSize.width, mPreviewSize.height, mTextureView.width, mTextureView.height,
            map.getOutputSizes(SurfaceTexture::class.java).toList()
        ) ?: mPreviewSize
        mImageReader =
            ImageReader.newInstance(
                mPreviewSize.width,
                mPreviewSize.height,
//                ImageFormat.YUV_420_888,
                ImageFormat.JPEG,
                2
            )
        mImageReader?.setOnImageAvailableListener(onImageAvailableListener, mBackgroundHandler)
        //mCameraId = cameraId
        return true
    }

    fun setCameraCallback(eventCallback: ICameraEventCallback) {
        this.mCameraCallback = eventCallback
    }

    private val onImageAvailableListener = ImageReader.OnImageAvailableListener {
        Log.e("RRye", "onImageAvailableListener....")
        //获取图像数据
        val image = it.acquireLatestImage()
        val byteBuffer = image.planes[0].buffer
        val byteArray = ByteArray(byteBuffer.remaining())
        byteBuffer.get(byteArray)
        image.close()//必不可缺！！
        //TODO 保存图片数据， byteArray
        mCameraCallback?.onCaptureImg(createBitmapWithRotate(byteArray))
    }

    private val mDeviceStateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) {
            mCameraDevice = camera
            createCameraPreviewSession(camera)
        }

        override fun onDisconnected(camera: CameraDevice) {
            Log.e(TAG, "onDisconnected....")
        }

        override fun onError(camera: CameraDevice, error: Int) {
            Log.e(TAG, "onError...")
        }

    }

    /**
     * 创建预览会话
     */
    private fun createCameraPreviewSession(cameraDevice: CameraDevice) {
        val texture = mTextureView.surfaceTexture ?: return
        texture.setDefaultBufferSize(mPreviewSize.width, mPreviewSize.height)
        val surface = Surface(mTextureView.surfaceTexture)

        mCreateCaptureRequest = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
        //闪光灯
        mCreateCaptureRequest?.set(
            CaptureRequest.CONTROL_AE_MODE,
            CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH
        )
        //自动对焦
        mCreateCaptureRequest?.set(
            CaptureRequest.CONTROL_AF_MODE,
            CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE
        )
        //设置完captureRequest属性后才能设置target！！
        //给此次请求添加一个Surface对象作为图像的输出目标!!
        mCreateCaptureRequest?.addTarget(surface)
        //---创建CameraCaptureSession对象
        cameraDevice.createCaptureSession(
            arrayListOf(surface, mImageReader?.surface), mCaptureStateCallback,
            mBackgroundHandler
        )

    }

    private val mCaptureStateCallback = object : CameraCaptureSession.StateCallback() {
        override fun onConfigured(session: CameraCaptureSession) {
            if (null == mCameraDevice || null == mCreateCaptureRequest) return
            mCameraCaptureSession = session
            mCameraCaptureSession!!.setRepeatingRequest(
                mCreateCaptureRequest!!.build(),
                mCaptureCallback, mBackgroundHandler
            )
        }

        override fun onConfigureFailed(session: CameraCaptureSession) {
            Log.i(TAG, "onConfigureFailed:$session ")
        }

    }

    private val mCaptureCallback = object : CameraCaptureSession.CaptureCallback() {
        override fun onCaptureCompleted(
            session: CameraCaptureSession,
            request: CaptureRequest,
            result: TotalCaptureResult
        ) {
            super.onCaptureCompleted(session, request, result)
        }

        override fun onCaptureFailed(
            session: CameraCaptureSession,
            request: CaptureRequest,
            failure: CaptureFailure
        ) {
            super.onCaptureFailed(session, request, failure)
            Log.e(TAG, "onCaptureFailed..")
        }
    }

    /**
     * 拍照
     */
    fun capture() {
        if (mCameraDevice == null || !mTextureView.isAvailable) return
        mCameraDevice?.apply {
            val captureRequestBuilder = createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
            captureRequestBuilder.addTarget(mImageReader!!.surface)
            //自动对焦
            captureRequestBuilder.set(
                CaptureRequest.CONTROL_AF_MODE,
                CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE
            )
            captureRequestBuilder.set(
                CaptureRequest.CONTROL_AE_MODE,
                CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH
            )
            //这个方法是否可用，是依赖于底层的，也就是说，底层没有做相应的处理的话，设置之后才有效果，
            // 如果底层没有做相应的处理，是没有作用。（如三星手机/小米mix2是没有做相应的处理的）
            captureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION, mCameraSensorOrientation)
            mCameraCaptureSession?.capture(captureRequestBuilder.build(), null, mBackgroundHandler)
        }
    }

    fun releaseCamera() {
        mCameraCaptureSession?.close()
        mCameraCaptureSession = null

        mCameraDevice?.close()
        mCameraDevice = null

        mImageReader?.close()
        mImageReader = null
    }

    fun switchCamera() {
        toggleCameraId()
        releaseCamera()
        //mBackgroundThread?.quitSafely()
        openCamera()
    }

    fun toggleFlashLamp() {

    }

    private fun toggleCameraId() {
        if (mCameraId == CAMERA_ID_BACK) {
            mCameraId = CAMERA_ID_FRONT
        } else if (mCameraId == CAMERA_ID_FRONT) {
            mCameraId = CAMERA_ID_BACK
        }
    }


    private fun checkPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                mActivity,
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.e(TAG, "没有相机权限！！")
            return false
        }
        return true
    }

    /**
     * 获取图片原始角度，机型不相同
     */
    private fun createBitmapWithRotate(byteArray: ByteArray): Bitmap {
        val degree = ImageUtils.getNaturalOrientation(byteArray)
        Log.e(TAG, "picture naturalOrientation:$degree")
        val origin = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size, null)
        if (degree == 0) return origin
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        return Bitmap.createBitmap(origin, 0, 0, origin.width, origin.height, matrix, true)
    }

    /**
     * 根据提供的屏幕方向和相机方向，决定是否要交换视频预览的宽高；
     * TODO 去掉此方法判断，看一下效果！！！
     */
    private fun exchangeWidthAndHeight(displayRotation: Int, sensorOrientation: Int): Boolean {
        var exchange = false
        when (displayRotation) {
            Surface.ROTATION_0, Surface.ROTATION_180 -> {
                if (sensorOrientation in intArrayOf(90, 270)) {
                    exchange = true
                }
            }
            Surface.ROTATION_90, Surface.ROTATION_270 -> {
                if (sensorOrientation in intArrayOf(0, 180)) {
                    exchange = true
                }
            }
            else -> Log.e(TAG, "Display rotation is invalid:$displayRotation")
        }

        return exchange
    }


    /**
     *
     * 根据提供的参数值返回与指定宽高相等或最接近的尺寸
     *
     * @param targetWidth   目标宽度
     * @param targetHeight  目标高度
     * @param maxWidth      最大宽度(即TextureView的宽度)
     * @param maxHeight     最大高度(即TextureView的高度)
     * @param sizeList      支持的Size列表
     *
     * @return  返回与指定宽高相等或最接近的尺寸
     *
     */
    private fun getBestSize(
        targetWidth: Int,
        targetHeight: Int,
        maxWidth: Int,
        maxHeight: Int,
        sizeList: List<Size>?
    ): Size? {
        val bigEnough = ArrayList<Size>()     //比指定宽高大的Size列表
        val notBigEnough = ArrayList<Size>()  //比指定宽高小的Size列表
        if (sizeList.isNullOrEmpty()) return null
        for (size in sizeList) {

            //宽<=最大宽度  &&  高<=最大高度  &&  宽高比 == 目标值宽高比
            if (size.width <= maxWidth && size.height <= maxHeight
                && size.width == size.height * targetWidth / targetHeight
            ) {

                if (size.width >= targetWidth && size.height >= targetHeight)
                    bigEnough.add(size)
                else
                    notBigEnough.add(size)
            }
            Log.e(
                TAG,
                "系统支持的尺寸: ${size.width} * ${size.height} ,  比例 ：${size.width.toFloat() / size.height}"
            )
        }

        Log.e(TAG, "最大尺寸 ：$maxWidth * $maxHeight, 比例 ：${targetWidth.toFloat() / targetHeight}")
        Log.e(
            TAG,
            "目标尺寸 ：$targetWidth * $targetHeight, 比例 ：${targetWidth.toFloat() / targetHeight}"
        )

        //选择bigEnough中最小的值  或 notBigEnough中最大的值
        return when {
            bigEnough.size > 0 -> Collections.min(bigEnough, CompareSizesByArea())
            notBigEnough.size > 0 -> Collections.max(notBigEnough, CompareSizesByArea())
            else -> sizeList[0]
        }
    }

    private class CompareSizesByArea : Comparator<Size> {
        override fun compare(size1: Size, size2: Size): Int {
            return java.lang.Long.signum(size1.width.toLong() * size1.height - size2.width.toLong() * size2.height)
        }
    }
}

interface ICameraEventCallback {
    fun onOpenError(errorMsg: String) {}
    fun onCaptureImg(bitmap: Bitmap)
}