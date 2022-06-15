package com.rye.opengl.course_y.other.texture;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.rye.opengl.course_y.CustomEglSurfaceView;

/**
 * Create by  [Rye]
 * <p>
 * at 2022/5/29 4:08 下午
 */
public class GLTextureView extends CustomEglSurfaceView {
     private TextureRender mTextureRender;

    public GLTextureView(Context context) {
        this(context,null);
    }

    public GLTextureView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GLTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mTextureRender = new TextureRender(context);
        setGLRender(mTextureRender);
    }

    public TextureRender getTextureRender() {

        return mTextureRender;
    }
}
