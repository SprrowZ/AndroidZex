package com.rye.base.utils

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
import com.rye.base.BaseApplication

object MediaManager {
    //https://cloud.tencent.com/developer/article/1489208
    //https://blog.csdn.net/weixin_41177814/article/details/102695717
    private val mContentResolver: ContentResolver
        get() {
            return BaseApplication.getInstance().applicationContext.contentResolver
        }

    fun getVideoFiles(): List<MediaVideo>? {
        val videoList = mutableListOf<MediaVideo>()
        val cursor = mContentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null,
            null, MediaStore.Video.Media.DEFAULT_SORT_ORDER
        )
        cursor ?: return null
        while (cursor.moveToNext()) {
            //获取文件路径
            val video = MediaVideo()
            val videoPath =
                cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))
            if (!FileUtils.isFileExist(videoPath)) continue;
            video.apply {
                path = videoPath
                id = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media._ID))
                name =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME))
                //分辨率
                resolution =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.RESOLUTION))
                size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE))
                duration =
                    cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION))
                date =
                    cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED))
            }
            videoList.add(video)
        }
        cursor.close()
        return videoList
    }

    /**
     * 获取视频缩略图
     */
    fun getVideoThumbnail(id:Long):Bitmap {
        val options = BitmapFactory.Options()
        options.inDither = false
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        return MediaStore.Video.Thumbnails.getThumbnail(mContentResolver,id,MediaStore.Images.Thumbnails.MICRO_KIND,options)
    }
}

class MediaVideo {
    var id: Long? = null
    var path: String? = null
    var name: String? = null
    var resolution: String? = null
    var size: Long? = null
    var duration: Long? = null
    var date: Long? = null
}

class MediaMusic {

}