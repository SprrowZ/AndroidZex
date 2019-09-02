package com.rye.catcher.project.ctmviews.takephoto


import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.graphics.*
import android.hardware.camera2.*
import android.media.ImageReader
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.util.Log
import android.util.Size
import android.util.SparseIntArray
import android.view.Gravity
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rye.base.utils.PopupEx
import com.rye.catcher.BaseFragment
import com.rye.catcher.R
import com.rye.catcher.BaseLazyFragment
import kotlinx.android.synthetic.main.fragment_camera_t.*
import java.lang.Exception
import java.util.*


/**
 * A simple [Fragment] subclass.
 *
 */
class CameraTFragment : BaseFragment() {
    //看来得手动findViewById才行
    private lateinit var textureView:AutoFitTextureView
    private lateinit var takePhoto:ImageView


    private lateinit var  mCameraDevice:CameraDevice

    //摄像头id，找到一个即可
    private   var cameraId=CameraCharacteristics.LENS_FACING_FRONT.toString()

    //默认不支持闪光灯
    private var supportFlash=false

    //拍照运行在子线程中，实际上运行在主线程即可，textureview并不会阻塞当前线程
    private var backgroundThread: HandlerThread? = null
    private var backgroundHandler: Handler? = null
    //ImageReader类允许应用程序直接访问呈现表面的图像数据 ----------------------?
    private var imageReader: ImageReader? = null
    //相机传感器
    private var sensorOrientation = 0
    //预览最大宽度
    private val MAX_PREVIEW_WIDTH = 1920
    //预览最大高度
    private val MAX_PREVIEW_HEIGHT = 1080
    //相机预览尺寸
    private lateinit var previewSize: Size
    /**
     *用于预览
     */
    private lateinit var previewRequestBuilder: CaptureRequest.Builder
    /**
     *  预览请求
     */
    private lateinit var previewRequest: CaptureRequest

    /**
     * 会话
     */
    private var captureSession: CameraCaptureSession? = null
    /**
     * 相机状态：预览
     */
    private val STATE_PREVIEW = 0
    /**
     * 相同状态：准备聚焦
     */
    private val STATE_WAITING_LOCK = 1

    /**
     * Camera state: Waiting for the exposure to be precapture state.
     */
    private val STATE_WAITING_PRECAPTURE = 2

    /**
     * Camera state: Waiting for the exposure state to be something other than precapture.
     */
    private val STATE_WAITING_NON_PRECAPTURE = 3

    /**
     * 相机状态：已经拍照
     */
    private val STATE_PICTURE_TAKEN = 4

    /**
     * T当前相机状态
     */
    private var state = STATE_PREVIEW


    //callback
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private val stateCallback= object : CameraDevice.StateCallback() {
        override fun onOpened(cameraDevice: CameraDevice) {
           mCameraDevice=cameraDevice
            //创建会话
            createCameraPreviewSession()
        }

        override fun onDisconnected(cameraDevice: CameraDevice) {
            cameraDevice.close()
         }

        override fun onError(cameraDevice: CameraDevice, p1: Int) {
            cameraDevice.close()
         }

    }

