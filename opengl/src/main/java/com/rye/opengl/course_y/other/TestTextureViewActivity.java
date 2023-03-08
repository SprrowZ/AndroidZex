package com.rye.opengl.course_y.other;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.rye.opengl.R;
import com.rye.opengl.course_y.other.texture.GLMultiplyTextureView;
import com.rye.opengl.course_y.other.texture.GLTextureView;
import com.rye.opengl.course_y.other.texture.TextureRender;
import com.rye.opengl.course_y.other.texture.test.WlTextureRender;

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //底部导航栏
            //   getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
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
                        for (int i=0;i<3;i++){ //添加3个

                            GLMultiplyTextureView multiplyTextureView = new GLMultiplyTextureView(TestTextureViewActivity.this);
                            multiplyTextureView.setTextureId(textureId,i);
                            multiplyTextureView.setSurfaceAndEglContext(null,mMainSurface.getEglContext());//上下文共享！！

                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT);
                            lp.width = 200;
                            lp.height = 300;
                            multiplyTextureView.setLayoutParams(lp);
                            mSubContainer.addView(multiplyTextureView);
                        }

                    }
                });
            }
        });
    }
}