package com.rye.opengl.course_y.yuv;

import android.content.Context;
import android.opengl.GLES10;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import com.rye.opengl.R;
import com.rye.opengl.course_y.CustomEglSurfaceView;
import com.rye.opengl.hockey.TextureResourceReader;
import com.rye.opengl.utils.ShaderHelper;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Create by  [Rye]
 * <p>
 * at 2023/5/5 11:34
 */
public class YuvRender implements CustomEglSurfaceView.CustomGLRender {

    private Context context;

    private FloatBuffer vertexBuffer;
    private float[] vertexData = {  //TODO 确认是否需要修改下面两个数组内容
            -1f, -1f,
            1f, -1f,
            -1f, 1f,
            1f, 1f
    };

    private FloatBuffer textureBuffer;

    private float[] textureData = {
            0f, 1f,
            1f, 1f,
            0f, 0f,
            1f, 0f
    };

    private int program;
    private int vPosition;
    private int fPosition;

    private int samplerY;
    private int samplerU;
    private int samplerV;

    private int[] texture_yuv;

    private int fboId;
    private int textureId;//fbo的textureId

    int w;
    int h;
    Buffer y;
    Buffer u;
    Buffer v;

    private YUVFboRender fboRender;

    private float[] matrix = new float[16];
    private int uMatrix;

    public YuvRender(Context context) {
        this.context = context;
        fboRender = new YUVFboRender(context);
        vertexBuffer = ByteBuffer.allocateDirect(vertexData.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData);
        vertexBuffer.position(0);

        textureBuffer = ByteBuffer.allocateDirect(textureData.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(textureData);
        textureBuffer.position(0);
        Matrix.setIdentityM(matrix,0);
    }

    @Override
    public void onSurfaceCreated() {
        checkError("00");
        fboRender.onCreate();
        String vertexSource = TextureResourceReader.readTextFileFromResource(context, R.raw.vertex_shader_yuv);
        String fragmentSource = TextureResourceReader.readTextFileFromResource(context, R.raw.fragment_shader_yuv);
        program = ShaderHelper.buildProgram(vertexSource, fragmentSource);
        vPosition = GLES20.glGetAttribLocation(program, "v_Position");
        fPosition = GLES20.glGetAttribLocation(program, "f_Position");

        samplerY = GLES20.glGetUniformLocation(program, "sampler_y");
        samplerU = GLES20.glGetUniformLocation(program, "sampler_u");
        samplerV = GLES20.glGetUniformLocation(program, "sampler_v");
        uMatrix = GLES20.glGetUniformLocation(program,"u_Matrix");
        checkError("11");

        texture_yuv = new int[3];
        GLES20.glGenTextures(3, texture_yuv, 0);

        for (int i = 0; i < 3; i++) {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture_yuv[i]);
            //设置纹理环绕方式
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);

            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        }
        checkError("22");


        //----------FBO
        int[] fbos = new int[1];
        GLES20.glGenBuffers(1, fbos, 0);
        fboId = fbos[0];
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fboId);


        //纹理操作----------
        int[] textureIds = new int[1];
        GLES20.glGenTextures(1, textureIds, 0);
        textureId = textureIds[0];
        //绑定纹理,即使onDrawFrame中每一帧都会绑定，这里也少不了要绑定
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        checkError("33");

        //设置纹理环绕方式
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        //设置FBO缓存大小【必须放在绑定纹理之后，否则会黑屏】
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, 1080, 2122,/*尺寸应该跟手机尺寸一致**/
                0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);
        checkError("44");

        //【将纹理绑定到FBO】
        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0,
                GLES20.GL_TEXTURE_2D, textureId, 0);
        //判断是否绑定成功
        if (GLES20.glCheckFramebufferStatus(GLES20.GL_FRAMEBUFFER) != GLES20.GL_FRAMEBUFFER_COMPLETE) {
            Log.e("RRye", "fbo wrong");
        } else {
            Log.e("RRye", "fbo right");
        }

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
        checkError("55");


    }

    @Override
    public void onSurfaceChanged(int width, int height) {
        Matrix.rotateM(matrix,0,180f,1,0,0);
        GLES20.glViewport(0, 0, width, height);
        fboRender.onChange(width,height);

    }

    @Override
    public void onDrawFrame() {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(1f, 0f, 0f, 1f);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fboId); //改为0则直接绘制在窗口上,改fbo则绘制在fbo上
        checkError("66");

        if (w > 0 && h > 0 && y != null && u != null && v != null) {
            GLES20.glUseProgram(program);
            GLES20.glUniformMatrix4fv(uMatrix,1,false,matrix,0);//使用矩阵翻转
            GLES20.glEnableVertexAttribArray(vPosition);
            GLES20.glVertexAttribPointer(vPosition, 2, GLES20.GL_FLOAT, false, 8, vertexBuffer);

            GLES20.glEnableVertexAttribArray(fPosition);
            GLES20.glVertexAttribPointer(fPosition, 2, GLES20.GL_FLOAT, false, 8, textureBuffer);
            checkError("77");

            //---激活y/u/v纹理
            //y
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture_yuv[0]);
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_LUMINANCE, w, h, 0, GLES20.GL_LUMINANCE, GLES20.GL_UNSIGNED_BYTE, y);
            GLES20.glUniform1i(samplerY, 0);//这个数字和纹理对应
            //u
            GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture_yuv[1]);
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_LUMINANCE, w / 2, h / 2, 0, GLES20.GL_LUMINANCE, GLES20.GL_UNSIGNED_BYTE, u);
            GLES20.glUniform1i(samplerU, 1);
            //v
            GLES20.glActiveTexture(GLES20.GL_TEXTURE2);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture_yuv[2]);
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_LUMINANCE, w / 2, h / 2, 0, GLES20.GL_LUMINANCE, GLES20.GL_UNSIGNED_BYTE, v);
            GLES20.glUniform1i(samplerV, 2);
            checkError("88");

            y.clear();
            u.clear();
            v.clear();

            y = null;
            u = null;
            v = null;

            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP,0,4);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,0);
            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER,0);
            checkError("99");

            fboRender.onDraw(textureId);

        }

    }

    public void setFrameData(int w, int h, byte[] by, byte[] bu, byte[] bv) {
        this.w = w;
        this.h = h;
        this.y = ByteBuffer.wrap(by);
        this.u = ByteBuffer.wrap(bu);
        this.v = ByteBuffer.wrap(bv);
    }

    private void checkError(String params) {
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        int errorCode = GLES20.glGetError();
        if (errorCode != 0) {
            Log.i("RRye", "errorCode:" + errorCode+", params:"+params);
        }
    }
}
