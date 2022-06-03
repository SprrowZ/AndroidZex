package com.rye.opengl.course_y.other.texture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

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
 * at 2022/5/29 4:09 下午
 */
public class TextureRender implements CustomEglSurfaceView.CustomGLRender {
    private Context mContext;//用来加载着色器

    private int program;
    //顶点坐标范围
    private float[] vertexData = {
            -1f, -1f,
            1f, -1f,
            -1f, 1f, //前三个就是一个三角形
            1f, 1f //最后差一个点;
    };

    //纹理坐标范围
    private float[] fragmentData = { //点要和顶点坐标对应上！【顶点坐标范围为-1~1，纹理坐标范围为0~1】
            0f, 1f,
            1f, 1f,
            0f, 0f,
            1f, 0f
    };
    //为坐标分配本地内存地址（OpenGL没有虚拟机，直接绘制在本地内存中）
    private FloatBuffer vertexBuffer;
    //为纹理坐标分配本地内存地址
    private FloatBuffer fragmentBuffer;


    //顶点着色器属性
    private int vPosition; //顶点坐标
    private int fPosition; //纹理坐标

    //纹理
    private int textureId;

    private int sampler2D;

    public TextureRender(Context context) {
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


    @Override
    public void onSurfaceCreated() {
        String vertexSource = TextureResourceReader.readTextFileFromResource(mContext, R.raw.course_demo1_vertex_shader);
        String fragmentSource = TextureResourceReader.readTextFileFromResource(mContext, R.raw.course_demo1_fragment_shader);
        program = ShaderHelper.buildProgram(vertexSource, fragmentSource);//创建program并绑定着色器
        //获取顶点着色器属性
        vPosition = GLES20.glGetAttribLocation(program,"v_Position");
        fPosition = GLES20.glGetAttribLocation(program,"f_Position");
        //纹理
        sampler2D = GLES20.glGetUniformLocation(program,"sTexture");


        //加载纹理
        int[] textureIds = new int[1];
        GLES20.glGenTextures(1,textureIds,0);
        textureId = textureIds[0];
        //绑定纹理,即使onDrawFrame中每一帧都会绑定，这里也少不了要绑定
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textureId);
        //单纹理时，下面两个可以不用
       // GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
       // GLES20.glUniform1i(sampler2D,0);

        //设置纹理环绕方式
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_REPEAT);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
        //加载纹理图片Bitmap
        loadBitmap();
    }

    private void loadBitmap() {
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.my2);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D,0,bitmap,0);
        bitmap.recycle();
        bitmap = null;
    }

    @Override
    public void onSurfaceChanged(int width, int height) {
         GLES20.glViewport(0,0,width,height);
    }

    @Override
    public void onDrawFrame() {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(1.0f,0.0f,0.0f,1.0f);
        GLES20.glUseProgram(program);
        //绑定纹理，不绑定也绘制不出来纹理呐
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textureId);

        //使顶点生效
        GLES20.glEnableVertexAttribArray(vPosition);
        GLES20.glVertexAttribPointer(vPosition,2,GLES20.GL_FLOAT,false,8/*一个位置占4位，说明用两个数据代表一个点*/,
                vertexBuffer);

        GLES20.glEnableVertexAttribArray(fPosition);
        GLES20.glVertexAttribPointer(fPosition,2,GLES20.GL_FLOAT,false,8/*一个位置占4位，说明用两个数据代表一个点*/,
                fragmentBuffer);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP,0,4);//这里可以改成3个试试，会有奇特现象；
        //纹理解绑，去掉也可以渲染出来
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,0);
    }
}
