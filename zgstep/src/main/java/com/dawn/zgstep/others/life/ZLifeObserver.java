package com.dawn.zgstep.others.life;

import android.app.Activity;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * Create by rye
 * at 2020-10-30
 *
 * @description:
 */
public class ZLifeObserver  implements LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onActivityCreate(){
        Log.i("Rye","onActivityCreate...");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onActivityResume(){
        Log.i("Rye","onActivityResume...");
    }

    private void getLifecycle(AppCompatActivity activity){
        activity.getLifecycle().addObserver(this);
    }
}
