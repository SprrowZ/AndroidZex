package com.rye.opengl.course_y.oes.camera;

import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;

import java.io.IOException;

public class OESCamera {
    private SurfaceTexture surfaceTexture;
    private Camera camera;
    public  OESCamera() {
    }
    public void init(SurfaceTexture surfaceTexture/*从openGl中获取*/,int cameraId) {
        this.surfaceTexture = surfaceTexture;
        setCameraId(cameraId);
    }
    private void setCameraId(int cameraId) {
        camera = Camera.open(cameraId);
        try {
            camera.setPreviewTexture(surfaceTexture);
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode("off");//关闭闪光灯
            parameters.setPreviewFormat(ImageFormat.NV21);
            parameters.setPictureSize(parameters.getSupportedPictureSizes().get(0).width,
                    parameters.getSupportedPictureSizes().get(0).height);
            parameters.setPreviewSize(parameters.getSupportedPreviewSizes().get(0).width,
                    parameters.getSupportedPreviewSizes().get(0).height);

            camera.setParameters(parameters);
            camera.startPreview();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void stopPreview() {
        if (camera!=null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }
    private void changeCamera(int cameraId) {
        if (camera!= null) {
            stopPreview();
        }
        setCameraId(cameraId);
    }
}
