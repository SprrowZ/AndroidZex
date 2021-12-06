package com.rye.catcher.project.media.record

import android.util.Log
import android.view.SurfaceView
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.dawn.zgstep.player.IPlayerController
import com.dawn.zgstep.player.VideoDetail
import com.rye.base.BaseFragment
import com.rye.catcher.R

/**
 * Create by  [Rye]
 *
 * at 2021/11/20 3:56 下午
 */
class MediaRecorderFragment : BaseFragment() {
    private var mRecordAudio: TextView? = null
    private var mRecordVideo: TextView? = null
    private var mAudioIcon: ImageView? = null
    private var mVideoIcon: ImageView? = null
    private var mMediaRecorderManager: MediaRecorderManager? = null
    private var mPlayerController: IPlayerController? = null
    private var mIsRecordingAudio = false
    private var mIsRecordingVideo = false
    private var mPlayerContainer: FrameLayout? = null
    override fun getLayoutId(): Int {
        return R.layout.fragment_media_recorder
    }

    override fun initWidget() {
        super.initWidget()
        mRoot?.apply {
            mRecordAudio = findViewById(R.id.record_audio)
            mRecordVideo = findViewById(R.id.record_video)
            mAudioIcon = findViewById(R.id.audio_icon)
            mPlayerContainer = findViewById(R.id.player_container)
            mVideoIcon = findViewById(R.id.video_icon)
        }

    }

    override fun initEvent() {
        super.initEvent()
        if (mMediaRecorderManager == null) {
            mMediaRecorderManager = MediaRecorderManager.create()
        }
        if (mPlayerController == null) {
            mPlayerController = IPlayerController.create()
        }
        mRecordAudio?.setOnClickListener {
            toggleRecordAudio()
        }
        mAudioIcon?.setOnClickListener {
            playRecordAudio()
        }
        mRecordVideo?.setOnClickListener {
            toggleRecordVideo()
        }
        mVideoIcon?.setOnClickListener {
            playRecordVideo()
        }
        mVideoIcon?.visibility = View.VISIBLE
    }

    private fun toggleRecordVideo() {
        mPlayerContainer?.visibility = View.VISIBLE
        mAudioIcon?.visibility = View.GONE
        if (mIsRecordingVideo) {
            mIsRecordingVideo = false
            mRecordVideo?.text = "录制视频"
            mMediaRecorderManager?.stopRecord()
            mVideoIcon?.visibility = View.VISIBLE
        } else {
            mIsRecordingVideo = true
            mRecordVideo?.text = "录制视频中..."
            //TODO
            //mSurfaceView?.holder?.let { mMediaRecorderManager?.recordVideo(it) }
            mVideoIcon?.visibility = View.GONE
        }
    }

    private fun toggleRecordAudio() {
        mPlayerContainer?.visibility = View.GONE
        mVideoIcon?.visibility = View.GONE
        if (mIsRecordingAudio) {
            mIsRecordingAudio = false
            mRecordAudio?.text = "录制音频"
            mMediaRecorderManager?.stopRecord()
            mAudioIcon?.visibility = View.VISIBLE
        } else {
            mIsRecordingAudio = true
            mRecordAudio?.text = "录制音频中..."
            mMediaRecorderManager?.recordAudio()
            mAudioIcon?.visibility = View.GONE
        }
    }

    private fun playRecordAudio() {
        val audioPath = mMediaRecorderManager?.getAudioPath()
        if (audioPath == null) {
            Log.e("RRye", "audioPath is Null..")
            return
        }
        mPlayerController?.goPlayAudio(audioPath)
    }

    private fun playRecordVideo() {
        mPlayerContainer?.visibility = View.VISIBLE
        val videoPath = mMediaRecorderManager?.getVideoPath()
        if (videoPath == null) {
            Log.e("RRye", "videoPath is Null..")
            return
        }
       // mMediaRecorderManager?.removeVideoSurfaceCallback(mSurfaceView?.holder)
        val video = VideoDetail().run {
            url = videoPath
            container = mPlayerContainer
            this
        }
       mPlayerController?.goPlay(video)
    }


    fun test(action: View.() -> Unit) {
        mRoot?.apply {
            action.invoke(this)
        }
    }
}