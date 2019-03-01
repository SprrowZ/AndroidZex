package com.rye.catcher.project.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created at 2019/2/21.
 *
 * @author Zzg
 * @function:
 */
public class IntentServiceEx extends IntentService {
    private static  final  String TAG="IntentServiceEx";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public IntentServiceEx(String name) {
        super(name);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ....");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
          String key=intent.getStringExtra("KEY");
          String value=intent.getStringExtra("VALUE");
        Log.d(TAG, "onHandleIntent: "+"KEY:"+key+",VALUE:"+value);
        try {
            Thread.sleep(2000);
            Log.i(TAG, "onHandleIntent: "+"zzg..");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
