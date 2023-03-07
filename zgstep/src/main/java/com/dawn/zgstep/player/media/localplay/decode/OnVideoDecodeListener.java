package com.dawn.zgstep.player.media.localplay.decode;

import android.media.MediaFormat;

public interface OnVideoDecodeListener {

    void onVideoDecodeData(long pts);
    void onVideoDecodeFinish();
    void onVideoFormatChanged(MediaFormat mediaFormat);
}
