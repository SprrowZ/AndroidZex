package com.rye.opengl.course_y.other.glsurfaceview;

import android.opengl.GLES20;

import com.rye.opengl.course_y.CustomEglSurfaceView;

/**
 * Create by  [Rye]
 * <p>
 * at 2022/5/29 12:01 下午
 */
public class ZgRenderer implements CustomEglSurfaceView.CustomGLRender {

    public ZgRenderer() {

    }

    @Override
    public void onSurfaceCreated() {

    }

    @Override
    public void onSurfaceChanged(int width, int height) {
        GLES20.glViewport(0,0,width,height);
    }

    @Override
    public void onDrawFrame() {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(1.0f,1.0f,0.0f,1.0f);
    }
}
