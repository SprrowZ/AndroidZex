package com.rye.opengl.course_y.other.texture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.Log;

import com.rye.opengl.R;
import com.rye.opengl.course_y.CustomEglSurfaceView;
import com.rye.opengl.course_y.other.render.FBORender;
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
            0f, 0f,
            1f, 0f,
            0f, 1f,
            1f, 1f   //纹理坐标替换为FBO坐标，在FBO操作章节有图示
//            0f, 1f,
//            1f, 1f,
//            0f, 0f,
//            1f, 0f    //FBO和纹理坐标系原点不同
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
    //VBO缓存
    private int vboId;
    //FBO离屏渲染
    private int fboId;

    private int imgTextureId;

    private int sampler2D;


    private FBORender fboRender;


    private int mMatrix;//投影矩阵

    private float[] matrix = new float[16];

    public TextureRender(Context context) {
        this.mContext = context;
        fboRender = new FBORender(context);
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
        fboRender.onCreate();
        String vertexSource = TextureResourceReader.readTextFileFromResource(mContext, R.raw.course_demo1_vertex_shader_m);
        String fragmentSource = TextureResourceReader.readTextFileFromResource(mContext, R.raw.course_demo1_fragment_shader);
        program = ShaderHelper.buildProgram(vertexSource, fragmentSource);//创建program并绑定着色器
        //获取顶点着色器属性
        vPosition = GLES20.glGetAttribLocation(program, "v_Position");
        fPosition = GLES20.glGetAttribLocation(program, "f_Position");
        //纹理
        sampler2D = GLES20.glGetUniformLocation(program, "sTexture");
        mMatrix = GLES20.glGetUniformLocation(program, "u_Matrix");//投影矩阵

        //----------VBO (跟纹理操作很像啊)
        int[] vbos = new int[1];
        GLES20.glGenBuffers(1, vbos, 0);
        vboId = vbos[0];
        //绑定
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboId);
        //将顶点着色器和片元着色器大小统一放到此VBO中，开启GPU内存（显存）
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexData.length * 4 + fragmentData.length * 4, null,
                GLES20.GL_STATIC_DRAW);
        //将顶点着色器、片元着色器数据存到显存中
        GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, 0, vertexData.length * 4, vertexBuffer);
        GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, vertexData.length * 4, fragmentData.length * 4, fragmentBuffer);
        //解绑
        GLES20.glBindTexture(GLES20.GL_ARRAY_BUFFER, 0);

        //----------FBO
        int[] fbos = new int[1];
        GLES20.glGenFramebuffers(1, fbos, 0);
        fboId = fbos[0];
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fboId);

        //纹理操作----------
        int[] textureIds = new int[1];
        GLES20.glGenTextures(1, textureIds, 0);
        textureId = textureIds[0];
        //绑定纹理,即使onDrawFrame中每一帧都会绑定，这里也少不了要绑定
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        //单纹理时，下面两个可以不用
        // GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        // GLES20.glUniform1i(sampler2D,0);

        //设置纹理环绕方式
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        //设置FBO缓存大小【必须放在绑定纹理之后，否则会黑屏】                         //TODO width和height自适应手机尺寸
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, 1080, 2122,/*尺寸应该跟手机尺寸一致**/
                0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);

        //【将纹理绑定到FBO】
        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0,
                GLES20.GL_TEXTURE_2D, textureId, 0);
        //判断是否绑定成功
        if (GLES20.glCheckFramebufferStatus(GLES20.GL_FRAMEBUFFER) != GLES20.GL_FRAMEBUFFER_COMPLETE) {
            Log.e("RRye", "fbo wrong");
        } else {
            Log.e("RRye", "fbo right");
        }
        imgTextureId = loadTexture(R.mipmap.my2);

        //加载纹理图片Bitmap
//        loadBitmap();  //绘制图片不要了，利用FBO将其他纹理内容绘制到此纹理上


    }

    private int loadTexture(int src) {//source texture
        int[] textureIds = new int[1];
        GLES20.glGenTextures(1, textureIds, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureIds[0]);

        // GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        // GLES20.glUniform1i(sampler2D,0);


        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        loadBitmap(src);

        //里面也要解绑,纹理用完就解绑掉
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);

        return textureIds[0];
    }

    private void loadBitmap(int src) {
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), src);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
        bitmap = null;
    }

    @Override
    public void onSurfaceChanged(int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        fboRender.onChange(width, height);
        //操作投影矩阵
        if (width > height) {//横屏
            //TODO 数值要根据图片尺寸来，不能写死,//左负右正
            Matrix.orthoM(matrix, 0, -width / ((height / 1243f) * 700f), width / ((height / 1243f) * 700f), -1f, 1f, -1f, 1f); // height/1080 算出图片拉伸的比例,700是图片宽度
        } else {
            Matrix.orthoM(matrix, 0, -1, 1, -height / ((width / 700f) * 1243f), height / ((width / 700f) * 1243f), -1f, 1f); // height/1080 算出图片拉伸的比例,700是图片宽度
        }
        //因fbo与纹理坐标系不同，翻转图像
        Matrix.rotateM(matrix,0,180,1,0,0);
    }

    @Override
    public void onDrawFrame() {
        //绑定FBO，后续操作都不在可见frame中操作，而是在fbo中了;必不可少
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);//此处fboId替换为0的话，就还会渲染到屏幕上

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glUseProgram(program);
        //正交矩阵,在useProgram之后就行
        GLES20.glUniformMatrix4fv(mMatrix, 1, false, matrix, 0);


        //绑定纹理，不绑定也绘制不出来纹理呐
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, imgTextureId);//替换为source texture

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

        //解绑FBO后，才能看到渲染出的纹理
//        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER,0);
//        fboRender.onDraw(textureId);

    }

}
