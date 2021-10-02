package com.rye.opengl.demos;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.rye.opengl.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

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
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f
    };
    private float[] aColorArray = {1.0f,1.0f,0.0f,1.0f};
    private static final int BYTE_SIZE = 4;
    private static final int POSITION_OFFSET = 3;
    private static final int STRIDE = BYTE_SIZE*POSITION_OFFSET;
    private  final int COORD_COUNT = positionArray.length/POSITION_OFFSET;
    private FloatBuffer vertexData = ByteBuffer.allocateDirect(positionArray.length * BYTE_SIZE)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer();

    private int loadShader(int shader, int type) {
        GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, readTextFileFromResource(mContext, shader));
        GLES20.glCompileShader(shader);
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

        aPosition = GLES20.glGetAttribLocation(mProgram, "vPosition");
        aColor = GLES20.glGetUniformLocation(mProgram, "vColor");
        vertexData.put(positionArray);
        vertexData.position(0);
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
          GLES20.glClearColor(1.0f,1.0f,1.0f,1.0f);
          GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
          GLES20.glUseProgram(mProgram);
          GLES20.glEnableVertexAttribArray(aPosition);
          GLES20.glUniform4fv(aColor,1,aColorArray,0);
          GLES20.glVertexAttribPointer(aPosition,POSITION_OFFSET,GLES20.GL_FLOAT,false,
                 STRIDE,vertexData);

          GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,COORD_COUNT);
          GLES20.glDisableVertexAttribArray(aPosition);
    }
}
