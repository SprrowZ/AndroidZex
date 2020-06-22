package com.rye.catcher.project.ctmviews.takephoto


import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.media.ImageReader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import android.util.Log
import android.util.Size
import android.util.SparseIntArray
import android.view.*
import com.rye.catcher.BaseOldFragment


import com.rye.base.utils.SDHelper
import kotlinx.android.synthetic.main.fragment_camera_two.*
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.collections.ArrayList
import java.lang.Long.signum


/**
 * A simple [Fragment] subclass.
 *
 */
class CameraTwoFragment : BaseOldFragment() {
    private val ORIENTATIONS=SparseIntArray()

    init {
        ORIENTATIONS.append(Surface.ROTATION_0,90)
        ORIENTATIONS.append(Surface.ROTATION_90,0)
        ORIENTATIONS.append(Surface.ROTATION_180,270)
        ORIENTATIONS.append(Surface.ROTATION_270,180)
    }
    private var mCameraId="0"
    private lateinit var cameraDevice:CameraDevice
    private lateinit var previewSize:Size
    private lateinit var previewRequestBuilder:CaptureRequest.Builder
    //预览照片捕获请求
    private lateinit var previewRequest:CaptureRequest
    private lateinit var captureSession:CameraCaptureSession
    private lateinit var imageReader: ImageReader
    private val mSurfaceTextureListener=object:TextureView.SurfaceTextureListener{


        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onSurfaceTextureAvailable(p0: SurfaceTexture?, width: Int, height: Int) {
            // 当TextureView可用时，打开摄像头
            openCamera(width, height)
        }
        override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture?, p1: Int, p2: Int) {

        }

        override fun onSurfaceTextureUpdated(p0: SurfaceTexture?) {

        }

