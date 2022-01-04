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
 *
 */
class VideoEncoder {
    private val videoWidth = 100
    private val videoHeight = 100
    private val fps = -1
    private val gop = -1
    private var mMediaCodec:MediaCodec?=null
    companion object{
        private const val MIME = "video/avc"
    }

    fun encode(){
        mMediaCodec = MediaCodec.createEncoderByType(MIME)
        val format  = MediaFormat.createVideoFormat(MIME,videoWidth,videoHeight)
        format.setInteger(MediaFormat.KEY_COLOR_FORMAT,MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface)
        format.setInteger(MediaFormat.KEY_BIT_RATE,2*1024*1024)
        format.setInteger(MediaFormat.KEY_FRAME_RATE,fps)
        format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL,gop)
        mMediaCodec?.configure(format,null,null,MediaCodec.CONFIGURE_FLAG_ENCODE)
        mMediaCodec?.start() //开始编码
    }
    fun onFrame(buf:ByteArray,offset:Int,length:Int,flag:Int){
        val inputBuffers = mMediaCodec?.inputBuffers
        val outputBuffers = mMediaCodec?.outputBuffers
        val inputBufferIndex = mMediaCodec?.dequeueInputBuffer(-1) ?:0
        if (inputBufferIndex >=0){
            val inputBuffer = inputBuffers?.get(inputBufferIndex)
            inputBuffer?.clear()
            inputBuffer?.put(buf,offset,length)
            mMediaCodec?.queueInputBuffer(inputBufferIndex,0,length,0,0)
        }

    }

}