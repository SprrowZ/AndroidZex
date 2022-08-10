package com.dawn.zgstep.player.media.localplay.decode;

import android.media.MediaCodec;
import android.media.MediaFormat;

import java.nio.ByteBuffer;

public interface OnAudioDecodeListener {

    void onAudioDecodeData(ByteBuffer bb, MediaCodec.BufferInfo bi);
    void onAudioDecodeFinish(boolean confirmation);
    void onAudioFormatChanged(MediaFormat mediaFormat);

}
