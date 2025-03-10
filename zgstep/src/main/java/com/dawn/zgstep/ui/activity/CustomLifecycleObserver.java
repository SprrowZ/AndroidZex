package com.dawn.zgstep.ui.activity;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * Create by  [Rye]
 * <p>
 * at 2023/5/29 14:44
 */
public class CustomLifecycleObserver implements LifecycleObserver {
    // 方法名随便取，注解才是重点
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onForeground() {
        Log.i("LifecycleObserver", "应用回到前台");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onBackground() {
        Log.i("LifecycleObserver", "应用退到后台");
    }

}
