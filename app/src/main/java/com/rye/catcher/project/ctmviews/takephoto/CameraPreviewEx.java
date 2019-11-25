package com.rye.catcher.project.ctmviews.takephoto;

import android.annotation.TargetApi;
import android.content.Context;

import android.content.res.Configuration;
import android.graphics.Canvas;
import android.hardware.Camera;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.io.IOException;
import java.util.List;

/**
 * Created at 2019/3/25.
 *
 * @author Zzg
 * @function: 相机预览
 */
public class CameraPreviewEx extends SurfaceView implements SurfaceHolder.Callback {
    private static String TAG = CameraPreviewEx.class.getName();

    private SurfaceHolder mHolder;
    //Camera已经过时，先用着，用好了替换成Camera2
    private Camera camera;

    private int orientation;
    public CameraPreviewEx(Context context) {
        super(context);
        init();
    }

    public CameraPreviewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CameraPreviewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CameraPreviewEx(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        //只是获取当前的surfaceHolder，设置回调，并不是camera传入得surfaceHolder
         mHolder=getHolder();
         //surfaceHolder添加回调，去掉试试？
         mHolder.addCallback(this);
        //是否保持屏幕常亮，true常亮，false不亮
        mHolder.setKeepScreenOn(true);

        Camera.CameraInfo info =new Camera.CameraInfo();
        orientation=info.orientation;
        Log.i(TAG, "init: "+orientation);
    }



    public void surfaceCreated(SurfaceHolder holder) {
             //打开相机
        camera=CameraUtils.openCamera();
        //设置相机预览方向
        if (camera!=null){
            try {
                //设置一个surface用承载预览
                camera.setPreviewDisplay(holder);//This method must be called before {@link #startPreview()}
                Camera.Parameters  parameters= camera.getParameters();
                //如果是垂直方向
                if (getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT){
                    //预览方向旋转90度
                    camera.setDisplayOrientation(90);
                    //获取相机的旋转角度
                     parameters.setRotation(90);
                    Log.i(TAG, "surfaceCreated: 1111");
                }else{
                    camera.setDisplayOrientation(0);
      //              parameters.setRotation(0);
                    Log.i(TAG, "surfaceCreated: 2222");
                }
            //接下来就是设置相机预览图片的尺寸了，模糊就是因为尺寸不对头
            Camera.Size bestSize=getBestSize(parameters.getSupportedPictureSizes());
            if (bestSize!=null){
                //设置保存图片的尺寸，这个其实我们不用保存原图的
                parameters.setPictureSize(bestSize.width,bestSize.height);
                //预览尺寸
                parameters.setPreviewSize(bestSize.width,bestSize.height);
            }else{
                //一般相机的照片是经过旋转的，高度和宽度不是我们手机的宽高！
                parameters.setPreviewSize(1920,1080);
                parameters.setPictureSize(1920,1080);
            }
            camera.setParameters(parameters);
            camera.startPreview();
            //开启相机的时候就自动对焦一次
            reAutoFocus();
            } catch (IOException e) {
                e.printStackTrace();
                Log.i(TAG, "surfaceCreated: error....");
            }
        }
    }


    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //自动对焦
        reAutoFocus();
    }


    public void surfaceDestroyed(SurfaceHolder holder) {
        release();
    }

    /**
     *  Android相机的预览尺寸都是4:3或者16:9，这里遍历所有支持的预览尺寸，得到16:9的最大尺寸，保证成像清晰度
     * @param sizes
     * @return
     */
    private Camera.Size getBestSize(List<Camera.Size> sizes) {
        Camera.Size bestSize = null;
        for (Camera.Size size : sizes) {

            if ((float) size.height / (float) size.width == 16.0f / 9.0f) {
                if (bestSize != null) {//肯定null啊..
                    bestSize = size;
//               }else if (size.width > bestSize.width){
//                   bestSize=size;
//                   Log.i(TAG, "getBestSize: 走个屁...");
//               }
                }
            }
        }
        return bestSize;
    }
    public void reAutoFocus() {

//            camera.autoFocus(new Camera.AutoFocusCallback() {
//                @Override
//                public void onAutoFocus(boolean success, Camera camera) {
//                }
//            });
           camera.autoFocus(null);
    }

    /**
     * 拍摄照片
     *
     * @param pictureCallback 在pictureCallback处理拍照回调
     */
    public void takePhoto(Camera.PictureCallback pictureCallback) {
        if (camera != null) {
            camera.takePicture(null, null, pictureCallback);
        }
    }

    /**
     * 释放资源
     */
    private void release() {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
