package com.example.myappsecond.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myappsecond.R;

/**
 * Created by ZZG on 2017/11/9.
 */

public class MeasureUtil {
    //**************************************获取屏幕长宽******************************
    public static int[] getScreenSize(Context context) {
        int[] a = new int[2];
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        a[0] = displayMetrics.widthPixels;
        a[1] = displayMetrics.heightPixels;
        return a;
    }
//*************************************获取屏幕高度*******************************
    public static int getScreenHeight(Context context) {
        int height;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        return height;
    }
//*************************************获取屏幕宽度********************************
    public static int getScreenWidth(Context context) {
        int width;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        return width;
    }

    public static int getScreenHeight(Activity activity) {
        int height;
        WindowManager manager = activity.getWindowManager();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        return height;
    }

    public static int getScreenWidth(Activity activity) {
        int width;
        WindowManager wm = activity.getWindowManager();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        return width;
    }
//******************************取得控件的长宽,在OnCreate中*****************************
    public static int[] getControlScale(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int[] measure = new int[2];
        measure[0] = view.getMeasuredHeight();
        measure[1] = view.getMeasuredWidth();
        return measure;
    }
//********************************设置的drawableLeft的大小*****************************
    public static void setLeftScale(Activity activity, View parent, TextView son, int ResId){
        Drawable drawable1 =activity.getResources().getDrawable(ResId);
        drawable1.setBounds(0, 0, (int) (MeasureUtil.getControlScale(parent)[0]*0.7),
                (int) (MeasureUtil.getControlScale(parent)[0]*0.7));//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        son.setCompoundDrawables(drawable1, null, null, null);//只放左边
    }
    public static void setLeftScale(Activity activity, View parent, EditText son, int ResId){
        Drawable drawable1 =activity.getResources().getDrawable(ResId);
        drawable1.setBounds(0, 0, (int) (MeasureUtil.getControlScale(parent)[0]*0.7),
                (int) (MeasureUtil.getControlScale(parent)[0]*0.7));//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        son.setCompoundDrawables(drawable1, null, null, null);//只放左边
    }
//**********************************设置view的宽高***************************************
    public static void setViewSize(View view,int height,int width){
         WindowManager.LayoutParams layoutParams= (WindowManager.LayoutParams) view.getLayoutParams();
         layoutParams.width= width;
         layoutParams.height= height;
         view.setLayoutParams(layoutParams);
    }






}
