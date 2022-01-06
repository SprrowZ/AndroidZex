package com.dawn.zgstep.player.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.SensorManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;

import android.util.Log;
import android.util.Pair;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dawn.zgstep.R;
import com.rye.base.media.CameraPreview;
import com.rye.base.utils.PermissionsUtil;

import java.io.File;
import java.io.IOException;
import java.security.Permission;
import java.security.Permissions;

/**
 * 拍照界面
 */
public class MediaCodecActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "CameraActivity2";

    public final static int REQUEST_CODE = 0X13;
    public final static int RESULT_CODE = 0X14;
    //水平还是垂直
    private boolean IS_VERTICAL = true;
    //是否还能旋转
    private boolean CAN_ROTATE = true;
    private final static int CAMERA_REQUEST_CODE = 5;
    //相册
    private Bitmap Abitmap;



    public static void openCamera(Activity activity, int type) {
        Intent intent = new Intent(activity, MediaCodecActivity.class);
        intent.putExtra("type", type);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    /**
     * @return 结果文件路径
     */
    public static String getResult(Intent data) {
        if (data != null) {
            return data.getStringExtra("result");
        }
        return "";
    }

    private CameraPreview cameraPreview;
    private View containerView;
    private ImageView cropView;
    private ImageView pickPhoto;

    private LinearLayout topContainer;
    //cropView上边的文字
    private TextView cropTextV;
    private TextView cropTextH;

    private TextView remake;

    //哪个图标被选中
    private int type;
    //屏幕宽度
    private float screenWidth;
    //当前位置
    private int currentPos = -1;
    // 设备方向监听器
    private OrientationEventListener mOrEventListener;
    //获取最近一张照片
    private CameraThread mThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        initView();
        initEvent();
    }

    private void initView() {
        //type表示是哪个被选中了
        type = getIntent().getIntExtra("type", 0);
        cameraPreview = findViewById(R.id.camera_surface);
        //中间布局
        containerView = findViewById(R.id.camera_crop_container);
        //裁剪布局
        cropView = findViewById(R.id.camera_crop);

        topContainer = findViewById(R.id.topContainer);
        //文字
        cropTextV = findViewById(R.id.cropTextV);
        cropTextH = findViewById(R.id.cropTextH);
        //设置crop图片
        pickPhoto = (ImageView) findViewById(R.id.album_pick);
        remake = findViewById(R.id.remake);
        cameraPreview.setOnClickListener(this);
        remake.setOnClickListener(this);
        findViewById(R.id.camera_close).setOnClickListener(this);
        findViewById(R.id.camera_take).setOnClickListener(this);
        pickPhoto.setOnClickListener(this);
        mThread = new CameraThread();
        mThread.start();
    }

    @SuppressLint("NewApi")
    private void initEvent() {

        screenWidth = Math.min(getResources().getDisplayMetrics().widthPixels,
                getResources().getDisplayMetrics().heightPixels);
        //长宽比为标准的16:9
//        float screenHeight = screenWidth / 9.0f * 16.0f;

        //获取屏幕方向，设置crop图片
        if (getOrientation()) {
            verticalView();
        } else {
            horizontalView();
        }
        //监听屏幕方向
        mOrEventListener = new OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int orientation) {
                Log.i(TAG, "isMainThread:" + (Looper.getMainLooper() == Looper.myLooper()) + "," + CAN_ROTATE);
                if ((((orientation >= 0) && (orientation <= 45)) || (orientation >= 315)
                        || ((orientation >= 135) && (orientation <= 225))) && CAN_ROTATE) {// portrait
                    IS_VERTICAL = true;
                    verticalView();
                    Log.i(TAG, "竖屏");
                } else if ((((orientation > 45) && (orientation < 135))
                        || ((orientation > 225) && (orientation < 315))) && CAN_ROTATE) {// landscape
                    IS_VERTICAL = false;
                    horizontalView();
                    Log.i(TAG, "横屏");
                }


            }
        };
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera_surface:
                cameraPreview.focus();
                break;
            case R.id.camera_close:
                finish();
                break;
            case R.id.camera_take:
                takePhoto();
                break;
            case R.id.album_pick:
                pickPhotos();
                break;
            case R.id.remake:
                remakePhoto();
                break;
        }
    }

    /**
     * 打开相册
     */
    private void pickPhotos() {
        PermissionsUtil.INSTANCE.checkPermissions(this, new PermissionsUtil.IPermissionsResult() {
            @Override
            public void passPermissons() {
                Intent intent = new Intent(
                        Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }

            @Override
            public void forbitPermissons() {

            }
        }, Permission.);
    }

    /**
     * 重拍
     */
    private void remakePhoto() {
        CAN_ROTATE = true;//可以旋转
        remake.setVisibility(View.GONE);
        cropView.setBackgroundResource(0);
        if (IS_VERTICAL) {
            cropView.setImageResource(R.drawable.vertical_crop);
        } else {
            cropView.setImageResource(R.drawable.horizontal_crop);
        }
    }

    /**
     * 获取屏幕方向，如果是竖屏，返回true
     *
     * @return
     */
    private boolean getOrientation() {
        if (this.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 竖屏
     */
    private void verticalView() {
        cropTextV.setVisibility(View.VISIBLE);
        cropTextH.setVisibility(View.GONE);
        cropView.setImageResource(R.drawable.vertical_crop);
        float width = (int) (screenWidth * 0.9);
        float height = (int) (width * 0.75f);
        RelativeLayout.LayoutParams cropParams = new RelativeLayout.LayoutParams((int) width, (int) height);
        cropParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        cropView.setLayoutParams(cropParams);
    }

    /**
     * 横屏
     */
    private void horizontalView() {
        cropTextV.setVisibility(View.GONE);
        cropTextH.setVisibility(View.VISIBLE);
        cropView.setImageResource(R.drawable.horizontal_crop);
        float width = (int) (screenWidth * 0.7);
        float height = (int) (width * 1.6f);
        RelativeLayout.LayoutParams cropParams = new RelativeLayout.LayoutParams((int) width, (int) height);
        cropParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        cropView.setLayoutParams(cropParams);
    }


    private void takePhoto() {
        cameraPreview.setEnabled(false);
        cameraPreview.takePhoto((data, camera) -> {
            //    camera.stopPreview();
            camera.startPreview();//机型适配
            //子线程处理图片，防止ANR
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //拍好了不能再旋转
                    CAN_ROTATE = false;
                    try {
                        // TODO: 2019/3/7 暂时处理，目前没有更好的解决办法
                        Bitmap bitmap;
                        //计算裁剪位置
                        int pX, pY, pWidth, pHeight;
                        pX = cropView.getLeft();

                        if ("HUAWEI".equals(Build.MANUFACTURER)) {
                            pY = cropView.getTop() + topContainer.getHeight() * 2;
                            bitmap = rotateBitmapByDegree(BitmapFactory.decodeByteArray(data, 0, data.length), 0);
                        } else {
                            pY = cropView.getTop() + topContainer.getHeight();
                            bitmap = rotateBitmapByDegree(BitmapFactory.decodeByteArray(data, 0, data.length), 90);
                        }
                        //裁剪长度
                        pWidth = (int) ((float) cropView.getWidth() / cameraPreview.getWidth() * (float) bitmap.getWidth());
                        pHeight = (int) ((float) cropView.getHeight() / cameraPreview.getHeight() * (float) bitmap.getHeight());
                        //裁剪图片
                        Bitmap cropBitmap = Bitmap.createBitmap(bitmap,
                                pX,
                                pY,
                                pWidth,
                                pHeight);

                        bitmap.recycle();
                        // cropBitmap.recycle();
                        runOnUiThread(() -> {
                            //
                            if (IS_VERTICAL) {
                                cropView.setImageResource(R.drawable.vertical_crop_no);
                            } else {
                                cropView.setImageResource(R.drawable.horizontal_crop_no);
                            }
                            cropView.setBackground(new BitmapDrawable(getResources(), cropBitmap));
                        });
                        runOnUiThread(() -> {
                            if (true) {//如果处理失败
                                remake.setVisibility(View.VISIBLE);
                                cropTextH.setText(getResources().getString(R.string.dis_error));
                                cropTextV.setText(getResources().getString(R.string.dis_error));
                            } else {//处理成功

                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        });
    }

    /**
     * 旋转图片
     *
     * @param bm
     * @param degree
     * @return
     */
    public Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                    bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

    public int readPictureDegress(String path) {
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
     * 获取相册中最新一张图片
     *
     * @param context
     * @return
     */
    public static Pair<Long, String> getLatestPhoto(Context context) {
        //拍摄照片的地址
        String CAMERA_IMAGE_BUCKET_NAME = Environment.getExternalStorageDirectory().toString() + "/DCIM/Camera";
        //截屏照片的地址
        String SCREENSHOTS_IMAGE_BUCKET_NAME = getScreenshotsPath();
        //拍摄照片的地址ID
        String CAMERA_IMAGE_BUCKET_ID = getBucketId(CAMERA_IMAGE_BUCKET_NAME);
        //截屏照片的地址ID
        String SCREENSHOTS_IMAGE_BUCKET_ID = getBucketId(SCREENSHOTS_IMAGE_BUCKET_NAME);
        //查询路径和修改时间
        String[] projection = {MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_MODIFIED};
        //
        String selection = MediaStore.Images.Media.BUCKET_ID + " = ?";
        //
        String[] selectionArgs = {CAMERA_IMAGE_BUCKET_ID};
        String[] selectionArgsForScreenshots = {SCREENSHOTS_IMAGE_BUCKET_ID};

        //检查camera文件夹，查询并排序
        Pair<Long, String> cameraPair = null;
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                MediaStore.Files.FileColumns.DATE_MODIFIED + " DESC");
        if (cursor.moveToFirst()) {
            cameraPair = new Pair(cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED)),
                    cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
        }
        //检查Screenshots文件夹
        Pair<Long, String> screenshotsPair = null;
        //查询并排序
        cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgsForScreenshots,
                MediaStore.Files.FileColumns.DATE_MODIFIED + " DESC");

        if (cursor.moveToFirst()) {
            screenshotsPair = new Pair(cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED)),
                    cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        //对比
        if (cameraPair != null && screenshotsPair != null) {
            if (cameraPair.first > screenshotsPair.first) {
                screenshotsPair = null;
                Log.i(TAG, "getLatestPhoto: " + cameraPair.first + "," + cameraPair.second);
                return cameraPair;
            } else {
                cameraPair = null;
                Log.i(TAG, "getLatestPhoto2: " + screenshotsPair.first + "," + screenshotsPair.second);
                return screenshotsPair;
            }

        } else if (cameraPair != null && screenshotsPair == null) {
            Log.i(TAG, "getLatestPhoto3: " + cameraPair.first + "," + cameraPair.second);
            return cameraPair;

        } else if (cameraPair == null && screenshotsPair != null) {
            Log.i(TAG, "getLatestPhoto4: " + screenshotsPair.first + "," + screenshotsPair.second);
            return screenshotsPair;
        }
        return null;
    }

    private static String getBucketId(String path) {
        return String.valueOf(path.toLowerCase().hashCode());
    }

    /**
     * 获取截图路径
     *
     * @return
     */
    public static String getScreenshotsPath() {
        String path = Environment.getExternalStorageDirectory().toString() + "/DCIM/Screenshots";
        File file = new File(path);
        if (!file.exists()) {
            path = Environment.getExternalStorageDirectory().toString() + "/Pictures/Screenshots";
        }
        file = null;
        return path;
    }

    private class CameraThread extends Thread {
        @Override
        public void run() {
            super.run();
            Pair pair = getLatestPhoto(Camera1Activity.this);
            if (pair != null) {
                Bitmap bitmap = rotateBitmapByDegree(ImageUtils.ratio(String.valueOf(pair.second),
                        150, 150, null), 90);
                runOnUiThread(() -> {
                    pickPhoto.setImageBitmap(bitmap);
                });
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            dealAlbum(data);
        } else if (requestCode == CAMERA_REQUEST_CODE && resultCode == 0) {
            cameraPreview.setEnabled(true);
            Pair pair = getLatestPhoto(Camera1Activity.this);
            if (pair != null) {
                Abitmap = BitmapFactory.decodeFile(String.valueOf(pair.second));
                cropView.setBackground(new BitmapDrawable(getResources(), Abitmap));
                remake.setVisibility(View.VISIBLE);
            }
        }
    }

    private void dealAlbum(Intent data) {
        String path;
        try {
            Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            path = cursor.getString(columnIndex);  //获取照片路径
            cursor.close();
            Abitmap = rotateBitmapByDegree(BitmapFactory.decodeFile(path), readPictureDegress(path));
            cropView.setBackground(new BitmapDrawable(getResources(), Abitmap));
            //选中后，不可再旋转
            CAN_ROTATE = false;
            if (IS_VERTICAL) {
                cropView.setImageResource(R.drawable.vertical_crop_no);
            } else {
                cropView.setImageResource(R.drawable.horizontal_crop_no);
            }
            // TODO: 2019/3/5 接口处理
            if (true) {//如果处理失败
                remake.setVisibility(View.VISIBLE);
            } else {

            }

        } catch (Exception e) {
            // TODO Auto-generatedcatch block
            e.printStackTrace();
        } finally {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mOrEventListener.enable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOrEventListener.disable();
        //及时回收
        if (Abitmap != null && !Abitmap.isRecycled()) {
            Abitmap.recycle();
            Abitmap = null;
        }
    }


}
