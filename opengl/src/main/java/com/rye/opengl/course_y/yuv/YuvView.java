package com.rye.opengl.course_y.yuv;

import android.content.Context;
import android.util.AttributeSet;

import com.rye.opengl.course_y.CustomEglSurfaceView;

/**
 * Create by  [Rye]
 * <p>
 * at 2023/5/5 11:33
 */
public class YuvView extends CustomEglSurfaceView {
    private YuvRender yuvRender;

    public YuvView(Context context) {
        this(context, null);
    }

    public YuvView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public YuvView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        yuvRender = new YuvRender(context);
        setGLRender(yuvRender);
        setRenderMode(CustomEglSurfaceView.RENDERMODE_WHEN_DIRTY); //手动渲染
    }

    public void setFrameData(int w, int h, byte[] by, byte[] bu, byte[] bv) {
        if (yuvRender != null) {
            yuvRender.setFrameData(w, h, by, bu, bv);
            wakeUpRender();
        }
    }
}
