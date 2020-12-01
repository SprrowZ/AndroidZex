package com.rye.catcher.project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.rye.catcher.activity.MainActivity;

/**
 * Create by rye
 * at 2020-10-19
 *
 * @description:
 */
public class SelfStartingReceivers extends BroadcastReceiver {
    private static final  String TAG = "SelfStartingReceivers";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"收到系统启动广播。。。");
        Intent intent1 = new Intent(context, MainActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }
}
