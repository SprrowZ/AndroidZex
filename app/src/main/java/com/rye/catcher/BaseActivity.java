package com.rye.catcher;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.rye.catcher.utils.DialogUtil;
import com.rye.catcher.utils.ExtraUtil.Constant;
import com.rye.catcher.utils.PermissionsUtil;
import com.rye.catcher.utils.StringUtils;
import com.rye.catcher.utils.ToastUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ZZG on 2017/10/23.
 */

public class BaseActivity extends AppCompatActivity {

    protected Map<BroadcastReceiver, Integer> receiverMap = new ConcurrentHashMap<>();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //沉浸式状态栏
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
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
    /**
     * 设置标题栏右侧文本
     */
    public void setBarRightText(String rightText, View.OnClickListener listener) {
        TextView view = (TextView) findViewById(R.id.right_text);
        ImageView img = (ImageView) findViewById(R.id.right_image);
        if (view != null) {
            view.setText(rightText);
            img.setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);

            if (listener != null) {
                view.setOnClickListener(listener);
            }
        }
    }

    public void hideRightDrawable() {
        TextView view = (TextView) findViewById(R.id.right_text);
        ImageView img = (ImageView) findViewById(R.id.right_image);
        view.setVisibility(View.GONE);
        img.setVisibility(View.GONE);
    }

    public void setBarRightText(int resId, View.OnClickListener listener) {
        TextView view = (TextView) findViewById(R.id.right_text);
        ImageView img = (ImageView) findViewById(R.id.right_image);
        if (view != null) {
            view.setText(resId);
            img.setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);

            if (listener != null) {
                view.setOnClickListener(listener);
            }
        }
    }


    /**
     * 设置标题栏右侧图片
     */
    public void setBarRightDrawable(Drawable drawable, View.OnClickListener listener) {
        ImageView view = (ImageView) findViewById(R.id.right_image);
        TextView text = (TextView) findViewById(R.id.right_text);
        if (view != null) {
            view.setImageDrawable(drawable);
            text.setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);

            if (listener != null) {
                view.setOnClickListener(listener);
            }
        }
    }

    public void setBarRightDrawable(int resId, View.OnClickListener listener) {
        ImageView view = (ImageView) findViewById(R.id.right_image);
        TextView text = (TextView) findViewById(R.id.right_text);
        if (view != null) {
            view.setImageResource(resId);
            text.setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);

            if (listener != null) {
                view.setOnClickListener(listener);
            }
        }
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

    /**
     * 增加长时间的提示消息
     *
     * @param msg 提示消息
     */
    public void showLongMsg(final String msg) {
        try {
            runOnUiThread(()->{
                    ToastUtils.longMsg(msg);
            });
        } catch (Exception ignored) {
        }
    }

    /**
     * 增加短时间的提示消息
     *
     * @param msg 提示消息
     */
    public void showShortMsg(final String msg) {
        try {
            runOnUiThread(()->{
                    ToastUtils.shortMsg(msg);
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

    /**
     * 记录所有活动的Activity
     */
    private static final List<BaseActivity> mActivities = new LinkedList<BaseActivity>();
    /**
     * 关闭所有Activity
     */
    public static void finishAll() {
        List<BaseActivity> copy;
        synchronized (mActivities) {
            copy = new ArrayList<BaseActivity>(mActivities);
        }
        for (BaseActivity activity : copy) {
            activity.finish();
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
        DialogUtil.closeLoadingDialog(context);
    }

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
    protected void onDestroy() {
        super.onDestroy();
        if (timer!=null){//防止内存泄露
            timerTask.cancel();
            timer.cancel();
        }
    }
}
