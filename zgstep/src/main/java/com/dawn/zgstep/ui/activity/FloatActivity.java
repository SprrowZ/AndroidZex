package com.dawn.zgstep.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dawn.zgstep.R;
import com.rye.base.ApplicationHelper;
import com.rye.base.common.IBackgroundListener;


public class FloatActivity extends AppCompatActivity implements IBackgroundListener {
    private static final int ACTION_MANAGE_OVERLAY_PERMISSION = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float);
        test11();
    }

    public static void jump(Context context) {
        Intent intent = new Intent(context, FloatActivity.class);
        context.startActivity(intent);
    }

    public void startFloat(View view) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //检查是否已经授予权限
            if (!Settings.canDrawOverlays(FloatActivity.this)) {
                //若未授权则请求权限
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 0);
            } else {
                Toast.makeText(getApplicationContext(),
                        "Weconds已经获取到悬浮框权限！", Toast.LENGTH_LONG).show();
                jump11();
            }
        }
    }


    private void test11() {
        ApplicationHelper.registerBackgroundListener(this);
    }

    private void jump11() {
        Intent intent = new Intent(FloatActivity.this, FloatingWindowService.class);
        startService(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ACTION_MANAGE_OVERLAY_PERMISSION) {
            jump11();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("RRye","onResume...");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("RRye","onStop...");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("RRye","onDestroy...");

        ApplicationHelper.unRegisterBackgroundListener(this);
        //todo 先按照简单的来，极端case:-》浮窗打开时点击app图标

        Intent intent = new Intent(this,FloatingWindowService.class);
        stopService(intent);


    }

    @Override
    public void onBackground() {
        Log.i("RRye","onBackground......");
        jump11();
    }

    @Override
    public void onFront() {
        Log.i("RRye","onFront......");

    }
}