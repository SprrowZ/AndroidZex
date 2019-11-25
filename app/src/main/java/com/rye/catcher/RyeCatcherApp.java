package com.rye.catcher;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import com.rye.base.BaseApplication;
import com.rye.catcher.GreenDaos.Base.DaoMaster;
import com.rye.catcher.GreenDaos.Base.DaoSession;

import com.rye.catcher.base.OverallHandler;
import com.rye.catcher.base.dbs.SchemasModule;
import com.rye.catcher.utils.CrashHandler;
import com.rye.catcher.utils.FileUtil;
import com.rye.catcher.utils.Old_ApplicationUtil;

import com.rye.base.utils.FileUtils;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.commonsdk.UMConfigure;

import org.greenrobot.greendao.database.Database;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by ZZG on 2018/3/22.
 */

public class RyeCatcherApp extends BaseApplication {

    private static final String TAG="RyeCatcherApp";
    private static final String LIFECYCLE_LOG="LIFECYCLE_LOG:";

    private static Context mContext;
    private DisplayMetrics displayMetrics;
    private static RyeCatcherApp instance;
    private boolean DAO_INITED=false;
    private DaoSession daoSession;
    //前后台判断
    private int countActivity=0;

    //全局Handler
    private  OverallHandler overallHandler=null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;//赋值
        mContext = getApplicationContext();
        init();
        //如果不赋值，那么EchatAppUtil获取的context永远为空...数据库那里会崩掉
        Old_ApplicationUtil.setContext(mContext);

        //友盟统计
        UMConfigure.init(this,UMConfigure.DEVICE_TYPE_PHONE,"");
        //UnCaughtException
        Thread.setDefaultUncaughtExceptionHandler(CrashHandler.Companion.getInstance());
       //realm
        initRealm(this);
        //内存泄露检测LeakCanary
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    private void initRealm(Application application){
        //Realm
        Realm.init(application);
        RealmConfiguration configuration=new RealmConfiguration.Builder()
                .name("rye.realm")
                .modules(new SchemasModule())
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(configuration);
    }
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
            //打开的Activity数量统计
            private int activityCount = 0;
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
              //如果为1，则说明从后台进入到前台
                countActivity++;
                Log.i(TAG, "onActivityStarted: "+countActivity);
                FileUtil.writeUserLog(LIFECYCLE_LOG+activity.getLocalClassName());
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

        //----------全局Handler
        if (overallHandler==null){
            overallHandler=new OverallHandler(this);
        }
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
    //单例
    public static RyeCatcherApp getInstance() {
        return instance;
    }
    //获取全局Handler
    public   OverallHandler getHandler() {
        return overallHandler;
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
