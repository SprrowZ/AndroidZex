package com.rye.catcher.base.helpers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.rye.catcher.RyeCatcherApp;
import com.rye.catcher.utils.FileUtil;

/**
 * Create by rye
 * at 2021/1/23
 *
 * @description:
 */
public class ApplicationHelper {
    private static final String TAG = ":AppLifeCycleHelper";

    //前后台判断
    private static int countActivity = 0;

    private static Paint mPaint;

    public static void registerLifeCycle() {
        Application application = RyeCatcherApp.getInstance();
        //----------监听生命周期
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            //打开的Activity数量统计
            private int activityCount = 0;

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                //如果为1，则说明从后台进入到前台
                countActivity++;
                Log.i(TAG, "onActivityStarted: " + countActivity);
                FileUtil.writeUserLog(TAG + activity.getLocalClassName());
                //   setGray(activity);
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.i(TAG, "onActivityResumed: " + activity.getLocalClassName());

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                countActivity--;
                //如果为0，说明程序已经运行在后台
                Log.i(TAG, "onActivityStopped: " + countActivity);
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    @SuppressLint("ResourceAsColor")
    private void setGray(Activity activity) {
        if (mPaint == null) {
            mPaint = new Paint();
        }
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0f);
        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        activity.getWindow().getDecorView().setLayerType(View.LAYER_TYPE_HARDWARE, mPaint);
    }

}
