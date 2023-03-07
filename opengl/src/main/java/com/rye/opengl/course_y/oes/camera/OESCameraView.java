package com.rye.opengl.course_y.oes.camera;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.WindowManager;

import com.rye.opengl.course_y.CustomEglSurfaceView;
import com.rye.opengl.course_y.other.ZGLSurfaceView;

public class OESCameraView extends CustomEglSurfaceView {
    private OESCameraRender mCameraRender;
    private OESCamera mCamera;
    private int cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
    private int mTextureId = -1;
    public OESCameraView(Context context) {
        this(context,null);
    }

    public OESCameraView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public OESCameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mCameraRender = new OESCameraRender(context);
        mCamera = new OESCamera(context);
        setGLRender(mCameraRender);
        previewAngle(context);
        mCameraRender.setOnSurfaceCreateListener(new OESCameraRender.OnSurfaceCreateListener() {
            @Override
            public void onSurfaceCreated(SurfaceTexture surfaceTexture,int tid) {
                mTextureId = tid;
                 mCamera.init(surfaceTexture, cameraId);
            }
        });
    }

    public void onDestroy() {
        if (mCamera != null) {
            mCamera.stopPreview();
        }
    }

    public  void previewAngle(Context context) {
        //获取当前activity角度，根据此通过matrix动态调整预览角度 || 旋转和翻转要分清！！！
        int angle =((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
        mCameraRender.resetMatrix();
        switch(angle){ //下面的值都是根据实际效果得到的
            case Surface.ROTATION_0:
                if (cameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    mCameraRender.setAngle(90,0,0,1);
                    mCameraRender.setAngle(180,1,0,0);
                } else {
                    mCameraRender.setAngle(90,0,0,1);

                }

                break;
            case Surface.ROTATION_90:
                if (cameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    mCameraRender.setAngle(180,0,0,1);
                    mCameraRender.setAngle(180,0,1,0);
                } else {
                    mCameraRender.setAngle(90,0,0,1);

                }

                break;
            case Surface.ROTATION_180:
                if (cameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    mCameraRender.setAngle(90,0,0,1);
                    mCameraRender.setAngle(180,0,1,0);
                } else {
                    mCameraRender.setAngle(-90,0,0,1);

                }
                break;
            case Surface.ROTATION_270:
                if (cameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    mCameraRender.setAngle(180,0,1,0);
                } else {
                    mCameraRender.setAngle(0,0,0,1);//可以去掉的
                }
                break;
        }

    }

    public int getTextureId() {
        return mTextureId;

    }

}
