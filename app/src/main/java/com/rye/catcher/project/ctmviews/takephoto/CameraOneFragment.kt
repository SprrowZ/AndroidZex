package com.rye.catcher.project.ctmviews.takephoto


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.text.TextUtils
import android.view.View
import com.rye.catcher.R
import com.rye.catcher.activity.fragment.BaseFragment
import com.rye.catcher.utils.ImageUtils
import com.rye.catcher.utils.SDHelper
import kotlinx.android.synthetic.main.fragment_camera_one.*
import java.io.File
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.*


class CameraOneFragment : BaseFragment() {

    private val CAMERA_REQUEST_CODE = 99

    private var uri: Uri? = null


    override fun getLayoutResId(): Int {
         return R.layout.fragment_camera_one
    }

    override fun initData() {
        takePhoto.setOnClickListener{
            takePhoto()
        }
        takeCard.setOnClickListener{
            takeCard()
        }






     }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CameraActivity2.REQUEST_CODE && resultCode == CameraActivity2.RESULT_CODE) {
            //获取文件路径，显示图片
            val path = CameraActivity2.getResult(data)
            if (!TextUtils.isEmpty(path)) {
                main_image.setImageBitmap(BitmapFactory.decodeFile(path))
            }
        } else if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (uri != null) {
                var bitmap: Bitmap? = null
                try {
                    val inputStream = activity!!.getContentResolver().openInputStream(uri!!)
                    //压缩图片
                    bitmap = ImageUtils.ratio(null, main_image.getMeasuredHeight(), main_image.getMeasuredWidth(),
                            inputStream)
                    if (data != null) {
                        main_image.setImageBitmap(bitmap)
                    }

                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                } finally {
                    if (bitmap != null && !bitmap.isRecycled) {
                        bitmap.recycle()
                    }
                }

            }


        }
    }

    /**
     * 拍摄身份证
     *
     * @param type 拍摄证件类型
     */
    private fun takePhoto(type: Int) {
        if (activity?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.CAMERA) } != PackageManager.PERMISSION_GRANTED) {
            activity?.let { ActivityCompat.requestPermissions(it, arrayOf(Manifest.permission.CAMERA), 0x12) }
            return
        }
        CameraActivity2.openCamera(activity, type)
    }
    private fun getImagePath(): String {
        val data = Date()
        val sdf = SimpleDateFormat("yy-MM-DD%HH:mm:ss")
        val dataStr = sdf.format(data)
        return SDHelper.getImageFolder() + dataStr + ".png"
    }

    /**
     * 身份证
     */
    fun takePhoto() {
        //拍照页面
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file = File(getImagePath())

        //低于7.0用file://
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.fromFile(file)
        } else {
            uri = activity?.let { FileProvider.getUriForFile(it, activity!!.getApplicationContext().getPackageName() + ".provider", file) }
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)

    }


    /**
     * 证件
     */
    fun takeCard( ) {
        takePhoto(CameraActivity2.TYPE_CARD)
    }

    /**
     * 发票
     */
    fun takeInvoice( ) {
        takePhoto(CameraActivity2.TYPE_INVOICE)
    }

    /**
     * 驾照
     */
    fun takeLicense( ) {
        takePhoto(CameraActivity2.TYPE_LICENSE)
    }

}
