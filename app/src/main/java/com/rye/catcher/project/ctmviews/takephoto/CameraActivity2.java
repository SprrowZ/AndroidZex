package com.rye.catcher.project.ctmviews.takephoto;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rye.catcher.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by smartown on 2018/2/24 11:46.
 * <br>
 * Desc:
 * <br>
 * 拍照界面
 */
public class CameraActivity2 extends Activity implements View.OnClickListener {

    /**
     * 拍摄类型-身份证正面
     */
    public final static int TYPE_IDCARD_FRONT = 1;
    /**
     * 拍摄类型-身份证反面
     */
    public final static int TYPE_IDCARD_BACK = 2;
    /**
     * 拍摄类型-竖版营业执照
     */
    public final static int TYPE_COMPANY_PORTRAIT = 3;
    /**
     * 拍摄类型-横版营业执照
     */
    public final static int TYPE_COMPANY_LANDSCAPE = 4;

    public final static int REQUEST_CODE = 0X13;
    public final static int RESULT_CODE = 0X14;


    public static void openCertificateCamera(Activity activity, int type) {
        Intent intent = new Intent(activity, CameraActivity2.class);
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
    private ImageView flashImageView;
    private View optionView;
    private View resultView;

    //顶部四个布局
    private LinearLayout idContainer;
    private LinearLayout cardContainer;
    private LinearLayout invoiceContainer;
    private LinearLayout licenseContainer;
    //四个布局内部
    private ImageView idImg;
    private ImageView cardImg;
    private ImageView invoiceImg;
    private ImageView licenseImg;

    private TextView idText;
    private TextView cardText;
    private TextView invoiceText;
    private TextView licenseText;


    //哪个图标被选中
    private int type=1;
    //屏幕宽度
    private float screenWidth;
    //当前位置
    private int currentPos=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        initView();
        initEvent();
    }
    private void initView(){
        //type表示是哪个被选中了
     //   type = getIntent().getIntExtra("type", 0);
        cameraPreview = findViewById(R.id.camera_surface);
        //中间布局
        containerView = findViewById(R.id.camera_crop_container);
        //裁剪布局
        cropView =  findViewById(R.id.camera_crop);
        //顶部四个按钮
        idContainer=findViewById(R.id.idContainer);
        cardContainer=findViewById(R.id.cardContainer);
        invoiceContainer=findViewById(R.id.invoiceContainer);
        licenseContainer=findViewById(R.id.licenseContainer);

        idImg=findViewById(R.id.idImg);
        cardImg=findViewById(R.id.cardImg);
        invoiceImg=findViewById(R.id.invoiceImg);
        licenseImg=findViewById(R.id.licenseImg);

        idText=findViewById(R.id.idText);
        cardText=findViewById(R.id.cardText);
        invoiceText=findViewById(R.id.invoiceText);
        licenseText=findViewById(R.id.licenseText);


        //设置crop图片
        flashImageView = (ImageView) findViewById(R.id.camera_flash);
        optionView = findViewById(R.id.camera_option);
        resultView = findViewById(R.id.camera_result);



        cameraPreview.setOnClickListener(this);
        findViewById(R.id.camera_close).setOnClickListener(this);
        findViewById(R.id.camera_take).setOnClickListener(this);
        flashImageView.setOnClickListener(this);
        findViewById(R.id.camera_result_ok).setOnClickListener(this);
        findViewById(R.id.camera_result_cancel).setOnClickListener(this);
    }
     private void initEvent(){

         screenWidth = Math.min(getResources().getDisplayMetrics().widthPixels,
                 getResources().getDisplayMetrics().heightPixels);
         //长宽比为标准的16:9
//        float screenHeight = screenWidth / 9.0f * 16.0f;

         //获取屏幕方向，设置crop图片
         if (getOrientation()){
             verticalView();
         }else{
             horizontalView();
         }
         //获取type，判定哪个item应该被选中

     }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.camera_surface:
                cameraPreview.focus();
                break;
            case R.id.camera_close:
                finish();
                break;
            case R.id.camera_take:
                takePhoto();
                break;
            case R.id.camera_flash:
                boolean isFlashOn = cameraPreview.switchFlashLight();
                flashImageView.setImageResource(isFlashOn ? R.mipmap.camera_flash_on : R.mipmap.camera_flash_off);
                break;
            case R.id.camera_result_ok:
                goBack();
                break;
            case R.id.camera_result_cancel:
                optionView.setVisibility(View.VISIBLE);
                cameraPreview.setEnabled(true);
                resultView.setVisibility(View.GONE);
                cameraPreview.startPreview();
                break;
        }
    }

    /**
     * 获取屏幕方向，如果是竖屏，返回true
     * @return
     */
    private boolean getOrientation(){
        if (this.getResources().getConfiguration().orientation==
                Configuration.ORIENTATION_PORTRAIT){
            return true;
        }else{
            return false;
        }

    }

    /**
     * 竖屏
     */
    private void verticalView(){
        cropView.setImageResource(R.drawable.vertical_crop);
        float width = (int) (screenWidth * 0.8);
        float height = (int) (width * 0.8f);
        RelativeLayout.LayoutParams cropParams = new RelativeLayout.LayoutParams((int) width, (int) height);
        cropParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        cropView.setLayoutParams(cropParams);
    }

    /**
     * 横屏
     */
    private void horizontalView(){

        cropView.setImageResource(R.drawable.horizontal_crop);
        float width = (int) (screenWidth * 0.7);
        float height = (int) (width * 1.6f);
        RelativeLayout.LayoutParams cropParams = new RelativeLayout.LayoutParams((int) width, (int) height);
        cropParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        cropView.setLayoutParams(cropParams);
    }


    /**
     * 识别中
     */
    private void distinguishing(){
        float width = (int) (screenWidth * 0.7);
        float height = (int) (width * 1.6f);
        RelativeLayout.LayoutParams cropParams = new RelativeLayout.LayoutParams((int) width, (int) height);
        cropParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        cropView.setLayoutParams(cropParams);
    }

    /**
     * 还原顶部布局
     */

    @TargetApi(Build.VERSION_CODES.M)
    private void restoreItem(){
        ColorStateList csl = getResources().getColorStateList(R.color.white,null);
        idImg.setImageTintList(csl);
        cardImg.setImageTintList(csl);
        invoiceImg.setImageTintList(csl);
        licenseImg.setImageTintList(csl);

        idText.setBackgroundTintList(csl);
        cardText.setBackgroundTintList(csl);
        invoiceText.setBackgroundTintList(csl);
        licenseText.setBackgroundTintList(csl);

    }

    /**
     * 选中布局
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void selectedItem(int id){
        //如果是当前位置，直接返回
        if (currentPos==id) return;
        currentPos=id;
        switch (id){
            case 1:
                restoreItem();
                idImg.setImageTintList(null);
                idText.setBackgroundTintList(null);
                break;
            case 2:
                restoreItem();
                cardImg.setImageTintList(null);
                cardText.setBackgroundTintList(null);
                break;
            case 3:
                restoreItem();
                invoiceImg.setImageTintList(null);
                invoiceText.setBackgroundTintList(null);
                break;
            case 4:
                restoreItem();
                licenseImg.setImageTintList(null);
                licenseText.setBackgroundTintList(null);
                break;
        }
    }



    private void takePhoto() {
        optionView.setVisibility(View.GONE);
        cameraPreview.setEnabled(false);
        cameraPreview.takePhoto(new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(final byte[] data, Camera camera) {
                camera.stopPreview();
                //子线程处理图片，防止ANR
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            File originalFile = getOriginalFile();
                            FileOutputStream originalFileOutputStream = new FileOutputStream(originalFile);
                            originalFileOutputStream.write(data);
                            originalFileOutputStream.close();

                            Bitmap bitmap = BitmapFactory.decodeFile(originalFile.getPath());

                            //计算裁剪位置
                            float left, top, right, bottom;
                            if (type == TYPE_COMPANY_PORTRAIT) {
                                left = (float) cropView.getLeft() / (float) cameraPreview.getWidth();
                                top = ((float) containerView.getTop() - (float) cameraPreview.getTop()) / (float) cameraPreview.getHeight();
                                right = (float) cropView.getRight() / (float) cameraPreview.getWidth();
                                bottom = (float) containerView.getBottom() / (float) cameraPreview.getHeight();
                            } else {
                                left = ((float) containerView.getLeft() - (float) cameraPreview.getLeft()) / (float) cameraPreview.getWidth();
                                top = (float) cropView.getTop() / (float) cameraPreview.getHeight();
                                right = (float) containerView.getRight() / (float) cameraPreview.getWidth();
                                bottom = (float) cropView.getBottom() / (float) cameraPreview.getHeight();
                            }
                            //裁剪及保存到文件
                            Bitmap cropBitmap = Bitmap.createBitmap(bitmap,
                                    (int) (left * (float) bitmap.getWidth()),
                                    (int) (top * (float) bitmap.getHeight()),
                                    (int) ((right - left) * (float) bitmap.getWidth()),
                                    (int) ((bottom - top) * (float) bitmap.getHeight()));

                            final File cropFile = getCropFile();
                            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(cropFile));
                            cropBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                            bos.flush();
                            bos.close();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    resultView.setVisibility(View.VISIBLE);
                                }
                            });
                            return;
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                optionView.setVisibility(View.VISIBLE);
                                cameraPreview.setEnabled(true);
                            }
                        });
                    }
                }).start();

            }
        });
    }

    /**
     * @return 拍摄图片原始文件
     */
    private File getOriginalFile() {
        switch (type) {
            case TYPE_IDCARD_FRONT:
                return new File(getExternalCacheDir(), "idCardFront.jpg");
            case TYPE_IDCARD_BACK:
                return new File(getExternalCacheDir(), "idCardBack.jpg");
            case TYPE_COMPANY_PORTRAIT:
            case TYPE_COMPANY_LANDSCAPE:
                return new File(getExternalCacheDir(), "companyInfo.jpg");
        }
        return new File(getExternalCacheDir(), "picture.jpg");
    }

    /**
     * @return 拍摄图片裁剪文件
     */
    private File getCropFile() {
        switch (type) {
            case TYPE_IDCARD_FRONT:
                return new File(getExternalCacheDir(), "idCardFrontCrop.jpg");
            case TYPE_IDCARD_BACK:
                return new File(getExternalCacheDir(), "idCardBackCrop.jpg");
            case TYPE_COMPANY_PORTRAIT:
            case TYPE_COMPANY_LANDSCAPE:
                return new File(getExternalCacheDir(), "companyInfoCrop.jpg");
        }
        return new File(getExternalCacheDir(), "pictureCrop.jpg");
    }

    /**
     * 点击对勾，使用拍照结果，返回对应图片路径
     */
    private void goBack() {
        Intent intent = new Intent();
        intent.putExtra("result", getCropFile().getPath());
        setResult(RESULT_CODE, intent);
        finish();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onConfigurationChanged(newConfig);
        Log.i("ConfigurationChanged", "onConfigurationChanged: ....");
    }
}
