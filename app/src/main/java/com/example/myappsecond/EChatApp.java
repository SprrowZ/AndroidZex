package com.example.myappsecond;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.myappsecond.GreenDaos.Base.DaoMaster;
import com.example.myappsecond.GreenDaos.Base.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by ZZG on 2018/3/22.
 */

public class EChatApp extends Application{
    private static Context mContext;
    private DisplayMetrics displayMetrics;
    private static EChatApp instance=new EChatApp();
    private boolean DAO_INITED=false;
    private DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;//赋值
        mContext = getApplicationContext();
        init();
    }
               //GreenDao初始化

    public static Context getContext() {
        return mContext;
    }

    private void init() {
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
    public static EChatApp getInstance() {
        return instance;
    }


    public int getVersionCode() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            int versionCode = info.versionCode;
            return versionCode;
        } catch (Exception e) {
            Log.e("zzg", e.getMessage(), e);
            return 0;
        }
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

    /**
     * @return
     * @Description： 获取当前屏幕的宽度
     */
    public int getWindowWidth() {
        return displayMetrics.widthPixels;
    }

    /**
     * @return
     * @Description： 获取当前屏幕的高度
     */
    public int getWindowHeight() {
        return displayMetrics.heightPixels;
    }

    /**
     * @return 当前时间。格式为：20140506120801
     */
    private static String getCurrentDateString() {
        return com.example.myappsecond.utils.DateUtils.getCurrentTime("yyyyMMddHHmmssSSS");
    }
}
