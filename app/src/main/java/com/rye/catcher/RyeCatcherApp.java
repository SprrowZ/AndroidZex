package com.rye.catcher;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.rye.base.BaseApplication;


import com.rye.catcher.base.OverallHandler;

import com.rye.catcher.utils.FileUtil;
import com.rye.catcher.utils.Old_ApplicationUtil;


/**
 * Created by ZZG on 2018/3/22.
 */

public class RyeCatcherApp extends BaseApplication {

    private static final String TAG="RyeCatcherApp";
    private static final String LIFECYCLE_LOG="LIFECYCLE_LOG:";

    private static Context mContext;

    private static RyeCatcherApp instance;

    private static Paint mPaint;

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

        ThirdSdk.getInstance().initSdk(this);
    }


    public static Context getContext() {
        return mContext;
    }

    private void init() {


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
             //   setGray(activity);
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

    @SuppressLint("ResourceAsColor")
    private void setGray(Activity activity){
        if (mPaint == null) {
            mPaint = new Paint();
        }
         ColorMatrix colorMatrix = new ColorMatrix();
         colorMatrix.setSaturation(0f);
         mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        activity.getWindow().getDecorView().setLayerType(View.LAYER_TYPE_HARDWARE,mPaint);
    }


}
