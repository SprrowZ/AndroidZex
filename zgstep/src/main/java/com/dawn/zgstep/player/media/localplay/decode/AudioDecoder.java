package com.dawn.zgstep.player.media.localplay.decode;

import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.text.TextUtils;
import android.util.Log;


import com.dawn.zgstep.player.media.localplay.util.MediaUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

public class AudioDecoder {

    private static final String TAG = "AudioDecoder";
    private static final int STATE_IDLE          = 0x01;
    private static final int STATE_INITIALIZED   = 0x02;
    private static final int STATE_PREPARED      = 0x03;
    private static final int STATE_DECODING      = 0x04;
    private static final int STATE_PAUSING       = 0x05;

    private int mState;
    private String mPath;
    private MediaCodec mMediaCodec;
    private MediaExtractor mMediaExtractor;
    private MediaFormat mMediaFormat;

    private DecodeThread mDecodeThread;
    private OnAudioDecodeListener mListener;

    private volatile boolean audioFinished = false;

    public AudioDecoder() {
        mState = STATE_IDLE;
    }

    public void setDataSource(String path) throws IOException {
        if (TextUtils.isEmpty(path)) {
            throw new IllegalArgumentException();
        }
//        File file = new File(path);
//        if (!file.exists()) {
//            throw new FileNotFoundException();
//        }
        if (mState != STATE_IDLE) {
            throw new IllegalStateException();
        }
        mPath = path;
        mState = STATE_INITIALIZED;
    }

    public void setDataSource(String path,boolean net) throws IOException {
        if (TextUtils.isEmpty(path)) {
            throw new IllegalArgumentException();
        }
//        File file = new File(path);
//        if (!file.exists()) {
//            throw new FileNotFoundException();
//        }
        if (mState != STATE_IDLE) {
            throw new IllegalStateException();
        }
        mPath = path;
        mState = STATE_INITIALIZED;
    }




    public void setListener(OnAudioDecodeListener listener) {
        this.mListener = listener;
    }

    public void prepare() throws IOException, MediaCodec.CodecException {
        if (mState != STATE_INITIALIZED) {
            throw new IllegalStateException();
        }
        mMediaExtractor = MediaUtil.createExtractor(mPath);
        int trackIndex = MediaUtil.getAndSelectAudioTrackIndex(mMediaExtractor);
        if(trackIndex == -1) {
            Log.e(TAG, "trackIndex error");
            return;
        }
        mMediaFormat = mMediaExtractor.getTrackFormat(trackIndex);
        String audioMime = MediaUtil.getMimeTypeFor(mMediaFormat);
        mMediaCodec = MediaCodec.createDecoderByType(audioMime);
        mMediaCodec.configure(mMediaFormat, null, null, 0);
        mMediaCodec.start();
        mState = STATE_PREPARED;
    }

    public MediaFormat getMediaFormat() {
        if(mState == STATE_IDLE || mState == STATE_INITIALIZED) {
            return null;
        }
        return mMediaFormat;
    }


    public void start() {
        if (mState != STATE_PREPARED) {
            Log.e(TAG, "start state error, current State ->" + mState);
            return;
        }
        if(mListener == null) {
            Log.e(TAG, "start listener error");
            return;
        }
        mDecodeThread = new DecodeThread(mMediaCodec, mMediaExtractor,this);
        mDecodeThread.setListener(mListener);
        mDecodeThread.startDecode();
        mState = STATE_DECODING;
    }

    public synchronized void finishPause() {
        audioFinished = true;
        pause();
    }

    public boolean isAudioFinished() {
        return audioFinished;
    }

    public synchronized void pause() {
        if (mState != STATE_DECODING) {
            return;
        }
        mDecodeThread.pauseDecode();
        mState = STATE_PAUSING;
    }

    public synchronized void resume() {
        if (mState != STATE_PAUSING) {
            return;
        }
        mDecodeThread.resumeDecode(false);
        mState = STATE_DECODING;
    }

    public synchronized void replay() {
        if (mState != STATE_PAUSING) {
            return;
        }
        mDecodeThread.resumeDecode(true);
        audioFinished = false;
        mState = STATE_DECODING;
    }

    public void stop() {
        if(mState != STATE_DECODING && mState != STATE_PAUSING) {
            return;
        }
        mDecodeThread.stopDecode();
        mState = STATE_PREPARED;
    }

    public void release() {
        switch (mState) {
            case STATE_IDLE:
                break;
            case STATE_INITIALIZED:
                mPath = null;
                break;
            case STATE_PREPARED:
                mMediaExtractor.release();
                mMediaCodec.release();
                break;
            case STATE_PAUSING:
            case STATE_DECODING:
                stop();
                mMediaExtractor.release();
                mMediaCodec.release();
                break;
        }
        mState = STATE_IDLE;
    }

    private static class DecodeThread extends Thread {

        private MediaCodec mMediaCodec;
        private MediaExtractor mMediaExtractor;
        private volatile boolean mExtractFinish;
        private volatile boolean mStart;
        private volatile boolean mPause;
        private final Object mPauseLock;

        private OnAudioDecodeListener mListener;

