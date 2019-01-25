package com.rye.catcher;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import com.rye.catcher.GreenDaos.Base.DaoMaster;
import com.rye.catcher.GreenDaos.Base.DaoSession;
import com.rye.catcher.utils.EchatAppUtil;
import com.tencent.bugly.crashreport.CrashReport;

import org.greenrobot.greendao.database.Database;

/**
 * Created by ZZG on 2018/3/22.
 */

public class zApplication extends Application{

    private static final String TAG="zApplication";


    private static Context mContext;
    private DisplayMetrics displayMetrics;
    private static zApplication instance=new zApplication();
    private boolean DAO_INITED=false;
    private DaoSession daoSession;
    //前后台判断
    private int countActivity=0;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;//赋值
        mContext = getApplicationContext();
        init();
        //如果不赋值，那么EchatAppUtil获取的context永远为空...数据库那里会崩掉
        EchatAppUtil.setContext(this);
    }
               //GreenDao初始化

    public static Context getContext() {
        return mContext;
    }

    private void init() {
        //bugly
        CrashReport.initCrashReport(getApplicationContext(), "8fdd156f75", false);
        //第三个参数为SDK调试模式开关，调试模式的行为特性如下：

        //输出详细的Bugly SDK的Log；
        //每一条Crash都会被立即上报；
        //自定义日志将会在Logcat中输出。
        //建议在测试阶段建议设置成true，发布时设置为false。
       // CrashReport.testJavaCrash();
        if (displayMetrics == null) {
            displayMetrics = getResources().getDisplayMetrics();
        }
        //初始化GreenDao
        if (!DAO_INITED) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db");
            Database db = helper.getWritableDb();
            daoSession = new DaoMaster(db).newSession();
            DAO_INITED = true;
        }

        //----------监听生命周期
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
              //如果为1，则说明从后台进入到前台
               countActivity++;
                Log.i(TAG, "onActivityStarted: "+countActivity);
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.i(TAG, "onActivityResumed: "+activity.getLocalClassName());
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
               countActivity--;
               //如果为0，说明程序已经运行在后台
                Log.i(TAG, "onActivityStopped: "+countActivity);
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }
    public DaoSession getDaoSession() {//获取dao实例
        return daoSession;
    }
    public void  initDb(){
        if (!DAO_INITED) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db");
            Database db = helper.getWritableDb();
            daoSession = new DaoMaster(db).newSession();
            DAO_INITED = true;
        }
    }
    public static zApplication getInstance() {
        return instance;
    }

    /**
     * 获取当前应用程序的版本号
     */
    public String getVersion() {
        String st = getResources().getString(R.string.Version_number_is_wrong);
        PackageManager pm = this.getPackageManager();
        try {
            PackageInfo packinfo = pm.getPackageInfo(this.getPackageName(), 0);
            return packinfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return st;
        }
    }


}
