package com.rye.catcher;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import com.github.moduth.blockcanary.BlockCanary;
import com.rye.catcher.GreenDaos.Base.DaoMaster;
import com.rye.catcher.GreenDaos.Base.DaoSession;
import com.rye.catcher.base.dbs.SchemasModule;
import com.rye.catcher.base.sdks.AppBlockCanaryContext;
import com.rye.catcher.utils.CrashHandler;

import com.tencent.bugly.crashreport.CrashReport;
//import com.umeng.commonsdk.UMConfigure;

import org.greenrobot.greendao.database.Database;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ThirdSdk {
    private static ThirdSdk INSTANCE=null;
    private boolean DAO_INITED=false;
    private DaoSession daoSession;

    private DisplayMetrics displayMetrics;
    public static ThirdSdk getInstance(){
        if (INSTANCE==null){
            synchronized (ThirdSdk.class){
                if (INSTANCE==null){
                    INSTANCE=new ThirdSdk();
                }
            }
        }
        return INSTANCE;
    }


    public void initSdk(Application context){
        //友盟统计
//        UMConfigure.init(context,UMConfigure.DEVICE_TYPE_PHONE,"");
        //UnCaughtException
        Thread.setDefaultUncaughtExceptionHandler(CrashHandler.Companion.getInstance());
        //realm
        initRealm(context);

        //in
        initOther(context);
        //内存泄露检测LeakCanary
//

        BlockCanary.install(context, new AppBlockCanaryContext()).start();
    }

    private void initRealm(Application context){
        //Realm
        Realm.init(context);
        RealmConfiguration configuration=new RealmConfiguration.Builder()
                .name("rye.realm")
                .modules(new SchemasModule())
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(configuration);
    }

    private void initOther(Application context){
        //bugly
        CrashReport.initCrashReport(context, "8fdd156f75", false);
        //第三个参数为SDK调试模式开关，调试模式的行为特性如下：

        //输出详细的Bugly SDK的Log；
        //每一条Crash都会被立即上报；
        //自定义日志将会在Logcat中输出。
        //建议在测试阶段建议设置成true，发布时设置为false。
        // CrashReport.testJavaCrash();
        if (displayMetrics == null) {
            displayMetrics = context.getResources().getDisplayMetrics();
        }
        //初始化GreenDao
        if (!DAO_INITED) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "notes-db");
            Database db = helper.getWritableDb();
            daoSession = new DaoMaster(db).newSession();
            DAO_INITED = true;
        }
    }

    public DaoSession getDaoSession() {//获取dao实例
        return daoSession;
    }

}
