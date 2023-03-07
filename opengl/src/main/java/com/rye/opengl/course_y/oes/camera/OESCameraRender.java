package com.rye.opengl.course_y.oes.camera;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import com.rye.opengl.R;
import com.rye.opengl.course_y.CustomEglSurfaceView;
import com.rye.opengl.course_y.DisplayUtil;
import com.rye.opengl.hockey.TextureResourceReader;
import com.rye.opengl.utils.ShaderHelper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class OESCameraRender implements CustomEglSurfaceView.CustomGLRender,SurfaceTexture.OnFrameAvailableListener {
    private Context mContext;
    private int program;
    private int vPosition; //顶点坐标
    private int fPosition; //纹理坐标
    private int sampler2D;
    //纹理
    private int fboTextureId;
    private int cameraTextureId;
    //矩阵
    private int uMatrix;




    private  float[] matrix = new float[16];//4个点，单点4字节

    private SurfaceTexture mSurfaceTexture;
    private OnSurfaceCreateListener mOnSurfaceCreateListener;

    private CameraFboRender mCameraFboRender;


    //VBO缓存
    private int vboId;
    //FBO离屏渲染
    private int fboId;

    private float[] vertexData = {
            -1f, -1f,
            1f, -1f,
            -1f, 1f,
            1f, 1f
    };
    private FloatBuffer vertexBuffer;

    private float[] fragmentData = {
            0f, 1f,
            1f, 1f,
            0f, 0f,
            1f, 0f
    };
    private FloatBuffer fragmentBuffer;

    private int screenHeight;
    private int screenWidth;

    private int width;
    private int height;

    public OESCameraRender(Context context) {
        this.mContext = context;

        screenHeight = DisplayUtil.getScreenHeight(context);
        screenWidth = DisplayUtil.getScreenWidth(context);

        mCameraFboRender = new CameraFboRender(context);
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

        Matrix.setIdentityM(matrix,0);//矩阵初始化


    }

    public void setOnSurfaceCreateListener(OnSurfaceCreateListener mOnSurfaceCreateListener) {
        this.mOnSurfaceCreateListener = mOnSurfaceCreateListener;
    }

    @Override
    public void onSurfaceCreated() {
        mCameraFboRender.onCreate();
        String vertexSource = TextureResourceReader.readTextFileFromResource(mContext, R.raw.oes_vertex_shader_screen);
        String fragmentSource = TextureResourceReader.readTextFileFromResource(mContext, R.raw.oes_fragment_shader);
        program = ShaderHelper.buildProgram(vertexSource, fragmentSource);//创建program并绑定着色器
        //获取顶点着色器属性
        vPosition = GLES20.glGetAttribLocation(program, "v_Position");
        fPosition = GLES20.glGetAttribLocation(program, "f_Position");
        uMatrix = GLES20.glGetUniformLocation(program,"u_Matrix");
        //----------VBO (跟纹理操作很像啊)
        int[] vbos = new int[1];
        GLES20.glGenBuffers(1, vbos, 0);
        vboId = vbos[0];

        //----vbo绑定
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboId);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexData.length * 4 + fragmentData.length * 4, null,
                GLES20.GL_STATIC_DRAW);
        GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, 0, vertexData.length * 4, vertexBuffer);
        GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, vertexData.length * 4, fragmentData.length * 4, fragmentBuffer);
        //----vbo解绑
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);


        //----------FBO
        int[] fbos = new int[1];
        GLES20.glGenBuffers(1, fbos, 0);
        fboId = fbos[0];
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fboId);
        //FBO纹理操作----------
        int[] textureIds = new int[1];
        GLES20.glGenTextures(1, textureIds, 0);
        fboTextureId = textureIds[0];
        //绑定纹理,即使onDrawFrame中每一帧都会绑定，这里也少不了要绑定
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, fboTextureId);


        //设置纹理环绕方式
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        //checkError("loadTexture-------");

        //设置FBO缓存大小【必须放在绑定纹理之后，否则会黑屏】
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, screenWidth, screenHeight,/*尺寸应该跟手机尺寸一致**/
                0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);

        //【将纹理绑定到FBO】
        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0,
                GLES20.GL_TEXTURE_2D, fboTextureId, 0);
        //判断是否绑定成功
        if (GLES20.glCheckFramebufferStatus(GLES20.GL_FRAMEBUFFER) != GLES20.GL_FRAMEBUFFER_COMPLETE) {
            Log.e("RRye", "fbo wrong");
        } else {
            Log.e("RRye", "fbo right");
        }
         checkError("1111");
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);

        //camera纹理
        int[] cameraTextureIdsOES = new int[1];
        GLES20.glGenTextures(1, cameraTextureIdsOES, 0);
        cameraTextureId = cameraTextureIdsOES[0];
        //注意这里绑定的oes纹理
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, cameraTextureId);
        //设置纹理环绕方式
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);

        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES ,GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        mSurfaceTexture = new SurfaceTexture(cameraTextureId);
        mSurfaceTexture.setOnFrameAvailableListener(this);
        if (mOnSurfaceCreateListener!=null) {
            mOnSurfaceCreateListener.onSurfaceCreated(mSurfaceTexture);
        }

        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 0);
        checkError("2222");
    }
    private void checkError(String params) {
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        int errorCode = GLES20.glGetError();
        if (errorCode != 0) {
            Log.i("RRye", "errorCode:" + errorCode+", params:"+params);
        }
    }

    public void resetMatrix() {
        Matrix.setIdentityM(matrix,0);
    }

    public void setAngle(float angle,float x,float y,float z) {
        Matrix.rotateM(matrix,0,angle,x,y,z);
    }

    @Override
    public void onSurfaceChanged(int width, int height) {
//          mCameraFboRender.onChange(width,height);
//          GLES20.glViewport(0,0,width,height);

//          Matrix.rotateM(matrix,0,90,0,0,1);//绕y轴旋转90度
//        Matrix.rotateM(matrix,0,180,1,0,0);//绕x轴翻转180度
        this.width = width;
        this.height = height;
        
    }

    @Override
    public void onDrawFrame() {
        mSurfaceTexture.updateTexImage();
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(1f,0f, 0f, 1f);

        GLES20.glUseProgram(program);

        GLES20.glViewport(0,0,screenWidth,screenHeight);
        //矩阵
        GLES20.glUniformMatrix4fv(uMatrix,1,false,matrix,0);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fboId);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboId);

        GLES20.glEnableVertexAttribArray(vPosition);
        GLES20.glVertexAttribPointer(vPosition, 2, GLES20.GL_FLOAT, false, 8,
                0);

        GLES20.glEnableVertexAttribArray(fPosition);
        GLES20.glVertexAttribPointer(fPosition, 2, GLES20.GL_FLOAT, false, 8,
                vertexData.length * 4);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
        mCameraFboRender.onChange(width,height);//以实际窗口绘制
        mCameraFboRender.onDraw(fboTextureId);


    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {

    }

    public interface OnSurfaceCreateListener {
        void onSurfaceCreated(SurfaceTexture surfaceTexture);
    }

}
