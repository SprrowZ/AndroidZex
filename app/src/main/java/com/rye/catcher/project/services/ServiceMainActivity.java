package com.rye.catcher.project.services;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.rye.catcher.BaseActivity;
import com.rye.catcher.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Zzg on 2018/2/25.
 */

public class ServiceMainActivity extends BaseActivity {
    public static final String TAG = "ServiceMainActivity";
    @BindView(R.id.startService)
    Button startService;
    @BindView(R.id.stopService)
    Button stopService;
    @BindView(R.id.service)
    Button service;
    @BindView(R.id.sService)
    Button sService;
    @BindView(R.id.bindService)
    Button bindService;
    @BindView(R.id.unbindService)
    Button unbindService;

    //与Service进行绑定
    private LocalService.MyBinder myBinder;
    //IBinder
    //ServiceConnection用于绑定客户端和service
    //进度监控
    private ServiceConnection connection = new ServiceConnection() {
        //服务绑定成功后调用，毕竟onBind方法只执行一次，绑定成功/失败调用下面的两个方法
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //实例化service
            myBinder = (LocalService.MyBinder) iBinder;
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
        ButterKnife.bind(this);

    }
    @OnClick({R.id.startService, R.id.stopService, R.id.service,
            R.id.sService, R.id.bindService, R.id.unbindService})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.startService:
                //开启Service
                Intent intent1 = new Intent(this, LocalService.class);
                startService(intent1);
                break;
            case R.id.stopService:
                Intent intent2 = new Intent(this, LocalService.class);
                stopService(intent2);
                break;
            case R.id.service:
                break;
            case R.id.sService:
                break;
            case R.id.bindService:
                Intent intent3 = new Intent(this, LocalService.class);
                bindService(intent3, connection, BIND_AUTO_CREATE);
                break;
            case R.id.unbindService:
                unbindService(connection);
                break;
        }
    }
}
