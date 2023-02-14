package com.rye.opengl.hockey;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.rye.opengl.R;
import com.rye.opengl.hockey.data.ColorShaderProgram;
import com.rye.opengl.hockey.data.Mallet;
import com.rye.opengl.hockey.data.Table;
import com.rye.opengl.hockey.data.TextureShaderProgram;
import com.rye.opengl.utils.TextureHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class SecondRenderer implements GLSurfaceView.Renderer {
    private final Context context;
    private Table table;
    private Mallet mallet;
    private TextureShaderProgram textureShaderProgram;
    private ColorShaderProgram colorShaderProgram;
    int textureId;

    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];

    //总矩阵
    private final float[] uMatrix = new float[16];


    public SecondRenderer(Context context) {
      this.context = context;
        table = new Table();
        mallet = new Mallet();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        textureShaderProgram = new TextureShaderProgram(context);
        colorShaderProgram = new ColorShaderProgram(context);
        textureId = TextureHelper.loadTexture(context, R.mipmap.air_hockey_surface);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        MatrixHelper.perspectiveM(projectionMatrix,45,(float) width/(float) height,1f,100f);
        Matrix.setIdentityM(modelMatrix,0);
        Matrix.translateM(modelMatrix, 0, 0f,0f,-2.5f);//平移
        Matrix.rotateM(modelMatrix,0,-30f,1f,0f,0f);//旋转

        Matrix.multiplyMM(uMatrix,0,  projectionMatrix,0,   modelMatrix,0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        textureShaderProgram.userProgram();
        textureShaderProgram.setUniforms(uMatrix,textureId);
        table.bindData(textureShaderProgram);
        table.draw();

        colorShaderProgram.userProgram();
        colorShaderProgram.setUniforms(uMatrix);
        mallet.bindData(colorShaderProgram);
        mallet.draw();
    }
}
