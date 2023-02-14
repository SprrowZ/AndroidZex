package com.rye.opengl.course_y.oes;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.rye.opengl.R;

/**
 * OpenGL摄像头预览
 */
public class TestOESActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_oesactivity);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
//                requestPermissions(new String[] {Manifest.permission.CAMERA}, 1);
    }

    public static  void start(Context context) {
        Intent intent = new Intent(context,TestOESActivity.class);
        context.startActivity(intent);
    }

    public void cameraPreview(View view) {
        Intent intent = new Intent(this,OESCameraActivity.class);
        startActivity(intent);
    }
}