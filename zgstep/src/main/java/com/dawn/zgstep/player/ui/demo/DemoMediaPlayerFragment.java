package com.dawn.zgstep.player.ui.demo;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.dawn.zgstep.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Create by  [Rye]
 * <p>
 * at 2022/8/30 3:52 下午
 */
public class DemoMediaPlayerFragment extends Fragment implements View.OnClickListener{
    private View mRoot;
    private static final String TAG = DemoMediaPlayerFragment.class.getSimpleName();
    private ConstraintLayout mRootLayout;
    private SurfaceView mVideoPlaySurfaceview;
    private ImageView mStartAndStop, mBack;
    private MediaPlayer mMediaPlayer;
    private SeekBar mVedioSeek;
    private TextView mVedioCurrentTimeTextView, mVedioTotalTimeTextView, mVedioSettings;
    private String mPath;
    private boolean isInitFinish = false;
    private SurfaceHolder mSurfaceHolder;
    private Handler mHandler;
    private final int GET_VIDEO_PLAY_TIME_KEY = 0x01;
    private final int GONS_VIEW_KEY = 0x02;
    private static int mCurrentPlayTime = 0; //因为切换横竖屏后,activity其实是重新创建的,所以需要用静态变量保存当前播放时间
    private int mTotalPlayTime = 0;
    SimpleDateFormat mTimeFormat = new SimpleDateFormat("mm:ss");
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_demo_player, container, false);
        return mRoot;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        // initVedioSeekListener();
        initHandler();
        mHandler.sendEmptyMessageDelayed(GONS_VIEW_KEY,8*1000);
        File file = new File(getContext().getExternalCacheDir(), "demo.mp4");
        mPath = "https://1688living.alicdn.com/mediaplatform/90ed0fdb-6583-4892-a8e1-6dbc52b540e4.m3u8";
        initMediaPalyer();
        initSurfaceviewStateListener();


    }

    private void initView(){
        mRootLayout = mRoot.findViewById(R.id.root_layout);
        mVideoPlaySurfaceview = mRoot.findViewById(R.id.video_play_surfaceview);
        mStartAndStop = mRoot.findViewById(R.id.start_and_stop);
        mVedioCurrentTimeTextView = mRoot.findViewById(R.id.vedio_current_time);
        mVedioTotalTimeTextView = mRoot.findViewById(R.id.vedio_total_time);
        mVedioSeek = mRoot.findViewById(R.id.vedio_seek);
        mVedioSettings = mRoot.findViewById(R.id.vedio_settings);
        mBack = mRoot.findViewById(R.id.back);
        mStartAndStop.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mRootLayout.setOnClickListener(this);
    }

    private void initMediaPalyer() {
        mMediaPlayer = new MediaPlayer();

    }

    private void setPlayVideo(String path) {
        try {
            mMediaPlayer.setDataSource(path);//
            mMediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);//缩放模式
            mMediaPlayer.setLooping(true);//设置循环播放
            mMediaPlayer.prepareAsync();//异步准备
//            mMediaPlayer.prepare();//同步准备,因为是同步在一些性能较差的设备上会导致UI卡顿
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { //准备完成回调
                @Override
                public void onPrepared(MediaPlayer mp) {
                    isInitFinish = true;
                    mMediaPlayer.start();
                    mHandler.sendEmptyMessage(GET_VIDEO_PLAY_TIME_KEY);
                    mTotalPlayTime = mMediaPlayer.getDuration();
                    if (mTotalPlayTime == -1){
                        Toast.makeText(getContext(), "视频文件时间异常", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }
                    mVedioSeek.setMax(mTotalPlayTime/1000);
                    mVedioTotalTimeTextView.setText(formatTime(mTotalPlayTime));
                }
            });
            mMediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() { //尺寸变化回调
                @Override
                public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                    changeVideoSize();
                    //L.e("mCurrentPlayTime="+mCurrentPlayTime);
                    seekTo(mCurrentPlayTime);

                }
            });
            mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    //L.e("视频播放错误,错误原因what="+what+"extra="+extra);
                    return false;
                }
            });
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 修改视频的大小,以用来适配屏幕
     */
    public void changeVideoSize() {
        int videoWidth = mMediaPlayer.getVideoWidth();
        int videoHeight = mMediaPlayer.getVideoHeight();
        int deviceWidth = getResources().getDisplayMetrics().widthPixels;
        int deviceHeight = getResources().getDisplayMetrics().heightPixels;
        Log.e(TAG, "changeVideoSize: deviceHeight="+deviceHeight+"deviceWidth="+deviceWidth);
        float devicePercent = 0;
        //下面进行求屏幕比例,因为横竖屏会改变屏幕宽度值,所以为了保持更小的值除更大的值.
        if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) { //竖屏
            devicePercent = (float) deviceWidth / (float) deviceHeight; //竖屏状态下宽度小与高度,求比
        }else { //横屏
            devicePercent = (float) deviceHeight / (float) deviceWidth; //横屏状态下高度小与宽度,求比

        }

        if (videoWidth > videoHeight){
            if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
                videoWidth = deviceWidth;
                videoHeight = (int)(videoHeight/devicePercent);

            }else {
                videoWidth = deviceWidth;
                videoHeight = (int)(deviceWidth*devicePercent);
            }

        }else {
            if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {//竖屏

                videoHeight = deviceHeight;
                float videoPercent = (float) videoWidth / (float) videoHeight;
                float differenceValue = Math.abs(videoPercent - devicePercent);
                if (differenceValue < 0.3){
                    videoWidth = deviceWidth;
                }else {
                    videoWidth = (int)(videoWidth/devicePercent);
                }


            }else { //横屏
                videoHeight = deviceHeight;
                videoWidth = (int)(deviceHeight*devicePercent);

            }

        }

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mVideoPlaySurfaceview.getLayoutParams();
        layoutParams.width = videoWidth;
        layoutParams.height = videoHeight;
        layoutParams.verticalBias = 0.5f;
        layoutParams.horizontalBias = 0.5f;
        mVideoPlaySurfaceview.setLayoutParams(layoutParams);

    }




    private void startPlay(){
        if (!mMediaPlayer.isPlaying()){
            mMediaPlayer.start();
        }
    }

    private void stopPlay(){
        if (mMediaPlayer.isPlaying()){
            mMediaPlayer.stop();
        }
    }

    private void pausePlay(){
        if (mMediaPlayer.isPlaying()){
            mMediaPlayer.pause();
        }
    }

    private void seekTo(int time){
        mMediaPlayer.seekTo(time);
    }

    private String formatTime(long time){
        return mTimeFormat.format(time);
    }

    @Override
    public void onPause() {
        super.onPause();
        pausePlay();
    }

    @Override
    public void onResume() {
        super.onResume();
        startPlay();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null){
            if (mMediaPlayer.isPlaying()){
                mMediaPlayer.stop();
            }
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.start_and_stop) {
                            if (mMediaPlayer.isPlaying()) {
                    pausePlay();
                   // mStartAndStop.setImageResource(R.mipmap.ic_sex_male);
                } else {
                    startPlay();
                  //  mStartAndStop.setImageResource(R.mipmap.ic_sex_female);
                }
        } else if (id == R.id.back) {
            getActivity().finish();
        } else if (id == R.id.root_layout) {
            mStartAndStop.setVisibility(View.VISIBLE);
            mVedioSettings.setVisibility(View.VISIBLE);
            mVedioTotalTimeTextView.setVisibility(View.VISIBLE);
            mVedioCurrentTimeTextView.setVisibility(View.VISIBLE);
            mBack.setVisibility(View.VISIBLE);
            mVedioSeek.setVisibility(View.VISIBLE);
            mHandler.sendEmptyMessageDelayed(GONS_VIEW_KEY, 8 * 1000);
        }

    }

    private void initHandler(){
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case GET_VIDEO_PLAY_TIME_KEY:
                        if (mMediaPlayer != null){
                            mCurrentPlayTime = mMediaPlayer.getCurrentPosition();
                            //L.e("每秒mCurrentPlayTime="+mCurrentPlayTime);
                            mVedioSeek.setProgress(mCurrentPlayTime/1000);
                            mVedioCurrentTimeTextView.setText(mTimeFormat.format(mCurrentPlayTime));

                        }
                        mHandler.sendEmptyMessageDelayed(GET_VIDEO_PLAY_TIME_KEY,1000);
                        break;
                    case GONS_VIEW_KEY:
                        mStartAndStop.setVisibility(View.GONE);
                        mVedioSettings.setVisibility(View.GONE);
                        mVedioTotalTimeTextView.setVisibility(View.GONE);
                        mVedioCurrentTimeTextView.setVisibility(View.GONE);
                        mBack.setVisibility(View.GONE);
                        mVedioSeek.setVisibility(View.GONE);
                        break;
                    default:
                        break;

                }
            }
        };
    }


    private void initVedioSeekListener(){
        mVedioSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mMediaPlayer.seekTo(seekBar.getProgress()*1000);
                mCurrentPlayTime = mMediaPlayer.getCurrentPosition();
                mVedioCurrentTimeTextView.setText(mTimeFormat.format(mCurrentPlayTime));

            }
        });
    }

    private void initSurfaceviewStateListener() {
        mSurfaceHolder = mVideoPlaySurfaceview.getHolder();
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
              //  mMediaPlayer.setDisplay(holder);//给mMediaPlayer添加预览的SurfaceHolder
                mMediaPlayer.setSurface(mVideoPlaySurfaceview.getHolder().getSurface());
                setPlayVideo(mPath);//添加播放视频的路径
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.e(TAG, "surfaceChanged触发: width=" + width + "height" + height);

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }
}
