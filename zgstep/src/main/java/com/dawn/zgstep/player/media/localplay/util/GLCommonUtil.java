package com.dawn.zgstep.player.media.localplay.util;

import android.graphics.Bitmap;
import android.opengl.EGL14;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;


public class GLCommonUtil {

    private static final String TAG = "GlCommonUtil";
    public static final int NO_TEXTURE = -1;

    /**
     * 根据顶点shader和片元shader创建program
     * @param vertexSource 顶点shader
     * @param fragmentSource 片元shader
     * @return programId
     */
    public static int createProgram(String vertexSource, String fragmentSource){
        int vs = loadShader(GLES20.GL_VERTEX_SHADER,vertexSource);
        int fs = loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentSource);
        int program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program,vs);
        GLES20.glAttachShader(program,fs);
        GLES20.glLinkProgram(program);

        int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS,linkStatus,0);
        if (linkStatus[0] != GLES20.GL_TRUE) {
            Log.e(TAG, "Could not link program:");
            Log.e(TAG, GLES20.glGetProgramInfoLog(program));
            GLES20.glDeleteProgram(program);
            program = 0;
        }
        return program;
    }

    /**
     * 根据类别加载shader
     * @param shaderType shader类别
     * @param source shader内容
     * @return shaderId
     */
    public static int loadShader(int shaderType, String source){
        int shader = GLES20.glCreateShader(shaderType);
        GLES20.glShaderSource(shader,source);
        GLES20.glCompileShader(shader);

        int[] compiled = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS,compiled,0);
        if (compiled[0] == 0) {
            Log.e(TAG, "Could not compile shader(TYPE=" + shaderType + "):");
            Log.e(TAG, GLES20.glGetShaderInfoLog(shader));
            GLES20.glDeleteShader(shader);
            shader = 0;
        }
        return shader;
    }

    /**
     * 检查OpenGl错误
     * @param op 错误信息
     */
    public static void checkGlError(String op){
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, op + ": glGetError: 0x" + Integer.toHexString(error));
            return;
        }
    }

    /**
     * 检查Egl错误
     * @param op 错误信息
     */
    public static void checkEglError(String op) {
        int error;
        while ((error = EGL14.eglGetError()) != EGL14.EGL_SUCCESS) {
            Log.e(TAG, op + ": eglGetError: 0x" + Integer.toHexString(error));
            throw new RuntimeException("eglGetError encountered (see log)");
        }
    }

    /**
     * 删除纹理
     * @param textureId 纹理id
     */
    public static void deleteGLTexture(int textureId) {
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        int[] aTextures = new int[]{textureId};
        GLES20.glDeleteTextures(1, aTextures, 0);
    }

    /**
     * 将bitmap绑定纹理
     * @param img bitmap图片
     * @param usedTexId 纹理id
     * @param recycle 是否recycle bitmap
     * @return 绑定后的纹理id
     */
    public static int loadTexture(final Bitmap img, final int usedTexId, final boolean recycle){
        int textures[] = new int[1];
        if (usedTexId == NO_TEXTURE) {
            GLES20.glGenTextures(1, textures, 0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                    GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                    GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                    GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                    GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, img, 0);
        }else {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, usedTexId);
            GLUtils.texSubImage2D(GLES20.GL_TEXTURE_2D, 0, 0, 0, img);
            textures[0] = usedTexId;
        }
        if (recycle) {
            img.recycle();
        }
        return textures[0];
    }

    public static int createOESTextureId(){
        int[] textures = new int[1];
        checkGlError("create OES Texture");
        GLES20.glGenTextures(1, textures, 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE16);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textures[0]);

        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MAG_FILTER,
                GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_S,
                GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_T,
                GLES20.GL_CLAMP_TO_EDGE);
        checkGlError("glTexParameter");
        return textures[0];
    }

    public static int createTexture2d() {
        int[] texId  = new int[]{0};
        GLES20.glGenTextures(1, texId, 0);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId[0]);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        return texId[0];
    }
}