        override fun onSurfaceTextureDestroyed(p0: SurfaceTexture?): Boolean {
           return true
        }


    }

    private val stateCallback= @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    object: CameraDevice.StateCallback() {
        override fun onOpened(cameraDevice: CameraDevice) {
            this@CameraTwoFragment.cameraDevice=cameraDevice
            // 开始预览
            createCameraPreviewSession()
        }

        override fun onDisconnected(p0: CameraDevice) {
            cameraDevice.close()
        }

        override fun onError(p0: CameraDevice, p1: Int) {
            cameraDevice.close()
            activity?.finish()
        }

    }

    override fun getLayoutResId(): Int {
        return com.rye.catcher.R.layout.fragment_camera_two
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initData() {
        texture.surfaceTextureListener=mSurfaceTextureListener
        takePhoto.setOnClickListener {
            captureStillPicture()
        }
    }
    //拍照
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun captureStillPicture(){
          try{
              if (cameraDevice == null)
              {
                  return
              }

              val captureRequestBuild=cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
              //
              captureRequestBuild.addTarget(imageReader.surface)
              //设置自动对焦
              captureRequestBuild.set(CaptureRequest.CONTROL_AF_MODE,CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE)
              //自动曝光
              captureRequestBuild.set(CaptureRequest.CONTROL_AE_MODE,CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH)
              //获取设备方向
              var rotation=activity?.windowManager?.defaultDisplay?.rotation
              captureRequestBuild.set(CaptureRequest.JPEG_ORIENTATION, rotation?.let { ORIENTATIONS.get(it) })
              //停止连续取景？
              captureSession.stopRepeating()
              captureSession.capture(captureRequestBuild.build(),object: CameraCaptureSession.CaptureCallback() {
                  override fun onCaptureCompleted(session: CameraCaptureSession, request: CaptureRequest, result: TotalCaptureResult) {

                      try {
                          // 重设自动对焦模式
                          previewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER,
                                  CameraMetadata.CONTROL_AF_TRIGGER_CANCEL)
                          // 设置自动曝光模式
                          previewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE,
                                  CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH)
                          // 打开连续取景模式
                          captureSession.setRepeatingRequest(previewRequest, null, null)
                      } catch (e: CameraAccessException) {
                          e.printStackTrace()
                      }

                  }
              },null)
          }catch (e:Exception){

          }
    }

    //进入的时候别忘了申请权限
    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    //打开相机
    private fun openCamera(width:Int, height:Int){
        setUpCameraOutputs(width,height)
        val manager=activity?.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            manager.openCamera(mCameraId,stateCallback,null)
        }catch (e:Exception){

        }
    }
    //
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun createCameraPreviewSession(){
        try {
            val texture=texture.surfaceTexture
            //通过设置缓冲区去设置我们想要预览的大小
            texture.setDefaultBufferSize(previewSize.width,previewSize.height)
            //图像输出到了surface中
            val surface= Surface(texture)
            //设置预览
            previewRequestBuilder=cameraDevice.createCaptureRequest(
                    CameraDevice.TEMPLATE_PREVIEW)
            //?
            previewRequestBuilder.addTarget(Surface(texture))
            //创建CameraCaptureSession,十分重要，该对象负责管理处理预览请求和拍照请求
            cameraDevice.createCaptureSession(Arrays.asList(texture,imageReader.surface) as MutableList<Surface>,object:CameraCaptureSession.StateCallback(){
                override fun onConfigureFailed(p0: CameraCaptureSession) {

                }

                override fun onConfigured(captureSession1: CameraCaptureSession) {
                    // 如果摄像头为null，直接结束方法
                    if (null == cameraDevice)
                    {
                        return
                    }
                    captureSession=captureSession1

                    try{
                        // 设置图像连续
                        previewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE,
                                CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                        // 设置闪光灯模式，在需要的时候打开
                        previewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE,
                                CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH)
                        // 开始显示相机预览
                        previewRequest = previewRequestBuilder.build()
                        // 设置预览时连续捕获图像数据
                        captureSession.setRepeatingRequest(previewRequest,
                                null,null)
                    }catch (e:java.lang.Exception){

                    }
                }

            },null)
        }catch (e:java.lang.Exception){

        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setUpCameraOutputs(width:Int, height:Int){
        val manager=activity?.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
           //获取当前摄像头的特性
            val characteristics=manager.getCameraCharacteristics(mCameraId)
          //获取摄像头支持的配置属性
            val map=characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
            //获取摄像头支持的最大尺寸
            val largest= Collections.max( map?.getOutputSizes(ImageFormat.JPEG) as MutableList<Size>,CompareSizesByArea())
            //创建一个ImageReader对象，用户获取摄像头的图像数据
            imageReader= ImageReader.newInstance(largest.getWidth(),largest.getHeight(),ImageFormat.JPEG,2)
            imageReader.setOnImageAvailableListener(object:ImageReader.OnImageAvailableListener{
                //当照片数据可用时激发该方法
                override fun onImageAvailable(reader: ImageReader?) {
                     val image=reader?.acquireNextImage()
                    val buffer=image!!.planes[0].buffer
                    val bytes= byteArrayOf(buffer.remaining().toByte())
                    //
                    val file=File(SDHelper.getImageFolder(),"pic.jpg")
                    buffer.get(bytes)
                    try {
                        val output=FileOutputStream(file)
                        output.write(bytes)
                        Log.i("camera","图片已写入：${bytes.size}")
                    }catch (e:java.lang.Exception){

                    }finally {
                        image.close()
                    }

                }
            },null)
            previewSize=chooseOptimalSize(map!!.getOutputSizes(SurfaceTexture::class.java),width,height,largest)
             val orientation= activity!!.resources.configuration.orientation
            if (orientation==Configuration.ORIENTATION_LANDSCAPE){

            }else{

            }
        }catch (e:java.lang.Exception){

        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun chooseOptimalSize(choices: Array<Size>, width: Int, height: Int, aspectRatio: Size): Size {
        // 收集摄像头支持的大过预览Surface的分辨率
        val bigEnough = ArrayList<Size>()
        val w = aspectRatio.width
        val h = aspectRatio.height
        for (option in choices) {
            if (option.height == option.width * h / w &&
                    option.width >= width && option.height >= height) {
                bigEnough.add(option)
            }
        }
        // 如果找到多个预览尺寸，获取其中面积最小的
        if (bigEnough.size > 0) {
            return Collections.min(bigEnough, CompareSizesByArea())
        } else {
            println("找不到合适的预览尺寸！！！")
            return choices[0]
        }
    }

}
class CompareSizesByArea : java.util.Comparator<Size> {

    // We cast here to ensure the multiplications won't overflow
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun compare(lhs: Size, rhs: Size) =
            signum(lhs.width.toLong() * lhs.height - rhs.width.toLong() * rhs.height)

}
