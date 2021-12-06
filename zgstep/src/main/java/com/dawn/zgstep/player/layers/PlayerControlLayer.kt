package com.dawn.zgstep.player.layers

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import com.dawn.zgstep.R
import com.dawn.zgstep.player.IMediaPlayer
import com.dawn.zgstep.player.IPlayerController
import com.dawn.zgstep.player.base.IVideoLayer

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
    private var mProgressBar: ProgressBar? = null
    private var mTvDuration: TextView? = null
    private var mFullScreen: TextView? = null

    companion object {
        fun create(context: Context, playController: IPlayerController): PlayerControlLayer {
            return PlayerControlLayer(playController,context)
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
    }

    private val mMediaPlayer: IMediaPlayer?
        get() {
            return mPlayerController?.getMediaPlayer()
        }
    private val isPlaying: Boolean
        get() {
            return mMediaPlayer?.isPlaying() == true
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


    @LayoutRes
    private fun getLayoutRes(): Int {
        return R.layout.player_control_layer
    }
}