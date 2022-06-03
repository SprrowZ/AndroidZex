package com.rye.opengl.course_y.other;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.opengl.GLES20;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.rye.opengl.R;
import com.rye.opengl.course_y.EglHelper;

/**
 * 测试自定义EGLHelper，成功：颜色渲染
 */
public class TestEGLHelperActivity extends AppCompatActivity {
    private SurfaceView mSurfaceView;

    public static void start(Context context) {
        Intent intent = new Intent(context, TestEGLHelperActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course1_demo);
        mSurfaceView = findViewById(R.id.demo_surface);
        init();
    }

    private void init() {
        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
                new Thread() {
                    @Override
                    public void run() {//测试工程
                        super.run();
                        EglHelper eglHelper = new EglHelper();
                        eglHelper.initEgl(holder.getSurface(), null);
                        while (true) {
                            GLES20.glViewport(0,0,width,height);
                            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
                            GLES20.glClearColor(0.0f,1.0f,0.0f,1.0f);
                            eglHelper.swapBuffers();
                            try {
                                Thread.sleep(16);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

            }
        });
    }
}