    /**
     * A [CameraCaptureSession.CaptureCallback] that handles events related to JPEG capture.
     * 一些设备上预览可能报错，可用这个规避
     */
    private val captureCallback = @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    object : CameraCaptureSession.CaptureCallback() {

        private fun process(result: CaptureResult) {
            when (state) {
                STATE_PREVIEW -> Unit //预览正常的时候什么也不做
                STATE_WAITING_LOCK -> capturePicture(result)
                STATE_WAITING_PRECAPTURE -> {
                    // CONTROL_AE_STATE 在一些设备上可能为空
                    val aeState = result.get(CaptureResult.CONTROL_AE_STATE)
                    if (aeState == null ||
                            aeState == CaptureResult.CONTROL_AE_STATE_PRECAPTURE ||
                            aeState == CaptureRequest.CONTROL_AE_STATE_FLASH_REQUIRED) {
                        state = STATE_WAITING_NON_PRECAPTURE
                    }
                }
                STATE_WAITING_NON_PRECAPTURE -> {
                    // CONTROL_AE_STATE 在一些设备上可能为空
                    val aeState = result.get(CaptureResult.CONTROL_AE_STATE)
                    if (aeState == null || aeState != CaptureResult.CONTROL_AE_STATE_PRECAPTURE) {
                        state = STATE_PICTURE_TAKEN
                  //      captureStillPicture()
                    }
                }
            }
        }

        /**
         * 判断是否对好焦，然后去拍照
         */
        private fun capturePicture(result: CaptureResult) {
            val afState = result.get(CaptureResult.CONTROL_AF_STATE)
            if (afState == null) {
               captureStillPicture()
            } else if (afState == CaptureResult.CONTROL_AF_STATE_FOCUSED_LOCKED
                    || afState == CaptureResult.CONTROL_AF_STATE_NOT_FOCUSED_LOCKED) {
                // CONTROL_AE_STATE can be null on some devices
                val aeState = result.get(CaptureResult.CONTROL_AE_STATE)
                if (aeState == null || aeState == CaptureResult.CONTROL_AE_STATE_CONVERGED) {
                    state = STATE_PICTURE_TAKEN
                  captureStillPicture()
                } else {
                   runPrecaptureSequence()
                }
            }
        }

        override fun onCaptureProgressed(session: CameraCaptureSession,
                                         request: CaptureRequest,
                                         partialResult: CaptureResult) {
            process(partialResult)
        }

        override fun onCaptureCompleted(session: CameraCaptureSession,
                                        request: CaptureRequest,
                                        result: TotalCaptureResult) {
            process(result)
        }

    }

    override fun getLayoutResId(): Int {
         return  R.layout.fragment_camera_t
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initData() {
        textureView =view!!.findViewById(R.id.textureView)
        takePhoto=view!!.findViewById(R.id.takePhoto)
        takePhoto.setOnClickListener {
            takePicture()
        }
        reverse.setOnClickListener {
            switchCamera()
        }
    }

    /**
     * 获取摄像头属性
     */
//   private fun getCharacteristics(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//Api 大于21
//            mCameraManager=activity?.getSystemService(Context.CAMERA_SERVICE) as CameraManager
//            try {
//                //获取手机摄像头的id列表
//                val ids=mCameraManager.cameraIdList
//                numberOfCameras=ids.size
//                for (id in ids.withIndex()){
//                    val characteristics=mCameraManager.getCameraCharacteristics(id.value)
//                    val orientation=characteristics.get(CameraCharacteristics.LENS_FACING)
//                    if (orientation==CameraCharacteristics.LENS_FACING_FRONT){//前置摄像头
//                        faceFrontCameraId=id.value.toInt()
//                        faceFrontCameraOrientation=characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION)
//                        frontFlashAvailable= characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)!!
//                        frontCameraCharacteristics=characteristics
//                    }else{//后置摄像头
//                        faceBackCameraId=id.value.toInt()
//                        faceBackCameraOrientation=characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION)
//                        supportFlash= characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)!!
//                        backCameraCharacteristics=characteristics
//                    }
//                }
//
//            }catch (e:Exception){
//
//            }finally {
//                Log.i("cameraInfo","摄像头个数：$numberOfCameras,," +
//                        "前置摄像头信息：id->$faceFrontCameraId,方向->$faceFrontCameraOrientation," +
//                        "后置摄像头信息：id->$faceBackCameraId,方向->$faceBackCameraOrientation," +
//                        "是否支持闪光灯：$supportFlash")
//            }
//
//        }
//
//
//    }

    /**
     * 是否支持Camera2，后期适配可用                =。=
     */
   private  fun supportCamera2(mContext:Context):Boolean{
      if (mContext==null) return false
       //api小于21
       if (Build.VERSION.SDK_INT< Build.VERSION_CODES.LOLLIPOP) return false
       var support=true
       try {
          val manager=mContext.getSystemService(Context.CAMERA_SERVICE) as CameraManager
          val idList= manager.cameraIdList

          if (idList.isEmpty() ){
              support=false
          }else{
               for (id in idList){
                   if (id==null || id.isEmpty()){
                       support=false
                       break
                   }
                   val characteristics=manager.getCameraCharacteristics(id)
                   val supportLevel=characteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)
                   if (supportLevel==CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY){//只支持camera1
                       support=false
                       break
                   }

               }
          }
       }catch (e:Exception){

       }finally {

       }
       return support
    }

