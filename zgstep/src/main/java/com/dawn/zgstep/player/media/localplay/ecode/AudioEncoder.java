package com.dawn.zgstep.player.media.localplay.ecode;


import static com.dawn.zgstep.player.media.localplay.util.MediaUtil.AUDIO_MIME;

import android.media.MediaCodec;
import android.media.MediaFormat;


import com.dawn.zgstep.player.media.localplay.util.MediaUtil;

import java.io.IOException;
import java.nio.ByteBuffer;

public class AudioEncoder {


    private MediaCodec mMediaCodec;
    private OnAudioEncodeListener mListener;
    private MediaCodec.BufferInfo mBufferInfo = new MediaCodec.BufferInfo();

    public AudioEncoder(MediaFormat format) throws IOException {
        mMediaCodec = MediaCodec.createEncoderByType(AUDIO_MIME);
        mMediaCodec.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
    }

    public AudioEncoder(AudioConfiguration audioConfiguration) throws IOException {
        mMediaCodec = MediaUtil.getAudioMediaCodec(audioConfiguration);
    }

    public void setOnAudioEncodeListener(OnAudioEncodeListener listener) {
        this.mListener = listener;
    }

    public synchronized void start() throws Exception{
        mMediaCodec.start();
    }

    public synchronized void pause() throws Exception {

    }

    public synchronized void resume() throws Exception {

    }

    public synchronized void stop() {
        if (mMediaCodec != null) {
            mMediaCodec.stop();
            mMediaCodec.release();
            mMediaCodec = null;
        }
    }

    public synchronized void offerEncoder(ByteBuffer buffer, long ptsUs) {
        if(mMediaCodec == null) {
            return;
        }
        ByteBuffer[] inputBuffers = mMediaCodec.getInputBuffers();
        ByteBuffer[] outputBuffers = mMediaCodec.getOutputBuffers();
        int inputBufferIndex = mMediaCodec.dequeueInputBuffer(12000);
        if (inputBufferIndex >= 0) {
            ByteBuffer inputBuffer = inputBuffers[inputBufferIndex];
            inputBuffer.clear();
            int count = buffer.remaining();
            inputBuffer.put(buffer);
            mMediaCodec.queueInputBuffer(inputBufferIndex, 0, count, ptsUs, 0);
        }

        int outputBufferIndex = mMediaCodec.dequeueOutputBuffer(mBufferInfo, 12000);
        if(outputBufferIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
            MediaFormat format = mMediaCodec.getOutputFormat();
            if(mListener != null) {
                mListener.onAudioFormatChange(format);
            }
        }
        while (outputBufferIndex >= 0) {
            ByteBuffer outputBuffer = outputBuffers[outputBufferIndex];
            if(mListener != null) {
                mListener.onAudioEncode(outputBuffer, mBufferInfo);
            }
            mMediaCodec.releaseOutputBuffer(outputBufferIndex, false);
            outputBufferIndex = mMediaCodec.dequeueOutputBuffer(mBufferInfo, 0);
        }
    }

    /**
     * 给编码出的aac裸流添加adts头字段
     *
     * @param packet    要空出前7个字节，否则会搞乱数据
     * @param packetLen
     */
    public static void addADTStoPacket(byte[] packet, int packetLen) {
        int profile = 2;  //AAC LC
        int freqIdx = 4;  //44.1KHz
        int chanCfg = 2;  //CPE
        packet[0] = (byte) 0xFF;
        packet[1] = (byte) 0xF9;
        packet[2] = (byte) (((profile - 1) << 6) + (freqIdx << 2) + (chanCfg >> 2));
        packet[3] = (byte) (((chanCfg & 3) << 6) + (packetLen >> 11));
        packet[4] = (byte) ((packetLen & 0x7FF) >> 3);
        packet[5] = (byte) (((packetLen & 7) << 5) + 0x1F);
        packet[6] = (byte) 0xFC;
    }

}
