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

public class TriangleRenderer implements GLSurfaceView.Renderer {
    private Context mContext;
    private int mProgram;//openGL项目
    //顶点坐标
    private float triangleCoords[] = {
            0.5f, 0.5f, 0f,       //顶点
            -0.5f, -0.5f, -0.5f,  //左侧点
            0.5f, -0.5f, 0f       //右侧点
    };
    //绘制颜色
    private float color[] = {0.0f, 0.0f, 1.0f, 1.0f};//蓝色
    //偏移量(即几个变量代表一个点)
    private static final int COORDS_PER_VERTEX = 3;
    private static final int BYTE_PER_VERTEX = 4;
    //顶点个数
    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    //顶点之间的偏移量
    private final int vertexStride = COORDS_PER_VERTEX * BYTE_PER_VERTEX;

    private FloatBuffer vertexData = ByteBuffer.allocateDirect(triangleCoords.length * BYTE_PER_VERTEX)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer();
    //着色器位置信息
    private int aPosition;
    //着色器颜色信息
    private int aColor;

    public TriangleRenderer(Context context) {
        this.mContext = context;
    }

    /**
     * 创建、加载、编译着色器
     *
     * @param type
     * @param shaderResource
     */
    private int loadShader(int type, int shaderResource) {
        //根据type创建顶点或片段着色器
        int shader = GLES20.glCreateShader(type);
        //将资源加载到着色器中
        GLES20.glShaderSource(shader, readTextFileFromResource(mContext, shaderResource));
        //编译着色器
        GLES20.glCompileShader(shader);
        return shader;
    }

    private void loadShader(int type, String shaderContent) {
    }

    /**
     * 从原生文件读入glsl
     *
     * @param context
     * @param resourceId
     * @return
     */
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

    /**
     * 可以放在构造函数中或者onSurfaceCreated中
     */
    private void init() {
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, R.raw.triangle_vertex_shader);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, R.raw.triangle_fragment_shader);
        //创建项目
        mProgram = GLES20.glCreateProgram();
        //将着色器附着到项目中
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        //连接到着色器程序
        GLES20.glLinkProgram(mProgram);

        //获取着色器中位置、颜色句柄
        aPosition = GLES20.glGetAttribLocation(mProgram, "vPosition"/*要与顶点着色器中声明的相同*/);
        aColor = GLES20.glGetUniformLocation(mProgram, "vColor");
        //将位置信息存入byteBuffer中
        vertexData.put(triangleCoords);
        vertexData.position(0);
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        init();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        //将程序加入到OpenGL ES 2.0环境中
        GLES20.glUseProgram(mProgram);

        //启用三角形顶点句柄
        GLES20.glEnableVertexAttribArray(aPosition);
        //准备三角形坐标数据，为glDrawArrays做准备
        GLES20.glVertexAttribPointer(aPosition, COORDS_PER_VERTEX, GLES20.GL_FLOAT,
                false, vertexStride, vertexData);
        //设置三角形的颜色
        GLES20.glUniform4fv(aColor, 1, color, 0);
        //设置好坐标数据和颜色后，开始绘制三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        //禁用顶点数组句柄
        GLES20.glDisableVertexAttribArray(aPosition);
    }
}
