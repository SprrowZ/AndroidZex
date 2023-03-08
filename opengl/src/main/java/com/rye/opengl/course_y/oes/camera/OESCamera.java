package com.rye.opengl.course_y.oes.camera;

import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;

import com.rye.opengl.course_y.DisplayUtil;

import java.io.IOException;
import java.util.List;

public class OESCamera {
    private SurfaceTexture surfaceTexture;
    private Camera camera;
    private int width;
    private int height;

    public  OESCamera(Context context) {
        this.width = DisplayUtil.getScreenWidth(context);
        this.height = DisplayUtil.getScreenHeight(context);
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
             Camera.Size size = getFitSize(parameters.getSupportedPictureSizes());
             parameters.setPictureSize(size.width,size.height);
             size = getFitSize(parameters.getSupportedPreviewSizes());
             parameters.setPreviewSize(size.width,size.height);

//            parameters.setPictureSize(parameters.getSupportedPictureSizes().get(0).width,
//                    parameters.getSupportedPictureSizes().get(0).height);
//            parameters.setPreviewSize(parameters.getSupportedPreviewSizes().get(0).width,
//                    parameters.getSupportedPreviewSizes().get(0).height);

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
    private Camera.Size getFitSize(List<Camera.Size> sizes) {
        if (width<height){
            int t = height;
            height = width;
            width = t;
        }
        for (Camera.Size size:sizes) {
            if (1.0f * size.width /size.height == 1.0f * width/height){
                return size;
            }
        }
        return sizes.get(0);
    }
}
