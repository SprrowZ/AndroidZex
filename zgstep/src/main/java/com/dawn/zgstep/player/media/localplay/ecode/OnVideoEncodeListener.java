package com.dawn.zgstep.player.media.localplay.ecode;

import android.media.MediaCodec;
import android.media.MediaFormat;

import java.nio.ByteBuffer;

public interface OnVideoEncodeListener {

    void onVideoFormatChange(MediaFormat mediaFormat);
    void onVideoEncodeData(ByteBuffer bb, MediaCodec.BufferInfo bi);
    void onVideoEncodeFinish();

}
