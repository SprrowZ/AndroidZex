package com.rye.opengl.course_y.oes.encodec;

import android.content.Context;

public class MediaEncoder extends BaseMediaEncoder {
    private EncoderRender mEncoderRender;
    public MediaEncoder(Context context,int textureId) {
        super(context);
        mEncoderRender = new EncoderRender(context,textureId);
        setRender(mEncoderRender);
        setRenderMode(BaseMediaEncoder.RENDERMODE_CONTINUOUSLY);
    }
}
