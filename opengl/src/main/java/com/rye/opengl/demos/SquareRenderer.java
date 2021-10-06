package com.rye.opengl.demos;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.rye.opengl.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 四方形，加入校验机制
 */
public class SquareRenderer implements GLSurfaceView.Renderer {
    private Context mContext;
    private int aPosition;
    private int aColor;

    private int mProgram;
    private int mVertexShader;
    private int mFragmentShader;

    public SquareRenderer(Context context) {
        this.mContext = context;
    }

    private float[] positionArray = {
            -0.5f, 0.5f, 0.0f,
            0.5f, 0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f
    };
    //顶点索引，指定glDrawElements绘制的方向
    private short[] indices = {0, 1, 2, 0, 2, 3};

    private float[] aColorArray = {1.0f, 1.0f, 0.0f, 1.0f};

    private static final int BYTE_SIZE = 4;
    private static final int POSITION_OFFSET = 3;
    private static final int STRIDE = BYTE_SIZE * POSITION_OFFSET;
    private final int COORD_COUNT = positionArray.length / POSITION_OFFSET;

    private FloatBuffer vertexData = ByteBuffer.allocateDirect(positionArray.length * BYTE_SIZE)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer();

    private ShortBuffer indicesBuffer = ByteBuffer.allocateDirect(indices.length * 2)
            .order(ByteOrder.nativeOrder())
            .asShortBuffer();

    private int loadShader(int shaderResource, int type) {
       int shader =  GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, readTextFileFromResource(mContext, shaderResource));
        GLES20.glCompileShader(shader);
        //判断着色器是否有问题
        int[] compiled = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
        if (compiled[0] == 0) {
            Log.e("Rye", "create shader error:" + GLES20.glGetShaderInfoLog(shader));
            GLES20.glDeleteShader(shader);
            shader = 0;
        }
        return shader;
    }

    public static String readTextFileFromResource(Context context, int resourceId) {
        StringBuilder body = new StringBuilder();

        try {
            InputStream inputStream = context.getResources().openRawResource(resourceId);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String nextLine;

            while ((nextLine = bufferedReader.readLine()) != null) {
                body.append(nextLine);
                body.append('\n');
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body.toString();
    }

    private void init() {
        mProgram = GLES20.glCreateProgram();
        mVertexShader = loadShader(R.raw.square_vertex_shader, GLES20.GL_VERTEX_SHADER);
        mFragmentShader = loadShader(R.raw.square_fragment_shader, GLES20.GL_FRAGMENT_SHADER);

        GLES20.glAttachShader(mProgram, mVertexShader);
        GLES20.glAttachShader(mProgram, mFragmentShader);
        GLES20.glLinkProgram(mProgram);
        //校验是否link失败
        checkLinkStatus();

        aPosition = GLES20.glGetAttribLocation(mProgram, "vPosition");
        aColor = GLES20.glGetUniformLocation(mProgram, "vColor");

        vertexData.put(positionArray);
        vertexData.position(0);

        indicesBuffer.put(indices);
        indicesBuffer.position(0);
    }

    private void checkLinkStatus() {
        int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(mProgram, GLES20.GL_LINK_STATUS, linkStatus, 0);
        if(linkStatus[0]!= GLES20.GL_TRUE){
            Log.e("Rye","Could not link program:"+ GLES20.glGetProgramInfoLog(mProgram));
            GLES20.glDeleteProgram(mProgram);
            mProgram=0;
        }
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        init();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        GLES20.glGetError();
        GLES20.glUseProgram(mProgram);
        GLES20.glGetError();
        GLES20.glEnableVertexAttribArray(aPosition);
        GLES20.glUniform4fv(aColor, 1, aColorArray, 0);
        GLES20.glVertexAttribPointer(aPosition, POSITION_OFFSET, GLES20.GL_FLOAT, false,
                STRIDE, vertexData);
        GLES20.glGetError();
        //三角形绘制方法
        //        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, vertexCount);

        //第二个参数，指定绘制点的个数，很明显，这里是6个
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_SHORT, indicesBuffer);
        GLES20.glDisableVertexAttribArray(aPosition);
    }
}
