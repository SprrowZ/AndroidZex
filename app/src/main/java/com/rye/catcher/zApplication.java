package com.rye.catcher;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.rye.catcher.GreenDaos.Base.DaoMaster;
import com.rye.catcher.GreenDaos.Base.DaoSession;
import com.rye.catcher.utils.EchatAppUtil;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

import org.greenrobot.greendao.database.Database;

/**
 * Created by ZZG on 2018/3/22.
 */

public class zApplication extends TinkerApplication{
    private static Context mContext;
    private DisplayMetrics displayMetrics;
    private static zApplication instance=new zApplication();
    private boolean DAO_INITED=false;
    private DaoSession daoSession;

    public zApplication() {
        super(
                //tinkerFlags, tinker支持的类型，dex,library，还是全部都支持！
                ShareConstants.TINKER_ENABLE_ALL,
                //ApplicationLike的实现类，只能传递字符串
                "com.rye.catcher.sdks.tinker.CustomTinkerLike",//不要忘了替换
                //Tinker的加载器，一般来说用默认的即可
                "com.tencent.tinker.loader.TinkerLoader",
                //tinkerLoadVerifyFlag, 运行加载时是否校验dex与,ib与res的Md5
                false);
    }


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
