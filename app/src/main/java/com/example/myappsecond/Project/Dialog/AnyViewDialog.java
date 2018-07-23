package com.example.myappsecond.Project.Dialog;

import android.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.myappsecond.BaseActivity;

/**
 * Created by ZZG on 2017/11/14.
 */

public class AnyViewDialog {
    private AlertDialog dialog;

    /**
     * 构造方法
     *
     * @param activity Activity对象
     */
    public AnyViewDialog(BaseActivity activity, int layoutId) {
        //1.创建AlertDialog
        dialog = new AlertDialog.Builder(activity).create();//
        dialog.setCanceledOnTouchOutside(true);// 设置点击屏幕Dialog消失

        if (activity.isFinishing()){
            return;
        }
        dialog.show();

        //2.设置宽度
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (int) (metrics.widthPixels);
        window.setAttributes(params);

        //3.设置布局
        layout = View.inflate(activity,layoutId, null);
        dialog.setContentView(layout);



    }
    View layout;
    public View getview(){
        return layout;
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * 是否点击空白处关闭Dialog
     *
     * @param cancel 布尔值
     */
    public void setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
    }
}
