package com.rye.opengl.course_y.other;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.rye.opengl.R;

public class TestTextureViewActivity extends AppCompatActivity {
    public static void start(Context context) {
        Intent intent = new Intent(context, TestTextureViewActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_texture_view);
    }
}