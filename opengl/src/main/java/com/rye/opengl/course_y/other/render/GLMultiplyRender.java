package com.rye.opengl.course_y.other.render;

import android.content.Context;
import android.opengl.GLES20;

import com.rye.opengl.R;
import com.rye.opengl.course_y.CustomEglSurfaceView;
import com.rye.opengl.hockey.ShaderHelper;
import com.rye.opengl.hockey.TextureResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Create by  [Rye]
 * <p>
 * at 2022/6/12 3:11 下午
 */
public class GLMultiplyRender implements CustomEglSurfaceView.CustomGLRender {

    private Context mContext;//用来加载着色器

    private int program;

    //顶点着色器属性
    private int vPosition; //顶点坐标
    private int fPosition; //纹理坐标



    //VBO缓存
    private int vboId;

    private int textureId;

    //顶点坐标范围
    private float[] vertexData = {
            -1f, -1f,
            1f, -1f,
            -1f, 1f,
            1f, 1f
    };


    private float[] fragmentData = {
            0f, 1f,
            1f, 1f,
            0f, 0f,
            1f, 0f
    };

    private FloatBuffer vertexBuffer;

    private FloatBuffer fragmentBuffer;


    public GLMultiplyRender(Context context) {
        this.mContext = context;
        vertexBuffer = ByteBuffer.allocateDirect(vertexData.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData);
        vertexBuffer.position(0);

        fragmentBuffer = ByteBuffer.allocateDirect(fragmentData.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(fragmentData);
        fragmentBuffer.position(0);
    }

    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }

    @Override
    public void onSurfaceCreated() {
        String vertexSource = TextureResourceReader.readTextFileFromResource(mContext, R.raw.course_demo1_vertex_shader);
        String fragmentSource = TextureResourceReader.readTextFileFromResource(mContext, R.raw.course_demo1_fragment_shader);
        program = ShaderHelper.buildProgram(vertexSource, fragmentSource);//创建program并绑定着色器
        //获取顶点着色器属性
        vPosition = GLES20.glGetAttribLocation(program, "v_Position");
        fPosition = GLES20.glGetAttribLocation(program, "f_Position");

        //----------VBO
        int[] vbos = new int[1];
        GLES20.glGenBuffers(1, vbos, 0);
        vboId = vbos[0];

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboId);
        //将顶点着色器和片元着色器大小统一放到此VBO中，开启GPU内存（显存）
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexData.length * 4 + fragmentData.length * 4, null,
                GLES20.GL_STATIC_DRAW);
        GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, 0, vertexData.length * 4, vertexBuffer);
        GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, vertexData.length * 4, fragmentData.length * 4, fragmentBuffer);

        GLES20.glBindTexture(GLES20.GL_ARRAY_BUFFER, 0);
    }

    @Override
    public void onSurfaceChanged(int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame() {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glUseProgram(program);
        //绑定纹理，不绑定也绘制不出来纹理呐
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);//替换为source texture

        //绑定VBO
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboId);

        //使顶点生效
        GLES20.glEnableVertexAttribArray(vPosition);
        GLES20.glVertexAttribPointer(vPosition, 2, GLES20.GL_FLOAT, false, 8/*一个位置占4位，说明用两个数据代表一个点*/,
                0);//去掉vertexBuffer,buffer从CPU中获取，效率低下，应该使用VBO，减少CPU到GPU的开销；

        GLES20.glEnableVertexAttribArray(fPosition);
        GLES20.glVertexAttribPointer(fPosition, 2, GLES20.GL_FLOAT, false, 8/*一个位置占4位，说明用两个数据代表一个点*/,
                vertexData.length * 4);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);//这里可以改成3个试试，会有奇特现象；
        //纹理解绑，去掉也可以渲染出来
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);

        //单个纹理可以不解绑，多个纹理必须解绑，否则会渲染之前纹理的图片
        GLES20.glBindTexture(GLES20.GL_ARRAY_BUFFER, 0);
    }


}
