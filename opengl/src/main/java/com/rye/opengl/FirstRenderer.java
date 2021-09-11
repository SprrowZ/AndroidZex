package com.rye.opengl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class FirstRenderer  implements GLSurfaceView.Renderer {
    private static final String U_COLOR = "u_Color";
    private int uColorLocation;
    private static final String A_POSITION ="a_Position";
    private int aPositionLocation;



    //记录四方形位置坐标
    float[] tableVerticesWithTriangles = { //两个点代表一个位置
            //第一个三角形
            0f, 0f,
            9f, 14f,
            0f, 14f,
            //第二个三角形
            0f, 0f,
            9f, 0f,
            9f, 14f,
            //中间分界线
            0f,7f,
            9f,7f,
            //两个摇杆的质点位置
            4.5f,2f,
            4.5f,12f
    };
    //一个顶点有两个分量x,y
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int BYTES_PER_FLOAT = 4;
    private static final int COLOR_COMPONENT_COUNT = 3;
    private final FloatBuffer vertexData =
            ByteBuffer.allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT)
                    .order(ByteOrder.nativeOrder())
                    .asFloatBuffer();
    //着色器content
    String vertexShaderSource;
    String fragmentShaderSource;

    public FirstRenderer(Context context){
        vertexShaderSource = TextureResourceReader.readTextFileFromResource(context,
                R.raw.simple_vertex_shader);
        fragmentShaderSource = TextureResourceReader.readTextFileFromResource(context,
                R.raw.simple_fragment_shader);
        vertexData.put(tableVerticesWithTriangles);
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f,0.0f,0.0f,0.0f);
        int programId = ShaderHelper.buildProgram(vertexShaderSource,fragmentShaderSource);
        //告诉OpenGL使用此程序对象绘制屏幕信息
        GLES20.glUseProgram(programId);
        //分别获取attribute/uniform的位置
        uColorLocation = GLES20.glGetUniformLocation(programId,U_COLOR);
        aPositionLocation = GLES20.glGetAttribLocation(programId,A_POSITION);
        vertexData.position(0);
        //告诉OpenGL从哪里读取a_Position数据
        GLES20.glVertexAttribPointer(aPositionLocation,POSITION_COMPONENT_COUNT,
                GLES20.GL_FLOAT,false,0,vertexData);
        GLES20.glEnableVertexAttribArray(aPositionLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
       GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        //##### 1.先画两个三角形
        //更新着色器中u_Color的值，uniform没有默认值，一个uniform在着色器中被定义vec4类型，
        //我们就需要提供所有四个分量的值，红绿蓝透明度
        GLES20.glUniform4f(uColorLocation,1.0f,1.0f,1.0f,1.0f);
        //第一个参数指定要画三角形；第二个参数告诉OpenGL从顶点数组的开头处开始读顶点；
        //第三个参数告诉OpenGL读入六个顶点，因为每个三角形有三个顶点，所以调用最后会画出两个三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,6);//glVertexAttribPointer中指定了两个数据为一组
        //##### 2.接下来绘制桌子中间的中心分割线
        //定义u_Color为红色
        GLES20.glUniform4f(uColorLocation,1.0f,0.0f,0.0f,1.0f);
        //画的是线，所有应GL_LINES,从顶点的第6组开始，每组也是2个元素
        GLES20.glDrawArrays(GLES20.GL_LINES,6,2);
        //##### 3.画木追位置的两个点
        GLES20.glUniform4f(uColorLocation,0.0f,0.0f,1.0f,1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS,8,1);
        GLES20.glUniform4f(uColorLocation,0.0f,1.0f,0.0f,1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS,9,1);
    }
}
