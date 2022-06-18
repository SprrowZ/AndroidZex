package com.rye.opengl.course_y.other.texture;

import android.content.Context;
import android.util.AttributeSet;

import com.rye.opengl.course_y.CustomEglSurfaceView;
import com.rye.opengl.course_y.other.render.GLMultiplyRender;

/**
 * Create by  [Rye]
 * <p>
 * at 2022/6/12 2:57 下午
 * 将同一纹理渲染至多Surface
 */
public class GLMultiplyTextureView extends CustomEglSurfaceView {

    private GLMultiplyRender glMultiplyRender;

    public GLMultiplyTextureView(Context context) {
        this(context, null);
    }

    public GLMultiplyTextureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GLMultiplyTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        glMultiplyRender = new GLMultiplyRender(context);
        setGLRender(glMultiplyRender);
    }

    public void setTextureId(int textureId,int index) {
        if (glMultiplyRender != null) {
            glMultiplyRender.setTextureId(textureId,index);
        }
    }

}
