package com.rye.catcher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rye.catcher.BaseActivity;
import com.rye.catcher.BuildConfig;
import com.rye.catcher.R;
import com.rye.catcher.sdks.gmap.AmapAPI;
import com.rye.catcher.sdks.gmap.AmapResult;
import com.rye.catcher.utils.ToastUtils;
import com.rye.catcher.utils.permission.PermissionUtils;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZZG on 2018/9/7.
 */
public class SplashActivity extends BaseActivity {
    private static  final  String TAG="SplashActivity";
    @BindView(R.id.image)
     ImageView image;
     Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    startActivityByAlpha(new Intent(SplashActivity.this,MainActivityEx.class));
                    finish();
                    break;
            }
        }
    };



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_splash);
        ButterKnife.bind(this);
        initEx();

    }



    private void initEx() {
        //安装申请权限
        authority();
        Glide.with(this).load(R.drawable.ling).into(image);

    }

    private void authority() {
        PermissionUtils.requestPermission(this,"本地文件读写和手机状态需要此类权限,请去设置里授予权限",
                false,data ->{
                    Message message=new Message();
                    message.what=1;
                    mHandler.sendMessageDelayed(message,4000);
                },0,
                Permission.WRITE_EXTERNAL_STORAGE,Permission.CALL_PHONE,Permission.ACCESS_COARSE_LOCATION);
    }
    private void actions(){

    }

    @Override
    protected void onDestroy() {
        AmapAPI.getInstance().destroyLocation();
        super.onDestroy();
    }
}
