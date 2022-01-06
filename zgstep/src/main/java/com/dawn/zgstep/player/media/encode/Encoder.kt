package com.dawn.zgstep.player.media.encode

import android.media.MediaCodec
import android.media.MediaCodecInfo
import android.media.MediaFormat
import android.os.Environment
import android.provider.MediaStore
import com.rye.base.utils.SDHelper
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.LinkedBlockingQueue

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
class Encoder : EncodeCallback {
    private var videoWidth = 100
    private var videoHeight = 100
    private val fps = 30
    private val gop = 1
    var mMediaCodec: MediaCodec? = null
    var mMediaFormat: MediaFormat? = null
    var mBufferInfo: MediaCodec.BufferInfo? = null
    var mOutputStream: BufferedOutputStream? = null

    //待编码数据
    var videoQueue: LinkedBlockingQueue<ByteArray>? = null

    //是否已在视频编码
    var videoEncoderLoop = false

    //视频是否编码结束
    var isVideoEncodeEnd = false

    //语音是否编码结束
    private var isAudioEncodeEnd = false

    private var mVideoEncodeThread: Thread? = null
    private val H264_FILE_PATH = SDHelper.storagePath + "test1.h264"

    companion object {
        private const val MIME = "video/avc"

    }

    private fun initVideoCodec(width: Int, height: Int) {
        mMediaCodec = MediaCodec.createEncoderByType(MIME)
        videoWidth = width
        videoHeight = height

        videoQueue = LinkedBlockingQueue()
        mBufferInfo = MediaCodec.BufferInfo()


        mMediaFormat = MediaFormat.createVideoFormat(MIME, videoWidth, videoHeight)
        mMediaFormat?.apply {
            setInteger(
                MediaFormat.KEY_COLOR_FORMAT,
                MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface
            )
            setInteger(MediaFormat.KEY_BIT_RATE, videoHeight * videoWidth * 4)
            setInteger(MediaFormat.KEY_FRAME_RATE, fps)
            setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, gop)
        }
        mVideoEncodeThread = MediaEncodeThread(this)
        createSavedH264File()
    }


    fun startVideoEncode() {
        videoEncoderLoop = true
        mVideoEncodeThread?.start()
    }

    private fun releaseEncoder() {
        mMediaCodec?.stop()
        mMediaCodec?.release()
        mMediaCodec = null
    }

    fun stop() {
        stopAudioEncode()
        stopVideoEncode()
    }

    private fun createSavedH264File() {
        val file = File(H264_FILE_PATH)
        if (file.exists()) {
            file.delete()
        }
        mOutputStream = BufferedOutputStream(FileOutputStream(file))
    }

    private fun stopAudioEncode() {
        isAudioEncodeEnd = true
    }

    private fun stopVideoEncode() {
        isVideoEncodeEnd = true
    }

    override fun videoEncodeFinish() {
        releaseEncoder()
        videoQueue?.clear()
    }
}

class MediaEncodeThread(private val mEncoder: Encoder) : Thread("encode") {
    private var presentationTimeUs = 0L

    companion object {
        private const val TIMEOUT_US = 10000L//超时时间10s
    }

    override fun run() {
        presentationTimeUs = System.currentTimeMillis() * 1000
        mEncoder.mMediaCodec?.configure(
            mEncoder.mMediaFormat,
            null,
            null,
            MediaCodec.CONFIGURE_FLAG_ENCODE
        )
        mEncoder.mMediaCodec?.start() //开始编码
        while (mEncoder.isVideoEncodeEnd && !interrupted()) {
            try {
                val data = mEncoder.videoQueue?.take()
                encodeVideoData(data)
            } catch (e: InterruptedException) {
                e.printStackTrace()
                break
            }
        }
        //编码完释放编码器
        mEncoder.videoEncodeFinish()
    }

    private fun encodeVideoData(input: ByteArray?) {
        //拿到缓冲区
        val inputBuffers = mEncoder.mMediaCodec?.inputBuffers
        //得到有效的输入缓冲区的索引
        val inputBufferIndex = mEncoder.mMediaCodec?.dequeueInputBuffer(TIMEOUT_US) ?: -1
        if (inputBufferIndex >= 0) {//输入缓冲区有效
            val inputBuffer = inputBuffers?.get(inputBufferIndex)
            inputBuffer?.clear()
            //往输入缓冲区写入数据
            inputBuffer?.put(input)
            val pts = System.currentTimeMillis() * 1000 - presentationTimeUs
            if (mEncoder.isVideoEncodeEnd) { //结束时发送结束标志，编码完成后结束 TODO 可能有坑
                mEncoder.mMediaCodec?.queueInputBuffer(
                    inputBufferIndex, 0, input?.size ?: 0, pts,
                    MediaCodec.BUFFER_FLAG_END_OF_STREAM
                )
            } else {
                mEncoder.mMediaCodec?.queueInputBuffer(
                    inputBufferIndex, 0, input?.size ?: 0, pts,
                    0
                )
            }
        }
        saveEncodeDataToLocal()

    }

    /**
     * 拿到编码后的数据，保存到本地
     */
    private fun saveEncodeDataToLocal() {
        var outputBuffers = mEncoder.mMediaCodec?.outputBuffers
        var outputBufferIndex =
            mEncoder.mMediaCodec?.dequeueOutputBuffer(mEncoder.mBufferInfo!!, TIMEOUT_US) ?: 0

        while (outputBufferIndex >= 0) {
            val outputBuffer = outputBuffers?.get(outputBufferIndex)
            val outData = byteArrayOf()
            outputBuffer?.get(outData)
            when (mEncoder.mBufferInfo?.flags) {
                MediaCodec.BUFFER_FLAG_CODEC_CONFIG -> {
                    mEncoder.mOutputStream?.write(outData, 0, outData.size)
                }
                MediaCodec.BUFFER_FLAG_KEY_FRAME -> {//关键帧I帧
                    mEncoder.mOutputStream?.write(outData, 0, outData.size)
                }
                else -> {
                    // Non key frame and SPS, PPS, direct write file, may be B frame or P frame
                    mEncoder.mOutputStream?.write(outData, 0, outData.size)
                }
            }
            mEncoder.mMediaCodec?.releaseOutputBuffer(outputBufferIndex, false)
            outputBufferIndex = mEncoder.mMediaCodec?.dequeueOutputBuffer(
                mEncoder.mBufferInfo!!,
                TIMEOUT_US
            ) ?: -1
        }

    }

}

interface EncodeCallback {
    fun videoEncodeFinish()
}