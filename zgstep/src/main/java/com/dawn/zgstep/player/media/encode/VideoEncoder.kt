package com.dawn.zgstep.player.media.encode

import android.media.MediaCodec
import android.media.MediaCodecInfo
import android.media.MediaFormat
import android.provider.MediaStore

/**
 * Create by  [Rye]
 *
 * at 2021/12/27 9:07 下午
 * 参考资料：
 * https://codeantenna.com/a/KjGcwYyT0Y
 * https://github.com/zhongjihao/AVMediaCodecMP4
 * https://github.com/xiaole0310/MediaCodec
 * https://unbroken.blog.csdn.net/article/details/107574582?spm=1001.2101.3001.6661.1&utm_medium=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7Edefault-1.no_search_link&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7Edefault-1.no_search_link&utm_relevant_index=1
 *https://blog.csdn.net/weixin_43707799/article/details/107576409
 */
class VideoEncoder {
    private var videoWidth = 100
    private var videoHeight = 100
    private val fps = 30
    private val gop = 1
    private var mMediaCodec: MediaCodec? = null

    companion object {
        private const val MIME = "video/avc"
    }

    private fun initVideoCodec(width: Int, height: Int) {
        mMediaCodec = MediaCodec.createEncoderByType(MIME)
        videoWidth = width
        videoHeight = height
        val format = MediaFormat.createVideoFormat(MIME, videoWidth, videoHeight)
        format.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface)
        format.setInteger(MediaFormat.KEY_BIT_RATE, videoHeight*videoWidth*4)
        format.setInteger(MediaFormat.KEY_FRAME_RATE, fps)
        format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, gop)
        mMediaCodec?.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
        mMediaCodec?.start() //开始编码
    }

    //编码，将摄像头数据传递进来
    fun onFrame(buf: ByteArray, offset: Int, length: Int, flag: Int) {
        val inputBuffers = mMediaCodec?.inputBuffers
        val outputBuffers = mMediaCodec?.outputBuffers
        val inputBufferIndex = mMediaCodec?.dequeueInputBuffer(-1) ?: -1
        if (inputBufferIndex >= 0) {
            val inputBuffer = inputBuffers?.get(inputBufferIndex)
            inputBuffer?.clear()
            inputBuffer?.put(buf, offset, length)
            mMediaCodec?.queueInputBuffer(inputBufferIndex, 0, length, 0, 0)
        }
        val bufferInfo = MediaCodec.BufferInfo()
        var outputBufferIndex = mMediaCodec?.dequeueOutputBuffer(bufferInfo, 0) ?: -1
        while (outputBufferIndex >= 0) {
            val outputBuffer = outputBuffers?.get(outputBufferIndex)
            //TODO 监听
            mMediaCodec?.releaseOutputBuffer(outputBufferIndex, false)
            outputBufferIndex = mMediaCodec?.dequeueOutputBuffer(bufferInfo, 0) ?: 0
        }

    }

}