        private long mVideoReferenceTime;
        private long mVideoReferencePts;
        private AudioDecoder mAudioDecoder;


        public DecodeThread(MediaCodec mMediaCodec, MediaExtractor mMediaExtractor, AudioDecoder audioDecoder) {
            this.mMediaCodec = mMediaCodec;
            this.mMediaExtractor = mMediaExtractor;
            this.mAudioDecoder = audioDecoder;
            mPauseLock = new Object();
        }

        public void pauseDecode() {
            synchronized (mPauseLock) {
                mPause = true;
            }
        }

        public synchronized void resumeDecode(boolean reset) {
            if (reset) {
                mExtractFinish = false;
                mMediaExtractor.seekTo(0, MediaExtractor.SEEK_TO_PREVIOUS_SYNC);
                mMediaCodec.flush();
            }
            mVideoReferencePts = 0;
            mVideoReferenceTime = 0;
            synchronized (mPauseLock) {
                if (mPause) {
                    mPause = false;
                    mPauseLock.notify();
                }
            }
        }

        public void startDecode() {
            mStart = true;
            start();
        }

        private void stopDecode() {
            mStart = false;
            mVideoReferencePts = 0;
            mVideoReferenceTime = 0;
            mAudioDecoder = null;
            resumeDecode(false);
            interrupt();
            try {
                join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        public void setListener(OnAudioDecodeListener listener) {
            this.mListener = listener;
        }

        @Override
        public void run() {
            ByteBuffer[] inputBuffers = mMediaCodec.getInputBuffers();
            ByteBuffer[] outputBuffers = mMediaCodec.getOutputBuffers();
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            while (mStart) {
                synchronized (mPauseLock) {
                    if (mPause) {
                        try {
                            mPauseLock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (!mExtractFinish) {
                    int inputBufferIndex = mMediaCodec.dequeueInputBuffer(12000);
                    if (inputBufferIndex >= 0) {
                        ByteBuffer inputBuffer = inputBuffers[inputBufferIndex];
                        inputBuffer.clear();
                        int size = mMediaExtractor.readSampleData(inputBuffer, 0);
                        long presentationTime = mMediaExtractor.getSampleTime();
                        mExtractFinish = !mMediaExtractor.advance();
                        if (!mExtractFinish) {
                            if (size >= 0) {
                                int sampleFlag = mMediaExtractor.getSampleFlags() > 0 ? mMediaExtractor.getSampleFlags() : 0;
                                mMediaCodec.queueInputBuffer(inputBufferIndex, 0, size, presentationTime, sampleFlag);
                            }
                        } else {
                            mMediaCodec.queueInputBuffer(inputBufferIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
                        }
                    }
                }
                int outputBufferIndex = mMediaCodec.dequeueOutputBuffer(bufferInfo, 12000);
                if (outputBufferIndex >= 0) {
                    if ((bufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) == 0) {
                        ByteBuffer outputBuffer = outputBuffers[outputBufferIndex];
                        //todo Listener
                        try {
                            processSyn(bufferInfo.presentationTimeUs);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (null != mListener) {
                            mListener.onAudioDecodeData(outputBuffer, bufferInfo);
                        }
                        mMediaCodec.releaseOutputBuffer(outputBufferIndex, false);
                        if ((bufferInfo.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
//                            if (mLooping) {
//                                //Seek to the start point
//                                mExtractFinish = false;
//                                mMediaExtractor.seekTo(0, MediaExtractor.SEEK_TO_PREVIOUS_SYNC);
//                                mMediaCodec.flush();
//                                if (mListener != null) {
//                                    mListener.onAudioDecodeFinish(false);
//                                }
//                            } else {
                                if (null != mAudioDecoder) {
                                    mAudioDecoder.finishPause();
                                }
                                if (mListener != null) {
                                    mListener.onAudioDecodeFinish(true);
                                }
//                            }
                        }
                    } else {
                        mMediaCodec.releaseOutputBuffer(outputBufferIndex, false);
                    }
                } else if(outputBufferIndex == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) {
                    outputBuffers = mMediaCodec.getOutputBuffers();
                } else if (outputBufferIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                    MediaFormat format = mMediaCodec.getOutputFormat();
                    if (mListener != null) {
                        mListener.onAudioFormatChanged(format);
                    }
                }
            }
        }

        private void processSyn(long pts) throws InterruptedException {
            if(mVideoReferencePts == 0 || mVideoReferenceTime == 0) {
                mVideoReferenceTime = System.nanoTime();
                mVideoReferencePts = pts;
                return;
            }
            long currentTime = System.nanoTime();
            long ptsGap = pts - mVideoReferencePts;

            long timeGap = currentTime - mVideoReferenceTime;
            long waitTime = (ptsGap - timeGap / 1000) / 1000;
            if(ptsGap == 0) {
                return;
            }
            if(waitTime < 0 || waitTime > 500) {
                mVideoReferenceTime = System.nanoTime();
                mVideoReferencePts = pts;
                return;
            } else if(waitTime == 0) {
                return;
            }
            sleep(waitTime);
        }
    }

}
