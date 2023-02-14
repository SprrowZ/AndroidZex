package com.rye.opengl.course_y.oes.camera;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.AttributeSet;

import com.rye.opengl.course_y.CustomEglSurfaceView;
import com.rye.opengl.course_y.other.ZGLSurfaceView;

public class OESCameraView extends CustomEglSurfaceView {
    private OESCameraRender mCameraRender;
    private OESCamera mCamera;
    private int cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;

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
        mCamera = new OESCamera();
        setGLRender(mCameraRender);
        mCameraRender.setOnSurfaceCreateListener(new OESCameraRender.OnSurfaceCreateListener() {
            @Override
            public void onSurfaceCreated(SurfaceTexture surfaceTexture) {
                mCamera.init(surfaceTexture, cameraId);
            }
        });
    }

    public void onDestroy() {
        if (mCamera != null) {
            mCamera.stopPreview();
        }
    }


}
