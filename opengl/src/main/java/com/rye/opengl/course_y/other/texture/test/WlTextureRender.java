package com.rye.opengl.course_y.other.texture.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.Log;

import com.rye.opengl.R;
import com.rye.opengl.course_y.CustomEglSurfaceView;

import com.rye.opengl.utils.ShaderHelper;
import com.rye.opengl.hockey.TextureResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class WlTextureRender implements CustomEglSurfaceView.CustomGLRender{

    private Context context;


    private float[] vertexData = {
            -1f, -1f,
            1f, -1f,
            -1f, 1f,
            1f, 1f
    };
    private FloatBuffer vertexBuffer;

    private float[] fragmentData = {
//            0f, 0f,
//            1f, 0f,
//            0f, 1f,
//            1f, 1f
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
    private int fboId;

    private int imgTextureId;

    private FboRender fboRender;

    private int umatrix;
    private float[] matrix = new float[16];

    private OnRenderCreateListener onRenderCreateListener;

    private int width;
    private int height;


    public WlTextureRender(Context context) {
        this.context = context;
        fboRender = new FboRender(context);
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

    public void setOnRenderCreateListener(OnRenderCreateListener onRenderCreateListener) {
        this.onRenderCreateListener = onRenderCreateListener;
    }

    @Override
    public void onSurfaceCreated() {

        fboRender.onCreate();
        String vertexSource = TextureResourceReader.readTextFileFromResource(context, R.raw.course_demo1_vertex_shader_m);
        String fragmentSource = TextureResourceReader.readTextFileFromResource(context, R.raw.course_demo1_fragment_shader);

        program = ShaderHelper.buildProgram(vertexSource, fragmentSource);//创建program并绑定着色器

        vPosition = GLES20.glGetAttribLocation(program, "v_Position");
        fPosition = GLES20.glGetAttribLocation(program, "f_Position");
        sampler = GLES20.glGetUniformLocation(program, "sTexture");
        umatrix = GLES20.glGetUniformLocation(program, "u_Matrix");


        int [] vbos = new int[1];
        GLES20.glGenBuffers(1, vbos, 0);
        vboId = vbos[0];

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboId);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexData.length * 4 + fragmentData.length * 4, null, GLES20. GL_STATIC_DRAW);
        GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, 0, vertexData.length * 4, vertexBuffer);
        GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, vertexData.length * 4, fragmentData.length * 4, fragmentBuffer);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

        int[] fbos = new int[1];
        GLES20.glGenBuffers(1, fbos, 0);
        fboId = fbos[0];
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fboId);


        int []textureIds = new int[1];
        GLES20.glGenTextures(1, textureIds, 0);
        textureid = textureIds[0];

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureid);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glUniform1i(sampler, 0);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, 720, 1280, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);
        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, textureid, 0);
        if(GLES20.glCheckFramebufferStatus(GLES20.GL_FRAMEBUFFER) != GLES20.GL_FRAMEBUFFER_COMPLETE)
        {
            Log.e("ywl5320", "fbo wrong");
        }
        else
        {
            Log.e("ywl5320", "fbo success");
        }

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);

        imgTextureId = loadTexrute(R.mipmap.my2);

        if(onRenderCreateListener != null)
        {
            onRenderCreateListener.onCreate(textureid);
        }
    }

    @Override
    public void onSurfaceChanged(int width, int height) {
       GLES20.glViewport(0, 0, width, height);
//        fboRender.onChange(width, height);
        this.width = width;
        this.height = height;


        width = 720;
        height = 1280;

        if(width > height)
        {
            Matrix.orthoM(matrix, 0, -width / ((height / 702f) * 526f),  width / ((height / 702f) * 526f), -1f, 1f, -1f, 1f);
        }
        else
        {
            Matrix.orthoM(matrix, 0, -1,  1, -height / ((width / 526f) * 702f), height / ((width / 526f) * 702f), -1f, 1f);
        }

        Matrix.rotateM(matrix, 0, 180, 1, 0, 0);
        Matrix.rotateM(matrix, 0, 180, 0, 0, 1);

    }

    @Override
    public void onDrawFrame() {

      //  GLES20.glViewport(0, 0, 720, 1280);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fboId);
        checkError();
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(1f,0f, 0f, 1f);

        GLES20.glUseProgram(program);
        GLES20.glUniformMatrix4fv(umatrix, 1, false, matrix, 0);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, imgTextureId);


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

        GLES20.glViewport(0, 0, width, height);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
        fboRender.onDraw(textureid);


        checkError();



    }
    private void checkError() {
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        int errorCode = GLES20.glGetError();
        if (errorCode != 0) {
            Log.i("RRye", "errorCode:" + errorCode);
        }
    }

    private int loadTexrute(int src)
    {
        int []textureIds = new int[1];
        GLES20.glGenTextures(1, textureIds, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureIds[0]);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), src);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        return  textureIds[0];

    }

    public interface OnRenderCreateListener
    {
        void onCreate(int textid);
    }


}