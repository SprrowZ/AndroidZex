package com.rye.catcher.project.media.record

import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import android.view.Surface
import android.view.SurfaceHolder
import com.rye.base.utils.SDHelper
import java.io.File

/**
 * Create by  [Rye]
 *
 * at 2021/11/20 3:27 下午
 */
class MediaRecorderManager {
    private var mMediaRecorder: MediaRecorder? = null

    companion object {
        private const val TAG = "MediaRecorderManager"
        private const val AUDIO_FILE_NAME = "demoAudio.amr"
        private const val VIDEO_FILE_NAME = "demoVideo.mp4"
        private const val RECORD_VIDEO_WIDTH = 720
        private const val RECORD_VIDEO_HEIGHT = 1280
        fun create(): MediaRecorderManager {
            return MediaRecorderManager()
        }
    }

    /**
     * 录制音频
     */
    fun recordAudio() {
        initMediaRecorder()
        mMediaRecorder?.apply {
            //声音来源
            setAudioSource(MediaRecorder.AudioSource.MIC)
            //声音输出格式
            setOutputFormat(MediaRecorder.OutputFormat.AMR_NB)
            //声音编码格式
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            val audioFile = File(SDHelper.external + AUDIO_FILE_NAME)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                setOutputFile(audioFile)
                Log.e(TAG, "audioFile:${audioFile.absoluteFile}")
            }
        }
        mMediaRecorder?.prepare()
        mMediaRecorder?.start()
        Log.e(TAG, "mediaRecorder start->>")
    }

    fun stopRecord() {
        mMediaRecorder?.stop()
        mMediaRecorder?.release()
        mMediaRecorder = null
        Log.e(TAG, "stopRecord <<-")
    }

    fun getAudioPath(): String? {
        val file = File(SDHelper.external + AUDIO_FILE_NAME)
        if (!file.exists()) return null
        return file.absolutePath
    }

    fun getVideoPath(): String? {
        val file = File(SDHelper.external + VIDEO_FILE_NAME)
        if (!file.exists()) return null
        return file.absolutePath
    }

    /**
     * 录制视频
     */
    fun recordVideo(holder: SurfaceHolder) {
        holder.removeCallback(surfaceCallback)
        holder.addCallback(surfaceCallback)
    }

    fun removeVideoSurfaceCallback(holder: SurfaceHolder?) {
        holder?.removeCallback(surfaceCallback)
    }

    private fun realRecordVideo(surface: Surface) {
        val videoFile = File(SDHelper.external + VIDEO_FILE_NAME)
        initMediaRecorder()
        mMediaRecorder?.apply {
            reset()
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setVideoSource(MediaRecorder.VideoSource.CAMERA)
            //设置视频文件的输出格式，比如在设置声音编码格式、图像编码格式之前设置
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setVideoEncoder(MediaRecorder.VideoEncoder.H264)
            setVideoSize(RECORD_VIDEO_HEIGHT, RECORD_VIDEO_WIDTH)
            //每秒4帧
            setVideoFrameRate(20)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                setOutputFile(videoFile.absoluteFile)
            }
            setPreviewDisplay(surface)
        }
        mMediaRecorder?.prepare()
        //开始录制
        mMediaRecorder?.start()
    }
   private val surfaceCallback = object :SurfaceHolder.Callback {
        override fun surfaceCreated(holder: SurfaceHolder) {
            realRecordVideo(holder.surface)
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {

        }

    }

    private fun initMediaRecorder() {
        if (mMediaRecorder == null) {
            mMediaRecorder = MediaRecorder()
        }
    }
}