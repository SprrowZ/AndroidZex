package com.rye.opengl.course_y.oes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;

import com.rye.opengl.R;
import com.rye.opengl.course_y.oes.camera.OESCameraView;

public class OESCameraActivity extends AppCompatActivity {
    private OESCameraView cameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oescamera);
        cameraView = findViewById(R.id.camera);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraView.onDestroy();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        cameraView.previewAngle(this);
    }
}