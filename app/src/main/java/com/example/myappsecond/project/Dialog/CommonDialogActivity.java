package com.example.myappsecond.project.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.myappsecond.R;
import com.example.myappsecond.BaseActivity;

import com.example.myappsecond.utils.MeasureUtil;

/**
 * Created by ZZG on 2017/11/14.
 */

public class CommonDialogActivity {
    //这个是Top的Dialog
    private AlertDialog dialog;
    private View contentView;

    /**
     * 构造方法
     * @param activity Activity对象
     */
    public CommonDialogActivity(BaseActivity activity, int layoutResId) {
        //1.创建AlertDialog
        dialog = new AlertDialog.Builder(activity).create();
        dialog.show();

        //2.设置宽度
        Window window = getWindow();
        window.setGravity(Gravity.TOP);
        window.clearFlags(
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        WindowManager.LayoutParams params = window.getAttributes();
        params.width = getMetrics().widthPixels;
        window.setAttributes(params);

        //3.设置布局
        contentView = View.inflate(activity, layoutResId, null);
        dialog.setContentView(contentView);
    }
public void setLeftScale(Activity activity,LinearLayout parent,EditText son){
    MeasureUtil.setLeftScale(activity,parent,son, R.mipmap.icon_title_bar_edit_search);
    Toast.makeText(activity,"dddddd",Toast.LENGTH_LONG).show();
}
//    public CommonDialogActivity(BaseActivity activity, int Res,EditText son,LinearLayout parent) {
//        //1.创建AlertDialog
//        dialog = new AlertDialog.Builder(activity).create();
//        contentView = View.inflate(activity, Res, null);
//        MeasureUtil.setLeftScale(activity,parent,son,R.drawable.icon_title_bar_edit_search);
//        dialog.setView(contentView);
//        dialog.show();
//
//        //2.设置宽度
//        Window window = getWindow();
//        window.setGravity(Gravity.TOP);
//        WindowManager.LayoutParams params = window.getAttributes();
//        params.width = getMetrics().widthPixels;
//        window.setAttributes(params);
//        //3.设置布局
//
////        EditText son=contentView.findViewById(R.id.search_text);
////        LinearLayout parent=contentView.findViewById(R.id.search);
//
//       // Toast.makeText(activity,"dddddd",Toast.LENGTH_LONG).show();
//        //dialog.setContentView(contentView);
//    }
    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }



    public void setHeight(int height) {
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.height = height;
        window.setAttributes(params);
    }

    private DisplayMetrics getMetrics() {
        DisplayMetrics metrics = new DisplayMetrics();
        dialog.getWindow().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        return metrics;
    }

    public Window getWindow() {
        return dialog.getWindow();
    }

    public View getContentView() {
        return contentView;
    }

    public View findViewById(int id) {
        return dialog.findViewById(id);
    }


    public AlertDialog getAlertDialog(){
        return dialog;
    }
}
