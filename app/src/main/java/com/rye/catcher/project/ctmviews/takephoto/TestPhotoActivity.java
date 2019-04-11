package com.rye.catcher.project.ctmviews.takephoto;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.rye.catcher.R;
import com.rye.catcher.utils.SDHelper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestPhotoActivity extends AppCompatActivity {
    private ImageView imageView;

    private static final int CAMERA_REQUEST_CODE=99;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_photo);
        imageView = (ImageView) findViewById(R.id.main_image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CameraActivity2.REQUEST_CODE && resultCode == CameraActivity2.RESULT_CODE) {
            //获取文件路径，显示图片
            final String path = CameraActivity2.getResult(data);
            if (!TextUtils.isEmpty(path)) {
                imageView.setImageBitmap(BitmapFactory.decodeFile(path));
            }
        }else if (requestCode==CAMERA_REQUEST_CODE&&resultCode==RESULT_OK){
            if (data!=null){
                imageView.setImageBitmap(BitmapFactory.decodeFile(getImagePath()));
            }

        }
    }

    /**
     * 拍摄身份证
     *
     * @param type 拍摄证件类型
     */
    private void takePhoto(int type) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0x12);
            return;
        }
        CameraActivity2.openCamera(this, type);
    }

    /**
     * 身份证
     */
    public void takePhoto(View view) {
        //拍照页面
         Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
         File file=new File(getImagePath());
         Uri uri;
         //低于7.0用file://
         if (Build.VERSION.SDK_INT<Build.VERSION_CODES.N){
               uri=Uri.fromFile(file);
         }else{
             uri= FileProvider.getUriForFile(this,this.getApplicationContext().getPackageName()+".provider",file);
         }
         intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
         startActivityForResult(intent,CAMERA_REQUEST_CODE);

    }

    private String getImagePath(){
        Date data=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yy-MM-DD%HH:mm:ss");
        String dataStr=sdf.format(data);
        String imagePath=SDHelper.getImageFolder()+dataStr+".png";
        return imagePath;
    }

    /**
     * 证件
     */
    public void takeCard(View view) {
        takePhoto(CameraActivity2.TYPE_CARD);
    }

    /**
     * 发票
     */
    public void takeInvoice(View view) {
        takePhoto(CameraActivity2.TYPE_INVOICE);
    }

    /**
     * 驾照
     */
    public void takeLicense(View view) {
        takePhoto(CameraActivity2.TYPE_LICENSE);
    }


}
