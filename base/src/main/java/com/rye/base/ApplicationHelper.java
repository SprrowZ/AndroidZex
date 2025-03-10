package com.rye.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.rye.base.common.IBackgroundListener;

import java.util.ArrayList;
import java.util.List;


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

    private static List<IBackgroundListener> mBackgroundListeners;

    public static void registerLifeCycle() {
        Application application = BaseApplication.getInstance();
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
                // FileUtil.writeUserLog(TAG + activity.getLocalClassName());
                //   setGray(activity);
                notifyToFront();
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
                notifyToBackground();
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

    private static void notifyToBackground() {
        if (countActivity == 0 && mBackgroundListeners != null && mBackgroundListeners.size() > 0) {
            for (IBackgroundListener listener : mBackgroundListeners) {
                listener.onBackground();
            }
        }
    }

    private static void notifyToFront() {
        if (countActivity == 1 && mBackgroundListeners != null && mBackgroundListeners.size() > 0) {
            for (IBackgroundListener listener : mBackgroundListeners) {
                listener.onFront();
            }
        }
    }

    public static void registerBackgroundListener(IBackgroundListener listener) {
        if (mBackgroundListeners == null) {
            mBackgroundListeners = new ArrayList<>();
        }
        if (!mBackgroundListeners.contains(listener)) {
            mBackgroundListeners.add(listener);
        }
    }

    public static void unRegisterBackgroundListener(IBackgroundListener listener) {
        if (mBackgroundListeners == null) {
           return;
        }
        if (mBackgroundListeners.contains(listener)) {
            mBackgroundListeners.remove(listener);
        }
    }


}
