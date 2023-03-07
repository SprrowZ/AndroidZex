package com.dawn.zgstep.player.media.localplay;

import android.media.MediaCodec;
import android.media.MediaFormat;

import java.nio.ByteBuffer;

public interface OnLocalMediaListener {

    void onVideoFormatChange(MediaFormat format, boolean formatChange);
    void onAudioFormatChange(MediaFormat format);

    void onVideoDataUpdate(long pts);
    void onAudioDataUpdate(ByteBuffer bb, MediaCodec.BufferInfo bi);

    void onAudioFinish(boolean confirmation);
    void onVideoFinish();
}
