package com.rye.catcher.project.media


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import android.text.TextUtils
import android.widget.ImageView
import android.widget.TextView
import com.rye.base.BaseFragment

import com.rye.catcher.utils.ImageUtils
import com.rye.base.utils.SDHelper
import com.rye.catcher.R

import java.io.File
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.*

/**
 * 调用系统相机
 */
class CameraEntranceFragment : BaseFragment() {

    companion object {
        private const val CAMERA_REQUEST_CODE = 99
        private const val FILE_PROVIDER_PATH = "com.rye.catcher.zzg.provider"
    }

    private var uri: Uri? = null

    private var mTakePhotoView: TextView? = null
    private var mTakeCard: TextView? = null
    private var mMainImgView: ImageView? = null


    override fun getLayoutId(): Int {
        return R.layout.fragment_camera_one
    }

    override fun initWidget() {
        super.initWidget()
        mTakePhotoView = view?.findViewById(R.id.takePhoto)
        mTakeCard = view?.findViewById(R.id.takeCard)
        mMainImgView = view?.findViewById(R.id.main_image)
    }

    override fun initEvent() {
        super.initEvent()
        mTakePhotoView?.setOnClickListener {
            takePhoto()
        }
        mTakeCard?.setOnClickListener {
            takeCard()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Camera1Activity.REQUEST_CODE && resultCode == Camera1Activity.RESULT_CODE) {
            //获取文件路径，显示图片
            val path = Camera1Activity.getResult(data)
            if (!TextUtils.isEmpty(path)) {
                mMainImgView?.setImageBitmap(BitmapFactory.decodeFile(path))
            }
        } else if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            showImage(data)
        }
    }

    private fun showImage(data: Intent?) {
        if (uri != null) {
            var bitmap: Bitmap? = null
            try {
                val inputStream = activity!!.contentResolver.openInputStream(uri!!)
                //压缩图片
                bitmap = mMainImgView?.measuredHeight?.let {
                    mMainImgView?.measuredWidth?.let { it1 ->
                        ImageUtils.ratio(
                            null, it, it1,
                            inputStream
                        )
                    }
                }
                if (data != null) {
                    mMainImgView?.setImageBitmap(bitmap)
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

    /**
     * 拍摄身份证
     *
     * @param type 拍摄证件类型
     */
    private fun takePhoto(type: Int) {
        if (activity?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.CAMERA
                )
            } != PackageManager.PERMISSION_GRANTED) {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.CAMERA),
                    0x12
                )
            }
            return
        }
        Camera1Activity.openCamera(activity, type)
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
        uri = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Uri.fromFile(file)
        } else {
            activity?.let { FileProvider.getUriForFile(it, FILE_PROVIDER_PATH, file) }
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)

    }


    /**
     * 证件
     */
    private fun takeCard() {
        takePhoto(2)
    }


}
