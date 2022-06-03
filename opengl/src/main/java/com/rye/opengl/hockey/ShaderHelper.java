package com.rye.opengl.hockey;

import android.opengl.GLES20;
import android.util.Log;

public class ShaderHelper {
    private static final String TAG = "ShaderHelper";

    public static int compileVertexShader(String shaderCode) {
        return compileShader(GLES20.GL_VERTEX_SHADER, shaderCode);
    }

    public static int compileFragmentShader(String shaderCode) {
        return compileShader(GLES20.GL_FRAGMENT_SHADER, shaderCode);
    }

    /**
     * 创建、加载、编译着色器
     *
     * @param type
     * @param shaderCode
     * @return
     */
    private static int compileShader(int type, String shaderCode) {
        //创建一个新的着色器对象
        final int shaderObjectId = GLES20.glCreateShader(type);
        if (shaderObjectId == 0) {
            //出错
            if (LoggerConfig.ON) {
                Log.w(TAG, "Could not create new shader.");
            }
            return 0;
        }
        //上传着色器源代码
        GLES20.glShaderSource(shaderObjectId, shaderCode);
        //编译着色器源代码
        GLES20.glCompileShader(shaderObjectId);

        final int[] compileStatus = new int[1];
        //获取着色器状态
        GLES20.glGetShaderiv(shaderObjectId, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
        //---这里可以打log
        if (compileStatus[0] == 0) {//出错
            GLES20.glDeleteShader(shaderObjectId);
            if (LoggerConfig.ON) {
                Log.w(TAG, "Compilation of shader failed.");
            }

            return 0;
        }
        return shaderObjectId;

    }

    /**
     * 链接着色器到OpenGL的程序-->OpenGL程序链接顶点和片段着色器
     *
     * @param vertexShaderId
     * @param fragmentShaderId
     * @return OpenGL程序ID
     */
    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {
        //新建程序对象，记录程序对象ID
        final int programObjectId = GLES20.glCreateProgram();
        if (programObjectId == 0) { //创建OpenGL项目出错
            if (LoggerConfig.ON) {
                Log.w(TAG, "Could not create new program");
            }
            return 0;
        }
        //将两个着色器附着在OpenGL程序上
        GLES20.glAttachShader(programObjectId, vertexShaderId);
        GLES20.glAttachShader(programObjectId, fragmentShaderId);
        //将两个着色器链接起来
        GLES20.glLinkProgram(programObjectId);

        final int[] linkStatus = new int[1];

        GLES20.glGetProgramiv(programObjectId, GLES20.GL_LINK_STATUS, linkStatus, 0);


        if (LoggerConfig.ON) {
            // 打印链接信息
            Log.v(TAG, "Results of linking program:\n" + GLES20.glGetProgramInfoLog(programObjectId));
        }

        if (linkStatus[0] == 0) {//连接出错
            GLES20.glDeleteProgram(programObjectId);
            return 0;
        }
        return programObjectId;
    }

    /**
     * 链接着色器到OpenGL上，方便上层调用
     *
     * @param vertexShaderSource
     * @param fragmentShaderSource
     * @return
     */
    public static int buildProgram(String vertexShaderSource, String fragmentShaderSource) {
        int programObjectId;
        int vertexShader = compileVertexShader(vertexShaderSource);
        int fragmentShader = compileFragmentShader(fragmentShaderSource);
        programObjectId = linkProgram(vertexShader, fragmentShader);
        //validateProgram
        if (!validateProgram(programObjectId)) {
            return 0;
        }
        return programObjectId;
    }

    /**
     * 检测OpenGL程序状态是不是有效的
     *
     * @param programObjectId
     * @return
     */
    public static boolean validateProgram(int programObjectId) {
        GLES20.glValidateProgram(programObjectId);
        final int[] validateStatus = new int[1];
        GLES20.glGetProgramiv(programObjectId, GLES20.GL_VALIDATE_STATUS, validateStatus, 0);
        //输出状态信息
        return validateStatus[0] != GLES20.GL_FALSE;
    }


}
