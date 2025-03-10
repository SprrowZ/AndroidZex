package com.dawn.zgstep.ui.activity;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.dawn.zgstep.R;
import com.rye.base.BaseApplication;
import com.rye.base.utils.DensityUtil;

import java.util.List;

/**
 * Create by  [Rye]
 * <p>
 * at 2023/5/26 16:25
 */
public class FloatingWindowService extends Service {

    private WindowManager mWindowManager;
    private View mFloatingView;
    private WindowManager.LayoutParams layoutParams;
    private float x;
    private float y;

    private Button mCloseService;
    private Button mToFront;

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化 WindowManager
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        // 创建悬浮窗 View
        mFloatingView = LayoutInflater.from(this).inflate(R.layout.floating_window, null);

        mCloseService = mFloatingView.findViewById(R.id.close_service);
        mToFront = mFloatingView.findViewById(R.id.to_front);

        // 设置悬浮窗 View 的参数
        layoutParams = new WindowManager.LayoutParams(
                DensityUtil.dip2px(getApplicationContext(),150),
                DensityUtil.dip2px(getApplicationContext(),250),
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :
                        WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        // 添加悬浮窗 View
        mWindowManager.addView(mFloatingView, layoutParams);


        mFloatingView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        x = event.getRawX();
                        y = event.getRawY();
                    }
                    case MotionEvent.ACTION_MOVE: {
                        float currentX = event.getRawX();
                        float currentY = event.getRawY();
                        float offsetX = currentX - x;
                        float offsetY = currentY - y;
                        x = currentX;
                        y = currentY;
                        layoutParams.x = layoutParams.x + Math.round(offsetX);
                        layoutParams.y = layoutParams.y + Math.round(offsetY);
                        mWindowManager.updateViewLayout(mFloatingView, layoutParams);
                    }
                }
                return true;

            }
        });

        mCloseService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopSelf();
            }
        });
        mToFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               setTopApp(v.getContext());
               stopSelf();
            }
        });
    }

    public void setTopApp(Context context) {
        //获取ActivityManager
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //获得当前运行的task(任务)
        List<ActivityManager.RunningTaskInfo> taskInfoList = null;
        if (activityManager != null) {
            taskInfoList = activityManager.getRunningTasks(100);
        }
        if (taskInfoList != null) {
            for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                //找到本应用的 task，并将它切换到前台
                if (taskInfo.topActivity != null && taskInfo.topActivity.getPackageName().equals(context.getPackageName())) {
                    activityManager.moveTaskToFront(taskInfo.id, 0);
                    break;
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // 移除悬浮窗 View
        if (mFloatingView != null) {
            mWindowManager.removeView(mFloatingView);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

