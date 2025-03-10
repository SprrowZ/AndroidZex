package com.rye.base;

import android.app.Application;
import android.os.StrictMode;

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
        ApplicationHelper.registerLifeCycle();
        openStrictMode();
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    private void openStrictMode() {
        if (BuildConfig.DEBUG) { // 确保只在调试构建中启用
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .penaltyLog() // 将违规记录到logcat
                    .build();

            StrictMode.VmPolicy vmPolicy = new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog() // 同样记录到logcat
                    .build();

            StrictMode.setThreadPolicy(policy);
            StrictMode.setVmPolicy(vmPolicy);
        }
    }
}
