package com.dawn.zgstep.player.media.localplay;

import android.graphics.SurfaceTexture;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.Build;
import android.view.Surface;


import androidx.annotation.RequiresApi;

import com.dawn.zgstep.player.media.localplay.decode.AudioDecoder;
import com.dawn.zgstep.player.media.localplay.decode.OnAudioDecodeListener;
import com.dawn.zgstep.player.media.localplay.decode.OnVideoDecodeListener;
import com.dawn.zgstep.player.media.localplay.decode.VideoDecoder;
import com.dawn.zgstep.player.media.localplay.ecode.AudioEncoder;
import com.dawn.zgstep.player.media.localplay.ecode.OnAudioEncodeListener;
import com.dawn.zgstep.player.media.localplay.util.GLCommonUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class LocalMediaManager implements OnVideoDecodeListener, OnAudioDecodeListener, OnAudioEncodeListener {

    private static final String TAG = "LocalMediaManager";

    public static final int STATE_IDLE = 0x01;
    public static final int STATE_INITIALIZED = 0x02;
    public static final int STATE_PREPARED = 0x03;
    public static final int STATE_PLAYING = 0x04;
    public static final int STATE_PAUSING = 0x05;

    private static final int TEXTURE_END_FLAG = -1;

    private int mState;
    private String mPath;

    private AudioDecoder mAudioDeocder;
    private VideoDecoder mVideoDecoder;

    private int mLocalTextureId = -1;
    private SurfaceTexture mLocalSurfaceTexture;
    private Surface mLocalSurface;
    private OnLocalMediaListener mListener;

    private int mVideoWidth, mVideoHeight;

    private OnAACEventListener mAACEventListener;

    private boolean singleVideo = false;

    public void singleVideo() {
        singleVideo = true;
    }

    public void setAACEventListener(OnAACEventListener aacEventListener) {
        this.mAACEventListener = aacEventListener;
    }
    private boolean mLooping;

//    private LinkedBlockingQueue<byte[]> mAudioByteBufferQueue;

    public LocalMediaManager() {

        mAudioDeocder = new AudioDecoder();
        mVideoDecoder = new VideoDecoder();

        mAudioDeocder.setListener(this);
        mVideoDecoder.setListener(this);

        mState = STATE_IDLE;
    }

    public void setDataSource(String path) throws IOException {
        if (mState != STATE_IDLE) {
            throw new IllegalStateException();
        }
        mPath = path;
        mAudioDeocder.setDataSource(path);
        mVideoDecoder.setDataSource(path);
        mState = STATE_INITIALIZED;
    }

    public void setListener(OnLocalMediaListener listener) {
        this.mListener = listener;
    }

    public int getState() {
        return mState;
    }

    public void prepare(int textureId) throws IOException {
        if (mState != STATE_INITIALIZED) {
            throw new IllegalStateException();
        }
        initSurface(textureId);
        mVideoDecoder.prepare(mLocalSurface);
        mAudioDeocder.prepare();

        MediaFormat audioMediaFormat = mAudioDeocder.getMediaFormat();
        if (null != mListener && audioMediaFormat != null) {
            mListener.onAudioFormatChange(audioMediaFormat);
        }

        MediaFormat videoMediaFormat = mVideoDecoder.getMediaFormat();
        if (videoMediaFormat != null) {

            mVideoWidth = videoMediaFormat.getInteger(MediaFormat.KEY_WIDTH);
            mVideoHeight = videoMediaFormat.getInteger(MediaFormat.KEY_HEIGHT);

            //  视频旋转顺时针角度
            int rotation = -1;
            if (videoMediaFormat.containsKey(MediaFormat.KEY_ROTATION)) {
                rotation = videoMediaFormat.getInteger(MediaFormat.KEY_ROTATION);
            }

            if (rotation != -1) {
                if (rotation == 90) {
                    int temp = mVideoWidth;
                    mVideoWidth = mVideoHeight;
                    mVideoHeight = temp;
                }
            }

            if (null != mListener) {
                mListener.onVideoFormatChange(videoMediaFormat, false);
            }
        }

        mState = STATE_PREPARED;
    }

    private void initSurface(int textureId) {
        mLocalTextureId = textureId;
        mLocalSurfaceTexture = new SurfaceTexture(mLocalTextureId);
        mLocalSurface = new Surface(mLocalSurfaceTexture);
    }

    private boolean outSet = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void prepare(SurfaceTexture surfaceTexture) throws IOException {
        if (mState != STATE_INITIALIZED) {
            throw new IllegalStateException();
        }
        outSet = true;
        mLocalSurfaceTexture = surfaceTexture;

        mLocalSurface = new Surface(mLocalSurfaceTexture);

        try {
            mVideoDecoder.prepare(mLocalSurface);
            mAudioDeocder.prepare();
        } catch (MediaCodec.CodecException e) {
            e.printStackTrace();
            return;
        }

        MediaFormat audioMediaFormat = mAudioDeocder.getMediaFormat();
        if (null != mListener && audioMediaFormat != null) {
            mListener.onAudioFormatChange(audioMediaFormat);
        }

        MediaFormat videoMediaFormat = mVideoDecoder.getMediaFormat();
        if (videoMediaFormat != null) {

            mVideoWidth = videoMediaFormat.getInteger(MediaFormat.KEY_WIDTH);
            mVideoHeight = videoMediaFormat.getInteger(MediaFormat.KEY_HEIGHT);

            //  视频旋转顺时针角度
            int rotation = -1;
            if (videoMediaFormat.containsKey(MediaFormat.KEY_ROTATION)) {
                rotation = videoMediaFormat.getInteger(MediaFormat.KEY_ROTATION);
            }

            if (rotation != -1) {
                if (rotation == 90) {
                    int temp = mVideoWidth;
                    mVideoWidth = mVideoHeight;
                    mVideoHeight = temp;
                }
            }

            if (null != mListener) {
                mListener.onVideoFormatChange(videoMediaFormat, false);
            }
        }

        mState = STATE_PREPARED;
    }

    public SurfaceTexture getSurfaceTexture() {
        return mLocalSurfaceTexture;
    }

    public int getSurfaceTextureId() {
        return mLocalTextureId;
    }

    public int getVideoWidth() {
        return mVideoWidth;
    }

    public int getVideoHeight() {
        return mVideoHeight;
    }

    public void setLooping(boolean looping) {
        mLooping = looping;
    }

    public void start() {
        if (mState != STATE_PREPARED) {
            throw new IllegalStateException();
        }
        mAudioDeocder.start();
        mVideoDecoder.start();
        mState = STATE_PLAYING;
    }

    public void start(long time) throws InterruptedException {
        if (mState != STATE_PREPARED) {
            throw new IllegalStateException();
        }
        Thread.sleep(time);
        mAudioDeocder.start();
        mVideoDecoder.start();
        mState = STATE_PLAYING;
    }

    public void pause() {
        if (mState != STATE_PLAYING) {
            throw new IllegalStateException();
        }
        mAudioDeocder.pause();
        mVideoDecoder.pause();
        mState = STATE_PAUSING;
    }

    public void audioPause() {
        mAudioDeocder.pause();
    }

    public void audioResume() {
        if (!mAudioDeocder.isAudioFinished()) {
            mAudioDeocder.resume();
        }
    }

    public void resume() {
        if (mState != STATE_PAUSING) {
            return;
        }
        if (!mAudioDeocder.isAudioFinished()) {
            mAudioDeocder.resume();
        }
        if (!mVideoDecoder.isVidoeFinished()) {
            mVideoDecoder.resume();
        }
        mState = STATE_PLAYING;
    }

    public void stop() {
        if (mState != STATE_PLAYING && mState != STATE_PAUSING) {
            return;
        }
        mAudioDeocder.stop();
        mVideoDecoder.stop();
        if (mAudioEncoder != null) {
            mAudioEncoder.stop();
            mAudioEncoder = null;
            if (mFileOutputStream != null) {
                try {
                    mFileOutputStream.close();
                    mFileOutputStream = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        mState = STATE_PREPARED;
    }

    public void release() {
        if (mState == STATE_IDLE || mState == STATE_INITIALIZED) {
            return;
        }
        stop();
        if (mState == STATE_PREPARED) {
            mAudioDeocder.release();
            mVideoDecoder.release();
            GLCommonUtil.deleteGLTexture(mLocalTextureId);
            mLocalSurfaceTexture.setOnFrameAvailableListener(null);
            if (!outSet) {
                mLocalSurfaceTexture.release();
            }
            mLocalSurface.release();
        }
        mState = STATE_IDLE;
    }


    @Override
    public void onAudioDecodeData(ByteBuffer bb, MediaCodec.BufferInfo bi) {
        if (null != mListener) {
            mListener.onAudioDataUpdate(bb, bi);
        }
        if (null != mAudioEncoder) {

            if (mReleaseAudio) {
                if (null != mAudioEncoder) {
                    try {
                        mAudioEncoder.stop();
                        mAudioEncoder = null;
                        if (null != mFileOutputStream) {
                            mFileOutputStream.close();
                            mFileOutputStream = null;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                bb.position(0);
                mAudioEncoder.offerEncoder(bb, bi.presentationTimeUs);
            }
        }
    }

    @Override
    public void onAudioFormatChanged(MediaFormat mediaFormat) {
        if (null != mListener) {
            mListener.onAudioFormatChange(mediaFormat);
        }
    }

    @Override
    public void onVideoDecodeData(long pts) {
        if (null != mListener) {
            mListener.onVideoDataUpdate(pts);
        }
    }

    @Override
    public void onAudioDecodeFinish(boolean confirmation) {
        if (null != mListener) {
            mListener.onAudioFinish(confirmation);
        }
        doubleCheck();
    }

    @Override
    public void onVideoDecodeFinish() {
        if (null != mListener) {
            mListener.onVideoFinish();
        }
        if (singleVideo) {
            if (mLooping) {
                mVideoDecoder.replay();
            }
        } else {
            doubleCheck();
        }
    }


    public synchronized void doubleCheck() {
        if (mAudioDeocder.isAudioFinished() && mVideoDecoder.isVidoeFinished() && mLooping) {

            mAudioDeocder.replay();
            mVideoDecoder.replay();

        }
    }


    @Override
    public void onVideoFormatChanged(MediaFormat mediaFormat) {
        if (null != mListener) {
            mListener.onVideoFormatChange(mediaFormat, true);
        }
    }

    /**
     * ----------- AAC 音频生成逻辑 ----------------
     */

    private AudioEncoder mAudioEncoder;
    private FileOutputStream mFileOutputStream;
    private long mAACCountTime = 0, mAACLastTime = -1;

    private boolean mReleaseAudio = false;
    private String mAACPath;

    public void setAACSource(String path) {

        if (mAudioDeocder == null || mAudioDeocder.getMediaFormat() == null) {
            return;
        }

        mAACPath = path;

        if (null != mAudioEncoder) {
            try {
                mAudioEncoder.stop();
                mAudioEncoder = null;
                if (null != mFileOutputStream) {
                    mFileOutputStream.close();
                    mFileOutputStream = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mAACCountTime = 0;
        mAACLastTime = -1;
        mReleaseAudio = false;

        try {

            File mAACFile = new File(path);
            if (!mAACFile.exists()) {
                mAACFile.createNewFile();
            }
            mFileOutputStream = new FileOutputStream(mAACFile.getAbsoluteFile());

            mAudioEncoder = new AudioEncoder(mAudioDeocder.getMediaFormat());
            mAudioEncoder.start();
            mAudioEncoder.setOnAudioEncodeListener(this);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onAudioEncode(ByteBuffer bb, MediaCodec.BufferInfo bi) {

        if (bi.presentationTimeUs == 0) {
            return;
        }

        if (mAACLastTime != -1) {

            if (bi.presentationTimeUs < mAACLastTime) {
                mAACCountTime += bi.presentationTimeUs;
            } else {
                mAACCountTime += (bi.presentationTimeUs - mAACLastTime);
            }
        }

        if (mAACCountTime / 1000 / 1000 <= 65) {

            //获取缓存信息的长度
            int byteBufSize = bi.size;
            //添加ADTS头部后的长度
            int bytePacketSize = byteBufSize + 7;

            byte[] targetByte = new byte[bytePacketSize];
            //添加ADTS头部
            AudioEncoder.addADTStoPacket(targetByte, bytePacketSize);

            bb.get(targetByte, 7, byteBufSize);

            try {
                mFileOutputStream.write(targetByte);
            } catch (IOException e) {
                e.printStackTrace();
            }

            mAACLastTime = bi.presentationTimeUs;
        } else {

            System.out.println("close------>" + mAACCountTime / 1000 / 1000);

            if (null != mAACEventListener) {
                mAACEventListener.onAacComplate(mAACPath);
            }

            mReleaseAudio = true;
        }
    }

    @Override
    public void onAudioFormatChange(MediaFormat format) {

    }

}
