package com.dawn.zgstep.player.media.localplay.ecode;

import android.media.MediaCodec;
import android.media.MediaFormat;

import java.nio.ByteBuffer;

public interface OnAudioEncodeListener {
    void onAudioEncode(ByteBuffer bb, MediaCodec.BufferInfo bi);
    void onAudioFormatChange(MediaFormat format);
}
