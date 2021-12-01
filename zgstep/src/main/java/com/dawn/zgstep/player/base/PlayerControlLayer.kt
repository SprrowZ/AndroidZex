package com.dawn.zgstep.player.base

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.dawn.zgstep.R
import com.dawn.zgstep.player.IMediaPlayer
import com.dawn.zgstep.player.IPlayerController

/**
 * Create by  [Rye]
 *
 * 播放控制层
 * at 2021/12/1 4:14 下午
 */
class PlayerControlLayer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {
    private var mPlayerController: IPlayerController? = null
    private var mRoot: View? = null
    private var mSpaceLayout: View? = null
    private var mStartPause: ImageView? = null
    private var mTvProgress: TextView? = null
    private var mProgressBar: ProgressBar? = null
    private var mTvDuration: TextView? = null
    private var mFullScreen: TextView? = null

    init {
        mRoot = LayoutInflater.from(context).inflate(getLayoutRes(), this, false)
        addView(mRoot)
        initWidget()
        initEvent()
    }

    private fun initWidget() {
        mRoot?.apply {
            mSpaceLayout = findViewById(R.id.space)
            mStartPause = findViewById(R.id.start_pause)
            mTvProgress = findViewById(R.id.tv_progress)
            mProgressBar = findViewById(R.id.progressbar)
            mTvDuration = findViewById(R.id.tv_duration)
            mFullScreen = findViewById(R.id.fullscreen)
        }
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

    private val mMediaPlayer: IMediaPlayer?
        get() {
            return mPlayerController?.getMediaPlayer()
        }
    private val isPlaying: Boolean
        get() {
            return mMediaPlayer?.isPlaying() == true
        }

    private fun startOrPause() {
        if (isPlaying) {
            mMediaPlayer?.pause()
        } else {
            mMediaPlayer?.start()
        }
    }


    fun setPlayerController(playerController: IPlayerController) {
        this.mPlayerController = playerController
    }


    @LayoutRes
    private fun getLayoutRes(): Int {
        return R.layout.player_control_layer
    }
}