package com.rye.catcher;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.rye.catcher.base.OverallHandler;
import com.rye.catcher.utils.DialogUtil;
import com.rye.catcher.utils.ExtraUtil.Constant;
import com.rye.catcher.utils.PermissionsUtil;
import com.rye.catcher.utils.StringUtils;
import com.umeng.analytics.MobclickAgent;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ZZG on 2017/10/23.
 */

public class BaseActivity extends AppCompatActivity {
    private static final String TAG="BaseActivity";
    protected Map<BroadcastReceiver, Integer> receiverMap = new ConcurrentHashMap<>();
    //监听系统广播
    private ScreenBroadcastReceiver mScreenReceiver;

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
        TextView view = (TextView) findViewById(R.id.title);
        if (view != null) {
            view.setText(title);
        }
    }

    public void setBarTitle(int resId) {
        TextView view = (TextView) findViewById(R.id.title);
        if (view != null) {
            view.setText(resId);
        }
    }

    public String getBarTitle() {
        TextView view = (TextView) findViewById(R.id.title);
        return view != null ? view.getText().toString() : "";
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


    private ProgressDialog _loadingDlg = null;

    protected final Object lock = new Object();

    /**
     * 显示装载中对话框
     *
     * @param msg 提示消息内容
     */
    public void showLoadingDlg(final String msg) {
        runOnUiThread(()->{
                synchronized (lock) {
                    if (_loadingDlg == null) {
                        _loadingDlg = new ProgressDialog(BaseActivity.this);
                        _loadingDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        _loadingDlg.setIcon(R.drawable.ajax_loader);
                        _loadingDlg.setCancelable(true);
                    }

                    if (StringUtils.isNotEmpty(msg)) {
                        _loadingDlg.setMessage(msg);
                    } else {
                        _loadingDlg.setMessage(getResources().getString(R.string.please_wait));
                    }

                    if (!_loadingDlg.isShowing()) {
                        _loadingDlg.show();
                    }
                }
        });
    }

    /**
     * 显示装载中对话框
     *
     * @param msg           提示消息内容
     * @param setCancelable 是否能取消
     */
    public void showLoadingDlg(final String msg, final boolean setCancelable) {
        runOnUiThread(()->{
                synchronized (lock) {
                    if (_loadingDlg == null) {
                        _loadingDlg = new ProgressDialog(BaseActivity.this);
                        _loadingDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        _loadingDlg.setIcon(R.drawable.ajax_loader);
                        _loadingDlg.setCanceledOnTouchOutside(setCancelable);
                    }

                    if (StringUtils.isNotEmpty(msg)) {
                        _loadingDlg.setMessage(msg);
                    } else {
                        _loadingDlg.setMessage(getResources().getString(R.string.please_wait));
                    }

                    if (!_loadingDlg.isShowing()) {
                        _loadingDlg.show();
                    }
                }
        });
    }

    /**
     * 隐藏加载中对话框
     */
    public void cancelLoadingDlg() {
        try {
            runOnUiThread(()->{

                    if (_loadingDlg != null) {
                        _loadingDlg.cancel();
                    }
            });
        } catch (Exception ignored) {
        }
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

    /**
     * 注册广播监听类
     *
     * @param action   监听事件类型
     * @param receiver 监听类
     */
    protected void registerReceiver(String action, BroadcastReceiver receiver) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(action);
        super.registerReceiver(receiver, filter);
        receiverMap.put(receiver, Constant.YES_INT);
    }

    /**
     * 注册广播监听类
     *
     * @param action   监听事件类型g
     * @param receiver 监听类
     * @param priority 监听事件响应优先级
     */
    protected void registerReceiver(String action, BroadcastReceiver receiver, int priority) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(action);
        filter.setPriority(priority);
        super.registerReceiver(receiver, filter);
        receiverMap.put(receiver, Constant.YES_INT);
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


    /**
     * 将图片转换成Base64编码的字符串
     * @param path
     * @return base64编码的字符串
     */
    public static String imageToBase64(String path){
        if(StringUtils.isEmpty(path)){
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try{
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.DEFAULT);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(null !=is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
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
        DialogUtil.closeLoadingDialog(context);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer!=null){//防止内存泄露
            timerTask.cancel();
            timer.cancel();
        }

    }
}
