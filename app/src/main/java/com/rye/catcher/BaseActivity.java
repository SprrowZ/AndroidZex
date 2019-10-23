package com.rye.catcher;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.MutableMaprye.base.utils.LanguageUtil;
import com.rye.base.common.LanguageConstants;
import com.rye.catcher.base.NetChangeReceiver;
import com.rye.catcher.base.OverallHandler;
import com.rye.base.utils.DialogUtil;
import com.rye.catcher.utils.PermissionsUtil;
import com.rye.catcher.utils.SharedPreManager;
import com.umeng.analytics.MobclickAgent;

import java.lang.ref.WeakReference;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ZZG on 2017/10/23.
 */

public class BaseActivity extends AppCompatActivity {
    private static final String TAG=BaseActivity.class.getName();

    //监听屏幕系统广播
    private ScreenBroadcastReceiver mScreenReceiver;
    //监听网络系统广播
    private NetChangeReceiver  mNetReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //沉浸式状态栏
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //底部导航栏
         //   getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        com.rye.catcher.base.ActivityManager.getInstance().addActivity(this);
        //系统广播接受者
        mScreenReceiver=new ScreenBroadcastReceiver();
        //网络变化广播监听
        monitorNet();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在开屏的时候清掉所有,这样从后台到前台，就会把延迟的handler的处理去掉
        RyeCatcherApp.getInstance().getHandler().removeMessages(OverallHandler.exit);
        //友盟Session统计
        MobclickAgent.onResume(this);
    }

    /**
     * 设置标题栏标题
     */
    public void setBarTitle(String title) {
        TextView view = findViewById(R.id.title);
        if (view != null) {
            view.setText(title);
        }
    }

    public void setBarTitle(int resId) {
        TextView view = findViewById(R.id.title);
        if (view != null) {
            view.setText(resId);
        }
    }

    public String getBarTitle() {
        TextView view = findViewById(R.id.title);
        return view != null ? view.getText().toString() : "";
    }

    /*
     全局监听网络变化
     */
    private void monitorNet(){
        mNetReceiver=new NetChangeReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetReceiver,intentFilter);
    }

    // Android 判断app是否在前台还是在后台运行，直接看代码，可直接使用。
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses != null) {
            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.processName.equals(context.getPackageName())) {
                /*
                BACKGROUND=400 EMPTY=500 FOREGROUND=100
				GONE=1000 PERCEPTIBLE=130 SERVICE=300 ISIBLE=200
				 */
                    Log.i(context.getPackageName(), "此appimportace ="
                            + appProcess.importance
                            + ",context.getClass().getName()="
                            + context.getClass().getName());
                    if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        Log.i(context.getPackageName(), "处于后台"
                                + appProcess.processName);
                        return true;
                    } else {
                        Log.i(context.getPackageName(), "处于前台"
                                + appProcess.processName);
                        return false;
                    }
                }
            }
        }

        return false;
    }

    /**
     * 返回
     */
    public void back(View view) {
        finish();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.translate_to_left, R.anim.translate_to_left_hide);//activity间跳转动画
    }

    public void startActivityByAlpha(Intent intent){
        super.startActivity(intent);
        overridePendingTransition(R.anim.alpha_enter_anim,R.anim.alpha_exit_anim);
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.translate_to_right, R.anim.translate_to_right_hide);//activity间跳转动画
    }



    private static class  ScreenBroadcastReceiver extends BroadcastReceiver{
        private OverallHandler handler;
        @Override
        public void onReceive(Context context, Intent intent) {
          String action= intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)){//开屏广播
                Log.i(TAG, "onReceive: 开屏....");

            }else if (Intent.ACTION_SCREEN_OFF.equals(action)|| //息屏或按home键
                    Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)){
                Log.i(TAG, "onReceive: 屏幕关闭或按Home键--"+action);
                handler=RyeCatcherApp.getInstance().getHandler();
                handler.sendEmptyMessageDelayed(OverallHandler.backgroundCode,200);
                handler.sendEmptyMessageDelayed(OverallHandler.exit,OverallHandler.howlongtofinish);
            }
        }
    }








    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Exit();
        }

        return super.onKeyDown(keyCode, event);
    }

    private static Boolean isExit = false;
    private ZTask timerTask;
    //双击两次退出应用
    Timer timer;
    private void Exit() {
        if (isExit == false) {
            timer = new Timer();
            timerTask= new ZTask(BaseActivity.this);
            timer.schedule(timerTask,2000);
        } else {
            finish();
            System.exit(0);
        }
    }

    /**
     * timer内存泄露
     */
    private static class ZTask extends TimerTask{
        private WeakReference<BaseActivity> weakReference;
        public ZTask(BaseActivity activity){
            weakReference=new WeakReference<>(activity);
        }
        @Override
        public void run() {
           weakReference.get().isExit=false;
        }
    }


/*************************************新增********************************************/
    /**
     * Loading Dialog
     * @param context
     */
    protected void showLoadingDlg(Context context){
        DialogUtil.createLoadingDialog(context);
    }

    protected  void cancelLoadingDlg(Context context){
        DialogUtil.closeDialog(context);
    }

    /**
     * 开始监听系统广播
      */
    public void startScreenBroadcastReceiver(){
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        //用户按键返回
        intentFilter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        //屏幕开时广播
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);
        this.registerReceiver(mScreenReceiver,intentFilter);
    }
    /**
     * 停止screen状态更新
     */
    public void stopScreenStateUpdate() {
        this.unregisterReceiver(mScreenReceiver);
    }

    /**
     * 判断是不是在主线程中
     * @return
     */
    public boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //就多一个参数this
        PermissionsUtil.INSTANCE.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //友盟Session统计
        MobclickAgent.onPause(this);
    }

    /**
     * 多国语言适配
     * @param newBase
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        Log.i(TAG,"attachBaseContext");
        super.attachBaseContext(LanguageUtil.Companion.attachBaseContext(
                newBase, SharedPreManager.getValue(LanguageConstants.VALUE)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNetReceiver);
        if (timer!=null){//防止内存泄露
            timerTask.cancel();
            timer.cancel();
        }

    }
}
