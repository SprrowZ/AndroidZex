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
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Create by  [Rye]
 * <p>
 * at 2021/10/2 2:16 下午
 */
public class CircleRenderer implements GLSurfaceView.Renderer {
    //将圆划分成360个三角形
    private final int VERTEX_DATA_NUM = 360;
    private final int FLOAT_BYTE_PER = 4;
    //几个坐标点代表一个顶点
    private final int VERTEX_PER_SIZE = 2;
    //360个三角形顶点，每个顶点两个数据：x轴、y轴；再加中心点 & 闭合点
    private float[] circleVertex;
    //每个三角形的弧度
    private float radian = (float) (2 * Math.PI / VERTEX_DATA_NUM);

    private float radius = 0.8f;


    private Context mContext;

    private int mProgram;
    private int mVertexShader;
    private int mFragmentShader;
    private int aPosition;
    private int aColor;
    //纯白色
    private float[] paintColor = new float[]{1.0f, 1.0f, 0.0f, 1.0f};

    private final int STRIDE = VERTEX_PER_SIZE * FLOAT_BYTE_PER;
    private FloatBuffer vertexData ;


    public CircleRenderer(Context context) {
        this.mContext = context;
    }

    /**
     * 填充圆形顶点数据
     */
    private float[] cretePositions() {
//        //中心点数据，这里选取原点坐标
//        circleVertex[0] = 0.0f;
//        circleVertex[1] = 0.0f;
//
//        for (int i = 0; i < VERTEX_DATA_NUM; i++) {
//            //加2是因为上面先塞了个中心点
//            circleVertex[2 * i + 2] = (float) (radius * Math.cos(radian * i));
//            circleVertex[2 * i + 2 + 1] = (float) (radius * Math.sin(radian * i));
//        }
//        //闭合点数据
//        circleVertex[2 * VERTEX_DATA_NUM + 2] = (float) (radius * Math.cos(radian));
//        circleVertex[2 * VERTEX_DATA_NUM + 2 + 1] = (float) (radius * Math.cos(radian));
        ArrayList<Float> posData = new ArrayList<>();
        //圆心位置
        posData.add(0.0f);
        posData.add(0.0f);
        float perAngelSpan = 360f / VERTEX_DATA_NUM; //每个三角形所占角度
        for (int i = 0; i < 360 + perAngelSpan; i += perAngelSpan) {
            posData.add((float) (radius * Math.sin(i * Math.PI / 180F)));
            posData.add((float) (radius * Math.cos(i * Math.PI / 180F)));
        }
        float[] resultData = new float[posData.size()];
        for (int i = 0; i < resultData.length; i++) {
            resultData[i] = posData.get(i);
        }
        return resultData;
    }

    private int loadShader(int type, int source) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, readTextFileFromResource(mContext, source));
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

    private void initData() {
        mProgram = GLES20.glCreateProgram();
        mVertexShader = loadShader(GLES20.GL_VERTEX_SHADER, R.raw.circle_vertex_shader);
        mFragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, R.raw.circle_fragment_shader);
        GLES20.glAttachShader(mProgram, mVertexShader);
        GLES20.glAttachShader(mProgram, mFragmentShader);
        GLES20.glLinkProgram(mProgram);
        checkLinkStatus();
        aPosition = GLES20.glGetAttribLocation(mProgram, "v_Position");
        aColor = GLES20.glGetUniformLocation(mProgram, "v_Color");

        circleVertex = cretePositions();

        ByteBuffer bb = ByteBuffer.allocateDirect(circleVertex.length*FLOAT_BYTE_PER)
                .order(ByteOrder.nativeOrder());
        vertexData = bb.asFloatBuffer();
        vertexData.put(circleVertex);
        vertexData.position(0);
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        initData();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //绘制范围是整个屏幕
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        GLES20.glUseProgram(mProgram);
        GLES20.glEnableVertexAttribArray(aPosition);
        GLES20.glVertexAttribPointer(aPosition, 2, GLES20.GL_FLOAT, false,
                STRIDE, vertexData);
        GLES20.glUniform4fv(aColor, 1, paintColor, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, circleVertex.length / 2);
        GLES20.glDisableVertexAttribArray(aPosition);
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
}
