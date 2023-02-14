package com.rye.opengl.course_y.other.texture.test;

import android.content.Context;
import android.opengl.GLES20;

import com.rye.opengl.R;
import com.rye.opengl.course_y.CustomEglSurfaceView;
import com.rye.opengl.utils.ShaderHelper;
import com.rye.opengl.hockey.TextureResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class WlMutiRender implements CustomEglSurfaceView.CustomGLRender{


    private Context context;

    private float[] vertexData = {
            -1f, -1f,
            1f, -1f,
            -1f, 1f,
            1f, 1f
    };
    private FloatBuffer vertexBuffer;

    private float[] fragmentData = {
            0f, 1f,
            1f, 1f,
            0f, 0f,
            1f, 0f
    };
    private FloatBuffer fragmentBuffer;

    private int program;
    private int vPosition;
    private int fPosition;
    private int textureid;
    private int sampler;

    private int vboId;

    private int textureId;

    private int index;

    public void setTextureId(int textureId, int index) {
        this.textureId = textureId;
        this.index = index;
    }

    public WlMutiRender(Context context) {
        this.context = context;

        vertexBuffer = ByteBuffer.allocateDirect(vertexData.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData);
        vertexBuffer.position(0);

        fragmentBuffer = ByteBuffer.allocateDirect(fragmentData.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(fragmentData);
        fragmentBuffer.position(0);

    }

    @Override
    public void onSurfaceCreated() {
        String vertexSource = TextureResourceReader.readTextFileFromResource(context, R.raw.course_demo1_vertex_shader);
        String fragmentSource = TextureResourceReader.readTextFileFromResource(context, R.raw.course_demo1_fragment_shader);

        if(index == 0)
        {
            fragmentSource = TextureResourceReader.readTextFileFromResource(context, R.raw.course_demo1_fragment_shader);
        }
//        else if(index == 1)
//        {
//            fragmentSource = WlShaderUtil.getRawResource(context, R.raw.fragment_shader2);
//        }
//        else if(index == 2)
//        {
//            fragmentSource = WlShaderUtil.getRawResource(context, R.raw.fragment_shader3);
//        }


        program = ShaderHelper.buildProgram(vertexSource, fragmentSource);//创建program并绑定着色器

        vPosition = GLES20.glGetAttribLocation(program, "v_Position");
        fPosition = GLES20.glGetAttribLocation(program, "f_Position");
        sampler = GLES20.glGetUniformLocation(program, "sTexture");

        int [] vbos = new int[1];
        GLES20.glGenBuffers(1, vbos, 0);
        vboId = vbos[0];

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboId);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexData.length * 4 + fragmentData.length * 4, null, GLES20. GL_STATIC_DRAW);
        GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, 0, vertexData.length * 4, vertexBuffer);
        GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, vertexData.length * 4, fragmentData.length * 4, fragmentBuffer);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }

    @Override
    public void onSurfaceChanged(int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame() {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(1f,0f, 0f, 1f);

        GLES20.glUseProgram(program);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);


        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboId);

        GLES20.glEnableVertexAttribArray(vPosition);
        GLES20.glVertexAttribPointer(vPosition, 2, GLES20.GL_FLOAT, false, 8,
                0);

        GLES20.glEnableVertexAttribArray(fPosition);
        GLES20.glVertexAttribPointer(fPosition, 2, GLES20.GL_FLOAT, false, 8,
                vertexData.length * 4);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }
}
