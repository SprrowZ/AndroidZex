package com.rye.catcher.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.rye.catcher.R;
import com.rye.catcher.utils.ToastUtils;

public class BlueToothActivity extends Activity {
    private static final String TAG="BlueToothActivity";
    private BluetoothAdapter adapter;
    private int REQUEST_BLUE_TOOTH=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_tooth);
        init();
    }
    private void init(){
        adapter=BluetoothAdapter.getDefaultAdapter();
        if (adapter!=null){
            ToastUtils.shortMsg("该设备支持蓝牙！");
        }else{
            Log.i(TAG, "init: 设备不支持蓝牙");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!adapter.isEnabled()){//开启蓝牙
            Intent intent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent,REQUEST_BLUE_TOOTH);
        }else{
            ToastUtils.shortMsg("蓝牙设备已开启");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_BLUE_TOOTH){
            ToastUtils.shortMsg("蓝牙设备已开启");

        }
    }
}
