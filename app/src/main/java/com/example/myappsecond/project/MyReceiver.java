package com.example.myappsecond.project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by ZZG on 2017/10/24.
 */

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
Toast.makeText(context,"广播已经接受到，执行跳转操作",Toast.LENGTH_SHORT).show();
    }
}
