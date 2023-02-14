package com.rye.opengl.hockey.data;

import android.content.Context;
import android.opengl.GLES20;

import com.rye.opengl.utils.ShaderHelper;
import com.rye.opengl.hockey.TextureResourceReader;

public class ShaderProgram {

    protected final int programId;

    public ShaderProgram(String vertexShaderResourceStr,
                         String fragmentShaderResourceStr){
        programId = ShaderHelper.buildProgram(
                vertexShaderResourceStr,
                fragmentShaderResourceStr);
    }

    public ShaderProgram(Context context, int vertexShaderResourceId,
                         int fragmentShaderResourceId){
        programId = ShaderHelper.buildProgram(
                TextureResourceReader.readTextFileFromResource(context,vertexShaderResourceId),
                TextureResourceReader.readTextFileFromResource(context,fragmentShaderResourceId));
    }

    public void userProgram() {
        GLES20.glUseProgram(programId);
    }

    public int getShaderProgramId() {
        return programId;
    }

    public void deleteProgram() {
        GLES20.glDeleteProgram(programId);
    }
}
