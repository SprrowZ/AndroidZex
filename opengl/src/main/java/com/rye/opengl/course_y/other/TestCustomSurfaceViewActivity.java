package com.rye.opengl.course_y.other;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.rye.opengl.R;

public class TestCustomSurfaceViewActivity extends AppCompatActivity {
    public static void start(Context context) {
        Intent intent = new Intent(context, TestCustomSurfaceViewActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_custom_surface_view);
    }
}