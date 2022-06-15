package com.rye.opengl.course_y.other;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rye.opengl.R;
import com.rye.opengl.course_y.other.texture.GLMultiplyTextureView;
import com.rye.opengl.course_y.other.texture.GLTextureView;
import com.rye.opengl.course_y.other.texture.TextureRender;

public class TestTextureViewActivity extends AppCompatActivity {

    private GLTextureView mMainSurface;
    private LinearLayout mSubContainer;

    public static void start(Context context) {
        Intent intent = new Intent(context, TestTextureViewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_texture_view);

        mMainSurface = findViewById(R.id.main_gl_texture);
        mSubContainer = findViewById(R.id.sub_container);
        addSurfaces();
    }

    private void addSurfaces() {

        mMainSurface.getTextureRender().setOnRenderCreateListener(new TextureRender.OnRenderCreateListener() {
            @Override
            public void onRenderCreate(int textureId) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mSubContainer.getChildCount() > 1) {
                            mSubContainer.removeAllViews();
                        }
                        GLMultiplyTextureView multiplyTextureView = new GLMultiplyTextureView(TestTextureViewActivity.this);
                        multiplyTextureView.setTextureId(textureId);
                        multiplyTextureView.setSurfaceAndEglContext(null,mMainSurface.getEglContext());//上下文共享！！

                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT);
                        multiplyTextureView.setLayoutParams(lp);
                        mSubContainer.addView(multiplyTextureView);
                    }
                });
            }
        });
    }
}