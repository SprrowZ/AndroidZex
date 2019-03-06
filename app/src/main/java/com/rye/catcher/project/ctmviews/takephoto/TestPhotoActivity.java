package com.rye.catcher.project.ctmviews.takephoto;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.rye.catcher.R;

public class TestPhotoActivity extends AppCompatActivity {
    private ImageView imageView;
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
    public void takeID(View view) {
        takePhoto(CameraActivity2.TYPE_ID);
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
