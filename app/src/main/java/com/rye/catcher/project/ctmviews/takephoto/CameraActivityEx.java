package com.rye.catcher.project.ctmviews.takephoto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;

import android.media.ExifInterface;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.rye.base.BaseActivity;

import com.rye.catcher.R;
import com.dawn.zgstep.ui.ctm.views.CircleRectView;
import com.rye.catcher.utils.ImageUtils;
import com.rye.base.utils.SDHelper;
import com.rye.catcher.utils.permission.PermissionUtils;
import com.yanzhenjie.permission.Permission;

import java.io.IOException;
import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created at 2019/3/25.
 *
 * @author Zzg
 * @function: 自定义相机
 */

public class CameraActivityEx extends BaseActivity {
    private static final String TAG = CameraActivityEx.class.getSimpleName();

    @BindView(R.id.cameraPreview)
    CameraPreviewEx cameraPreview;
    @BindView(R.id.takePhoto)
    CircleRectView takePhoto;
    @BindView(R.id.iv)
    ImageView iv;

    @Override
    public int getLayoutId() {
        return R.layout.activity_preview_ex;
    }

    @Override
    public void initEvent() {
        PermissionUtils.requestPermission(this, "请开启相机权限！", false,
                action -> init(), 0, Permission.CAMERA);

    }

    @OnClick({R.id.cameraPreview, R.id.takePhoto})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cameraPreview:
                cameraPreview.reAutoFocus();
                break;
            case R.id.takePhoto:
                takePhotos();
                break;
        }
    }

    private void init() {


    }

    private void takePhotos() {
        cameraPreview.takePhoto((data, camera) -> {
            //   camera.startPreview();
            new Thread(new TakePhoto(data, camera, CameraActivityEx.this)).start();
        });
    }


    private static class TakePhoto implements Runnable {
        private WeakReference<CameraActivityEx> weakReference;
        private byte[] data;
        private Camera camera;

        public TakePhoto(final byte[] data, Camera camera, CameraActivityEx activityEx) {
            this.data = data;
            this.camera = camera;
            weakReference = new WeakReference<>(activityEx);
        }

        @Override
        public void run() {
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            weakReference.get().runOnUiThread(() ->
                    weakReference.get().iv.setImageBitmap(bitmap));

            ImageUtils.getIntance().saveBitmap(bitmap, "Z-TAKE.JPEG");
            int angle = readPictureDegree(SDHelper.getImageFolder() + "Z-TAKE.JPEG");
            Log.i(TAG, "run: " + angle);
        }
    }

    /**
     * 读取照片旋转角度
     *
     * @param path 照片路径
     * @return 角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转图片
     *
     * @param angle  被旋转角度
     * @param bitmap 图片对象
     * @return 旋转后的图片
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        Bitmap returnBm = null;
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bitmap;
        }
        if (bitmap != returnBm) {
            bitmap.recycle();
        }
        return returnBm;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
