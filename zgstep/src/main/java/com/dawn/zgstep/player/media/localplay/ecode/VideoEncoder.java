package com.dawn.zgstep.player.media.localplay.ecode;


import static com.dawn.zgstep.player.media.localplay.util.MediaUtil.VIDEO_MIME;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.view.Surface;


import com.dawn.zgstep.player.media.localplay.util.MediaUtil;

import java.io.IOException;
import java.nio.ByteBuffer;

public class VideoEncoder {

    private MediaCodec mMediaCodec;
    private Surface mEncodeSurface;
    private EncodeThread mEncodeThread;

    public VideoEncoder(MediaFormat mediaFormat) throws IOException {
        mMediaCodec = MediaCodec.createEncoderByType(VIDEO_MIME);
        mMediaCodec.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
        mEncodeSurface = mMediaCodec.createInputSurface();
        mEncodeThread = new EncodeThread(mMediaCodec, mEncodeSurface);
    }

    public VideoEncoder(VideoConfiguration videoConfiguration) throws IOException {
        mMediaCodec = MediaUtil.getVideoMediaCodec(videoConfiguration);
        mEncodeSurface = mMediaCodec.createInputSurface();
        mEncodeThread = new EncodeThread(mMediaCodec, mEncodeSurface);
    }

    public Surface getEncodeSurface() {
        return mEncodeSurface;
    }

    public void setOnVideoEncodeListener(OnVideoEncodeListener listener) {
        mEncodeThread.setListener(listener);
    }

    public void start() {
        mMediaCodec.start();
        mEncodeThread.start();
    }

    public void pause() {

    }

    public void resume() {

    }

    public void signalEnd() {
        mMediaCodec.signalEndOfInputStream();
    }

    public void stop() {
        mEncodeThread.setInterrupted();
        try {
            mEncodeThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class EncodeThread extends Thread {
        private MediaCodec mediaCodec;
        private MediaCodec.BufferInfo bufferInfo;
        private OnVideoEncodeListener listener;
        private volatile boolean interrupted;
        private Surface mEncodeSurface;

        EncodeThread(MediaCodec mediaCodec, Surface surface) {
            this.mediaCodec = mediaCodec;
            bufferInfo = new MediaCodec.BufferInfo();
            mEncodeSurface = surface;
        }

        void setListener(OnVideoEncodeListener listener) {
            this.listener = listener;
        }

        void setInterrupted() {
            this.interrupted = true;
        }

        @Override
        public void run() {
            ByteBuffer[] outBuffers = mediaCodec.getOutputBuffers();
            while (!interrupted){
                int outBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, 12000);
                if (outBufferIndex >= 0){
                    if ((bufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG)!= 0) {
                        mediaCodec.releaseOutputBuffer(outBufferIndex, false);
                        continue;
                    }
                    if ((bufferInfo.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
                        mediaCodec.releaseOutputBuffer(outBufferIndex, false);
                        if(listener != null) {
                            listener.onVideoEncodeFinish();
                        }
                        break;
                    }
                    ByteBuffer bb = outBuffers[outBufferIndex];
                    if (listener != null) {
                        listener.onVideoEncodeData(bb, bufferInfo);
                    }
                    mediaCodec.releaseOutputBuffer(outBufferIndex, false);
                } else {
                    if(outBufferIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                        MediaFormat format = mediaCodec.getOutputFormat();
                        if(listener != null) {
                            listener.onVideoFormatChange(format);
                        }
                    }
                }
            }
        }
    }
}
