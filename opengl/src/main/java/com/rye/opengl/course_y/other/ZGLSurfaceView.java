package com.rye.opengl.course_y.other;

import android.content.Context;
import android.util.AttributeSet;

import com.rye.opengl.course_y.CustomEglSurfaceView;
import com.rye.opengl.course_y.other.glsurfaceview.ZgRenderer;

/**
 * Create by  [Rye]
 * <p>
 * at 2022/5/29 11:59 上午
 */
public class ZGLSurfaceView extends CustomEglSurfaceView {
    public ZGLSurfaceView(Context context) {
        this(context,null);
    }

    public ZGLSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public ZGLSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setGLRender(new ZgRenderer());
        //设置为脏模式，手动刷新，可不设置使用自动刷新
        setRenderMode(CustomEglSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