    /**
     * 初始化预览后台handler
     */
    private fun startBackgroundThread(){
        backgroundThread=HandlerThread("CameraBk").also { it.start() }
        backgroundHandler=Handler(backgroundThread?.looper)
    }
    /**
     * 打开相机预览
     */
   @SuppressLint("MissingPermission")//一定要记得申请权限
   @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
   private  fun openCamera(width: Int, height: Int){
        //设置camera输出
        setUpCameraOutputs(width,height)
        //设置camera的显示范围
        configureTransform(width,height)
        val manager = activity?.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        Log.i("cameraId",cameraId)
        try {
             manager.openCamera(cameraId,stateCallback,backgroundHandler)
        }catch (e:Exception){

        }
   }
   @SuppressLint("MissingPermission")
   @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
   private fun switchCamera( ){
          closeCamera()
       cameraId = if (cameraId == "0"){
           "1"
       }else{
           "0"
       }
       openCamera(textureView.width,textureView.height)
   }

    override fun onResume() {
        super.onResume()
        startBackgroundThread()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if(isVisibleToUser){
            createPop()
        }
    }


    /**
     * 设置相机预览的相关属性，主要是选择合适的尺寸，此外防止预览尺寸超过部分手机
     * 的最大带宽导致崩溃
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setUpCameraOutputs(width: Int, height: Int) {
        val manager = activity!!.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            for (camId in manager.cameraIdList) {
                Log.i("cameraId",camId)
                val characteristics = manager.getCameraCharacteristics(camId)

                // We don't use a front facing camera in this sample.
                val cameraDirection = characteristics.get(CameraCharacteristics.LENS_FACING)
//                if (cameraDirection != null &&
//                        cameraDirection == CameraCharacteristics.LENS_FACING_FRONT) {
//                    continue
//                }
                  if (cameraDirection!=null && camId!=cameraId){
                      continue
                  }
                val map = characteristics.get(
                        CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP) ?: continue

                // 抓取静态图片，使用最大可用尺寸
                val largest = Collections.max(
                        Arrays.asList(*map.getOutputSizes(ImageFormat.JPEG)),
                        CompareSizesByArea())
                imageReader = ImageReader.newInstance(largest.width, largest.height,
                        ImageFormat.JPEG, /*maxImages*/ 2).apply {
                    setOnImageAvailableListener(onImageAvailableListener, backgroundHandler)
                }

                // 找出是否需要交换尺寸以获得相对于传感器坐标的预览尺寸
                val displayRotation = activity!!.windowManager.defaultDisplay.rotation
                sensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION)!!
                val swappedDimensions = areDimensionsSwapped(displayRotation)

                val displaySize = Point()
                activity!!.windowManager.defaultDisplay.getSize(displaySize)
                val rotatedPreviewWidth = if (swappedDimensions) height else width
                val rotatedPreviewHeight = if (swappedDimensions) width else height
                var maxPreviewWidth = if (swappedDimensions) displaySize.y else displaySize.x
                var maxPreviewHeight = if (swappedDimensions) displaySize.x else displaySize.y

                if (maxPreviewWidth > MAX_PREVIEW_WIDTH) maxPreviewWidth = MAX_PREVIEW_WIDTH
                if (maxPreviewHeight > MAX_PREVIEW_HEIGHT) maxPreviewHeight = MAX_PREVIEW_HEIGHT

                //最大预览预览尺寸可能超过手机相机的带宽，所以必须找到合适的，不一定是最大的
                previewSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture::class.java),
                        rotatedPreviewWidth, rotatedPreviewHeight,
                        maxPreviewWidth, maxPreviewHeight,
                        largest)

                //设置预览尺寸
                if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    textureView.setAspectRatio(previewSize.width, previewSize.height)
                } else {
                    textureView.setAspectRatio(previewSize.height, previewSize.width)
                }
                // 查看是否支持闪光灯
                supportFlash = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true
                //找到一个可用的摄像头就返回----
                return
            }
        } catch (e: CameraAccessException) {
            Log.e("camera", e.toString())
        } catch (e: NullPointerException) {

        }

    }

    /**
     * 当图像准备保存时，调用这个方法，需要加个log看一下
     */
    private val onImageAvailableListener = ImageReader.OnImageAvailableListener {
//        backgroundHandler?.post(ImageSaver(it.acquireNextImage(), file))
        val image= it.acquireNextImage()
        Log.i("camera","imageInfo:imageFormat:${image.format},imageHeight:${image.height}," +
                "imageWidth:${image.width}")
    }

    /**
     * 确定在手机当前旋转的情况下是否交换尺寸。
     */
    private fun areDimensionsSwapped(displayRotation: Int): Boolean {
        var swappedDimensions = false
        when (displayRotation) {
            Surface.ROTATION_0, Surface.ROTATION_180 -> {
                if (sensorOrientation == 90 || sensorOrientation == 270) {
                    swappedDimensions = true
                }
            }
            Surface.ROTATION_90, Surface.ROTATION_270 -> {
                if (sensorOrientation == 0 || sensorOrientation == 180) {
                    swappedDimensions = true
                }
            }
            else -> {
                Log.e("camera", "Display rotation is invalid: $displayRotation")
            }
        }
        return swappedDimensions
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun createCameraPreviewSession(){
        try {
            val texture=textureView.surfaceTexture
            //通过设置缓冲区去设置我们想要预览的大小
            texture.setDefaultBufferSize(previewSize.width,previewSize.height)
            //图像输出到了surface中
            val surface= Surface(texture)
            //设置预览
            previewRequestBuilder=mCameraDevice.createCaptureRequest(
                    CameraDevice.TEMPLATE_PREVIEW)
            //指定新创建的surface为本次request的输出目标，说明什么？
            //说明每次构建新请求的时候，也要创建对应的surface！
            previewRequestBuilder.addTarget(surface)
            //创建CameraCaptureSession,十分重要，该对象负责管理处理预览请求和拍照请求
            mCameraDevice.createCaptureSession(Arrays.asList(surface,imageReader?.surface) ,
                    object:CameraCaptureSession.StateCallback(){
                override fun onConfigureFailed(p0: CameraCaptureSession) {

                }

                override fun onConfigured(captureSession1: CameraCaptureSession) {
                    // 如果摄像头为null，直接结束方法
                    if (null == mCameraDevice)
                    {
                        return
                    }
                    captureSession=captureSession1
                    try{
                        // 设置图像连续
                        previewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE,
                                CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE)
                        // 设置闪光灯模式，在需要的时候打开
                        previewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE,
                                CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH)
                        // 开始显示相机预览
                        previewRequest = previewRequestBuilder.build()
                        // 设置预览时连续捕获图像数据
                        captureSession?.setRepeatingRequest(previewRequest,
                                null,backgroundHandler)
                    }catch (e:Exception){
                      Log.e("camera","preview error...")
                    }
                }

            },null)
        }catch (e:java.lang.Exception){
            Log.e("camera","preview error...")
        }
    }


    /**
     * 设置camera显示的范围
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun configureTransform(viewWidth: Int, viewHeight: Int) {
        activity ?: return
        val rotation = activity!!.windowManager.defaultDisplay.rotation
        val matrix = Matrix()
        val viewRect = RectF(0f, 0f, viewWidth.toFloat(), viewHeight.toFloat())
        val bufferRect = RectF(0f, 0f, previewSize.height.toFloat(), previewSize.width.toFloat())
        val centerX = viewRect.centerX()
        val centerY = viewRect.centerY()

        if (Surface.ROTATION_90 == rotation || Surface.ROTATION_270 == rotation) {
            bufferRect.offset(centerX - bufferRect.centerX(), centerY - bufferRect.centerY())
            val scale = Math.max(
                    viewHeight.toFloat() / previewSize.height,
                    viewWidth.toFloat() / previewSize.width)
            with(matrix) {
                setRectToRect(viewRect, bufferRect, Matrix.ScaleToFit.FILL)
                postScale(scale, scale, centerX, centerY)
                postRotate((90 * (rotation - 2)).toFloat(), centerX, centerY)
            }
        } else if (Surface.ROTATION_180 == rotation) {
            matrix.postRotate(180f, centerX, centerY)
        }
        textureView.setTransform(matrix)
    }

    /**
     * 锁定焦点后才能拍照
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun lockFocus(){
        try {
            //拍照前设置为自动对焦
            previewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER,
            CameraMetadata.CONTROL_AF_TRIGGER_START)
            //状态设置准备聚焦拍照，captureCallback需要
            state=STATE_WAITING_LOCK
            captureSession?.capture(previewRequestBuilder.build(),captureCallback,backgroundHandler)
        }catch(e:Exception){

        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun captureStillPicture() {
        try {
            if (activity == null || mCameraDevice == null) return
            val rotation = activity!!.let {
                it.windowManager.defaultDisplay.rotation
            }

            // 这个CaptureRequest.Builder 用来拍照
            val captureBuilder = mCameraDevice?.createCaptureRequest(
                    CameraDevice.TEMPLATE_STILL_CAPTURE)?.apply {
                addTarget(imageReader?.surface!!)
                //设置拍照图片方向，方向出问题，来这设置
                set(CaptureRequest.JPEG_ORIENTATION,
                        (ORIENTATIONS.get(rotation) + sensorOrientation + 270) % 360)

                // 设置和预览时一样的AF,AE模式
                set(CaptureRequest.CONTROL_AF_MODE,
                        CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE)
            }?.also { setAutoFlash(it) }

            val captureCallback = object : CameraCaptureSession.CaptureCallback() {
                //拍照完成回调此方法
                override fun onCaptureCompleted(session: CameraCaptureSession,
                                                request: CaptureRequest,
                                                result: TotalCaptureResult) {

                    Log.d("camera","拍照完成...")
                    unlockFocus()
                }
            }

            captureSession?.apply {
                stopRepeating()//停止预览
                abortCaptures()//
                capture(captureBuilder?.build(), captureCallback, null)//拍照
            }
        } catch (e: CameraAccessException) {
            Log.e("camera", e.toString())
        }

    }

    /**
     * 闪光灯---自动模式
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setAutoFlash(requestBuilder: CaptureRequest.Builder) {
        if (supportFlash) {
            requestBuilder.set(CaptureRequest.CONTROL_AE_MODE,
                    CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH)
        }
    }

    /**
     * 拍完后调用
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun unlockFocus() {
        try {
            // Reset the auto-focus trigger
            previewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER,
                    CameraMetadata.CONTROL_AF_TRIGGER_CANCEL)
            setAutoFlash(previewRequestBuilder)
            captureSession?.capture(previewRequestBuilder.build(), captureCallback,
                    backgroundHandler)
            // 将相机状态置为预览
            state = STATE_PREVIEW
            captureSession?.setRepeatingRequest(previewRequest, captureCallback,
                    backgroundHandler)
        } catch (e: CameraAccessException) {
            Log.e("camera", e.toString())
        }

    }

    /**
     *运行预捕获序列以捕获静止图像----------------------????
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun runPrecaptureSequence() {
        try {
            // 告诉相机要触发的方法
            previewRequestBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER,
                    CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER_START)
            // Tell #captureCallback to wait for the precapture sequence to be set.
            state = STATE_WAITING_PRECAPTURE
            captureSession?.capture(previewRequestBuilder.build(), captureCallback,
                    backgroundHandler)
        } catch (e: CameraAccessException) {
            Log.e("camera", e.toString())
        }

    }

    /**
     * pause的时候关闭相机
     */
    private fun closeCamera() {
        if (::mCameraDevice.isInitialized){
            try {
                //cameraOpenCloseLock.acquire()
                captureSession?.close()
                captureSession = null
                mCameraDevice?.close()

                imageReader?.close()
                imageReader = null
            } catch (e: InterruptedException) {
                throw RuntimeException("Interrupted while trying to lock camera closing.", e)
            } finally {
                //   cameraOpenCloseLock.release()
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun takePicture() {
        lockFocus()
    }

    /**
     * Stops the background thread and its [Handler].
     */
    private fun stopBackgroundThread() {
        backgroundThread?.quitSafely()
        try {
            backgroundThread?.join()
            backgroundThread = null
            backgroundHandler = null
        } catch (e: InterruptedException) {
            Log.e("camera", e.toString())
        }

    }
    override fun onPause() {
        super.onPause()
        closeCamera()
        stopBackgroundThread()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun createPop(){
     val popupEx=PopupEx.Builder()
             .setContextView(activity,R.layout.popup_camera)
             .setDim(0.8f)
             .setParentView(textureView)
             .outCancel(false)
             .create()
        popupEx.view.findViewById<TextView>(R.id.openCamera2)
                .setOnClickListener {
                    //开启相机预览
                    if (textureView.isAvailable){
                        openCamera(textureView.width,textureView.height)
                    }else{
                        Log.i("camera","textureView is not available...")
                    }
            popupEx.dismiss()
            bottom.visibility= View.VISIBLE
        }
    }

    companion object{
        //图片方向设置
        private val ORIENTATIONS = SparseIntArray()
        private val FRAGMENT_DIALOG = "dialog"

        init {
            ORIENTATIONS.append(Surface.ROTATION_0, 90)
            ORIENTATIONS.append(Surface.ROTATION_90, 0)
            ORIENTATIONS.append(Surface.ROTATION_180, 270)
            ORIENTATIONS.append(Surface.ROTATION_270, 180)
        }
        /**
         * 选择合适的尺寸
         */
       @JvmStatic @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        private fun chooseOptimalSize(
                choices: Array<Size>,
                textureViewWidth: Int,
                textureViewHeight: Int,
                maxWidth: Int,
                maxHeight: Int,
                aspectRatio: Size
        ): Size {

            // Collect the supported resolutions that are at least as big as the preview Surface
            val bigEnough = ArrayList<Size>()
            // Collect the supported resolutions that are smaller than the preview Surface
            val notBigEnough = ArrayList<Size>()
            val w = aspectRatio.width
            val h = aspectRatio.height
            for (option in choices) {
                if (option.width <= maxWidth && option.height <= maxHeight &&
                        option.height == option.width * h / w) {
                    if (option.width >= textureViewWidth && option.height >= textureViewHeight) {
                        bigEnough.add(option)
                    } else {
                        notBigEnough.add(option)
                    }
                }
            }

            // 选择尺寸足够(比xml里设置的大小要大)的情况下，最小值
            if (bigEnough.size > 0) {
                return Collections.min(bigEnough, CompareSizesByArea())
            } else if (notBigEnough.size > 0) {
                return Collections.max(notBigEnough, CompareSizesByArea())
            } else {
                Log.e("camera", "Couldn't find any suitable preview size")
                return choices[0]
            }
        }
    }

}
