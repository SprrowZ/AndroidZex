package com.rye.base;

import android.app.Application;

/**
 * Created By RyeCatcher
 * at 2019/10/17
 */
public class BaseApplication extends Application {
    private static  BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static BaseApplication getInstance(){
        return instance;
    }
}
