package com.rye.opengl.hockey.data;

import android.content.Context;
import android.opengl.GLES20;

import com.rye.opengl.R;

/**
 * 颜色着色器
 */
public class ColorShaderProgram extends ShaderProgram {

    protected static final String U_MATRIX = "u_Matrix";
    public final int uMatrixLocation;

    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    public final int aPositionLocation;
    public final int aColorLocation;

    public ColorShaderProgram(Context context) {
        super(context, R.raw.simple_vertex_shader, R.raw.simple_fragment_shader);

        uMatrixLocation = GLES20.glGetUniformLocation(programId, U_MATRIX);

        aColorLocation = GLES20.glGetAttribLocation(programId, A_COLOR);
        aPositionLocation = GLES20.glGetAttribLocation(programId, A_POSITION);
    }

    public void setUniforms(float[] matrix){
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
    }

}
