package com.dawn.zgstep.ui.player

import android.annotation.TargetApi
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.view.Surface
import android.view.SurfaceHolder
import java.io.FileDescriptor
import java.io.IOException

/**
 * Create by rye
 * at 2020/12/28
 * @description:
 */
interface IMediaPlayer {
    companion object {
        fun create(): IMediaPlayer {
            //目前只有系统播放器
            return AndroidMediaPlayer()
        }
    }

    fun setDisplay(var1: SurfaceHolder?)

    @Throws(IOException::class, IllegalArgumentException::class, SecurityException::class, IllegalStateException::class)
    fun setDataSource(var1: Context?, var2: Uri?)

    @TargetApi(14)
    @Throws(IOException::class, IllegalArgumentException::class, SecurityException::class, IllegalStateException::class)
    fun setDataSource(var1: Context?, var2: Uri?, var3: Map<String?, String?>?)

    @Throws(IOException::class, IllegalArgumentException::class, IllegalStateException::class)
    fun setDataSource(var1: FileDescriptor?)

    @Throws(IOException::class, IllegalArgumentException::class, SecurityException::class, IllegalStateException::class)
    fun setDataSource(url: String?)

    fun getDataSource(): String?

    @Throws(IllegalStateException::class)
    fun prepareAsync()

    @Throws(IllegalStateException::class)
    fun start()

    @Throws(IllegalStateException::class)
    fun stop()

    @Throws(IllegalStateException::class)
    fun pause()

    fun setScreenOnWhilePlaying(var1: Boolean)

    fun getVideoWidth(): Int

    fun getVideoHeight(): Int

    fun isPlaying(): Boolean

    @Throws(IllegalStateException::class)
    fun seekTo(var1: Long)

    fun getCurrentPosition(): Long

    fun getDuration(): Long

    fun release()

    fun reset()

    fun setVolume(var1: Float, var2: Float)

    fun getAudioSessionId(): Int

//    fun getMediaInfo(): MediaInfo?


    @Deprecated("")
    fun setLogEnabled(var1: Boolean)


    @Deprecated("")
    fun isPlayable(): Boolean

    fun setOnPreparedListener(var1: OnPreparedListener?)

    fun setOnCompletionListener(var1: OnCompletionListener?)

    fun setOnBufferingUpdateListener(var1: OnBufferingUpdateListener?)

    fun setOnSeekCompleteListener(var1: OnSeekCompleteListener?)

    fun setOnVideoSizeChangedListener(var1: OnVideoSizeChangedListener?)

    fun setOnErrorListener(var1: OnErrorListener?)

    fun setOnInfoListener(var1: OnInfoListener?)

    fun setOnTimedTextListener(var1: OnTimedTextListener?)

    fun setOnPlayerClockChangedListener(var1: Looper?, var2: OnPlayerClockChangedListener?)

    fun setAudioStreamType(var1: Int)


    @Deprecated("")
    fun setKeepInBackground(var1: Boolean)

    fun getVideoSarNum(): Int

    fun getVideoSarDen(): Int


    @Deprecated("")
    fun setWakeMode(var1: Context?, var2: Int)

    fun setLooping(var1: Boolean)

    fun isLooping(): Boolean

//    fun getTrackInfo(): Array<ITrackInfo?>?

    fun setSurface(var1: Surface?)

//    fun setDataSource(var1: IMediaDataSource?)

    interface OnPlayerClockChangedListener {
        fun onPlayerClockChanged(var1: IMediaPlayer?, var2: Float, var3: Long)
    }

    interface OnTrackerListener {
        fun onTrackerReport(var1: Boolean, var2: String, var3: Map<String?, String?>, var4: String?, var5: Map<String?, String?>?)
    }

    interface OnTimedTextListener {
//        fun onTimedText(var1: IMediaPlayer?, var2: IjkTimedText?)
    }

    interface OnInfoListener {
        fun onInfo(var1: IMediaPlayer?, var2: Int, var3: Int, var4: Bundle?): Boolean
    }

    interface OnErrorListener {
        fun onError(var1: IMediaPlayer?, var2: Int, var3: Int): Boolean
    }

    interface OnVideoSizeChangedListener {
        fun onVideoSizeChanged(var1: IMediaPlayer?, var2: Int, var3: Int, var4: Int, var5: Int)
    }

    interface OnSeekCompleteListener {
        fun onSeekComplete(var1: IMediaPlayer?)
    }

    interface OnBufferingUpdateListener {
        fun onBufferingUpdate(var1: IMediaPlayer?, var2: Int)
    }

    interface OnCompletionListener {
        fun onCompletion(var1: IMediaPlayer?)
    }

    interface OnPreparedListener {
        fun onPrepared(var1: IMediaPlayer?)
    }
}