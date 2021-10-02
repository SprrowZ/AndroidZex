package com.rye.opengl.hockey.data;

import android.opengl.GLES20;

/**
 * 木槌类
 */
public class Mallet {
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int COLOR_COMPONENT_COUNT = 3;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT+COLOR_COMPONENT_COUNT)* Constants.BYTES_PER_FLOAT;

    private static final float[] VERTEX_DATA = {
            // 两个木槌的质点位置
            0f,   -0.4f,  0f,0f,0f,
            0f,    0.4f,  0f,0f,0f,
    };

    private final VertexArray vertexArray;

    public Mallet(){
        vertexArray = new VertexArray(VERTEX_DATA);
    }

    public void bindData(ColorShaderProgram shaderProgram){
        vertexArray.setVertexAttribPointer(
                shaderProgram.aPositionLocation,
                POSITION_COMPONENT_COUNT,
                STRIDE,
                0);

        vertexArray.setVertexAttribPointer(
                shaderProgram.aColorLocation,
                COLOR_COMPONENT_COUNT,
                STRIDE,
                POSITION_COMPONENT_COUNT
        );
    }

    public void draw(){
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0,2);
    }
}
