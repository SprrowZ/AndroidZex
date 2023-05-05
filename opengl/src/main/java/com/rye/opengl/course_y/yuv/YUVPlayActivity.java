package com.rye.opengl.course_y.yuv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.rye.base.utils.SDHelper;
import com.rye.opengl.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * 播放yuv数据
 */
public class YUVPlayActivity extends AppCompatActivity {

    private YuvView mYuvView;

    private FileInputStream fis;
    public static  void jump(Context context) {
        Intent intent = new Intent(context,YUVPlayActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuvplay);
        mYuvView = findViewById(R.id.yuv_view);

    }

    public void start(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int w =640;
                    int h = 300;
                    fis = new FileInputStream(new File(SDHelper.getAppExternal(),"test.yuv"));//本地要存储此视频
                    byte[] y = new byte[w*h];
                    byte[] u = new byte[w*h/4];
                    byte[] v = new byte[w*h/4];

                    while(true) {
                        int  ry = fis.read(y);
                        int  ru = fis.read(u);
                        int  rv = fis.read(v);
                        if (ry>0 && ru>0 && rv>0) {
                           mYuvView.setFrameData(w,h,y,u,v);
                           Thread.sleep(40);
                        } else {
                            Log.e("RRye","渲染完成");
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}