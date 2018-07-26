package com.example.myappsecond.project.Services;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myappsecond.BaseActivity;
import com.example.myappsecond.R;

/**
 * Created by Zzg on 2018/2/25.
 */

public class ServiceMainActivity extends BaseActivity implements View.OnClickListener {
    public static final String TAG="ServiceMainActivity";
   private  Button startService;
   private  Button stopService;
   private  Button bindService;
   private  Button unbindService;
    //与Service进行绑定
    private ServiceTest.MyBinder myBinder;
    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //实例化service
            myBinder=(ServiceTest.MyBinder)iBinder;
            //开始调用Service中的方法
            myBinder.startDownload();
            Log.i(TAG, "onServiceConnected: -----------bindService---------");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(TAG, "onServiceDisconnected: --------unbindService--------");
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_main);
        initView();
    }

    private void initView() {
        startService=findViewById(R.id.startService);
        stopService=findViewById(R.id.stopService);
        bindService=findViewById(R.id.bindService);
        unbindService=findViewById(R.id.unbindService);
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.startService:
                //开启Service
                Intent intent1=new Intent(this,ServiceTest.class);
                startService(intent1);
                break;
            //关闭Service
            case R.id.stopService:
                Intent intent2=new Intent(this,ServiceTest.class);
                stopService(intent2);
                break;
            case R.id.bindService:
                Intent intent3=new Intent(this,ServiceTest.class);
                bindService(intent3,connection,BIND_AUTO_CREATE);
                break;
            case R.id.unbindService:
                unbindService(connection);
                break;
        }
    }
}
