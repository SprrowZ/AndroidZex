package com.dawn.zgstep.player.layers

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import com.dawn.zgstep.R
import com.dawn.zgstep.player.IMediaPlayer
import com.dawn.zgstep.player.assist.DemoLife
import com.dawn.zgstep.player.base.IPlayerController
import com.dawn.zgstep.player.base.IVideoLayer
import java.util.*

/**
 * Create by  [Rye]
 *
 * 播放控制层
 * at 2021/12/1 4:14 下午
 */
class PlayerControlLayer @JvmOverloads constructor(
    private val mPlayerController: IPlayerController,
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle), IVideoLayer {
    private var mRoot: View? = null
    private var mSpaceLayout: View? = null
    private var mSpace: View? = null
    private var mStartPause: ImageView? = null
    private var mTvProgress: TextView? = null
    private var mProgressBar: SeekBar? = null
    private var mTvDuration: TextView? = null
    private var mFullScreen: ImageView? = null

    companion object {
        fun create(context: Context, playController: IPlayerController): PlayerControlLayer {
            return PlayerControlLayer(playController, context)
        }
    }

    private val mMediaPlayer: IMediaPlayer?
        get() {
            return mPlayerController.getMediaService()?.getMediaPlayer()
        }

    private val isPlaying: Boolean
        get() {
            return mMediaPlayer?.isPlaying() == true
        }

    private val mDuration: Long?
        get() {
            return mMediaPlayer?.getDuration()
        }

    private val mOnPreparedListener = object : IMediaPlayer.OnPreparedListener {
        override fun onPrepared(mp: IMediaPlayer?) {
            Log.e("RRye", "onPrepared..duration:$mDuration")
            mTvDuration?.text = stringForTime(mDuration)
        }
    }

    private val mProgressListener = object : IMediaPlayer.OnProgressListener {
        override fun onProgress(duration: Long, progress: Long) {
            mTvProgress?.text = stringForTime(progress)
            if (mTvDuration?.text.isNullOrEmpty()) {
                mTvDuration?.text = stringForTime(duration)
            }
            if (duration == 0L) return
            val barProgress = (progress.toFloat() / duration * 100).toInt()
            mProgressBar?.progress = barProgress
            Log.i("RRye", "duration:$duration,progress:$progress,barProgress:$barProgress")
        }
    }


    init {
        mRoot = LayoutInflater.from(context).inflate(getLayoutRes(), this, false)
        addView(mRoot)
        initWidget()
        initEvent()
    }

    private fun initWidget() {
        mRoot?.apply {
            mSpaceLayout = findViewById(R.id.space_layout)
            mSpace = findViewById(R.id.space)
            mStartPause = findViewById(R.id.start_pause)
            mTvProgress = findViewById(R.id.tv_progress)
            mProgressBar = findViewById(R.id.progressbar)
            mTvDuration = findViewById(R.id.tv_duration)
            mFullScreen = findViewById(R.id.fullscreen)
        }
        val life = DemoLife()
        Log.e("RRye", "life ok!$life")
    }


    private fun initEvent() {
        mSpaceLayout?.setOnClickListener {
            startOrPause()
        }
        mStartPause?.setOnClickListener {
            startOrPause()
        }
        mFullScreen?.setOnClickListener {

        }
        mProgressBar?.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                  seekBar?.progress?.let {
//                    mMediaPlayer?.seekTo(it.toLong())
//                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                TODO("Not yet implemented")
            }

        })
        registerListener()
    }

    private fun toggleSpaceBg() {
        mSpace?.visibility = View.VISIBLE
        mSpace?.alpha = 1.0F
        if (isPlaying) {
            mSpace?.background = ContextCompat.getDrawable(context, R.drawable.video_pause)
        } else {
            mSpace?.background = ContextCompat.getDrawable(context, R.drawable.video_play)
        }
        ObjectAnimator.ofFloat(mSpace!!, "alpha", 1.0f, 0f)
            .setDuration(1000)
            .start()
    }

    private fun startOrPause() {
        if (isPlaying) {
            mMediaPlayer?.pause()
        } else {
            mMediaPlayer?.start()
        }
        toggleSpaceBg()
    }

    private fun registerListener() {
        mMediaPlayer?.setOnPreparedListener(mOnPreparedListener)
        Log.i("RRye", "mProgressListener:$mProgressListener")
        mPlayerController.getMediaService()?.registerOnProgressListener(mProgressListener)
    }

    /**
     * 格式化时间
     */
    fun stringForTime(timeMs: Long?): String {
        timeMs ?: return "null"
        val totalSeconds = timeMs / 1000
        val seconds = totalSeconds % 60
        val minutes = totalSeconds / 60 % 60
        val hours = totalSeconds / 3600
        return if (hours > 0) {
            String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        }
    }


    @LayoutRes
    private fun getLayoutRes(): Int {
        return R.layout.player_control_layer
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.i("RRye", "onDetachedFromWindow.")
    }
}