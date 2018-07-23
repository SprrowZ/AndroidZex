package com.example.myappsecond.Fragments;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.List;

/**
 * @author Created by xinzai on 2015/5/14.
 */
public class UIHelper {

    /**
     * dp转px
     */
    public static int dip2px(float dpValue) {
        float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }




    /**
     * 资源ID获取String
     */
//    public static String getString(int stringId) {
//        Context context = App.getContext();
//        if (context != null) {
//            return context.getString(stringId);
//        }
//        return " ";
//    }
//
//    public static String getString(int stringId, Object... formatArgs) {
//        Context context = App.getContext();
//        if (context != null) {
//            return context.getString(stringId, formatArgs);
//        }
//        return "";
//    }



    /**
     * 获取颜色
     */
//    public static int getColor(@ColorRes int color) {
//        return ContextCompat.getColor(App.getContext(), color);
//    }

    /**
     * 字符串转16进制整数
     */
//    public static int getColor(String color) {
//        if (TextUtils.isEmpty(color) || !Validator.checkColor(color)) {
//            return 0;
//        }
//        return Color.parseColor(color);
//    }

    /**
     * 字符串转16进制整数,带默认值
     */
//    public static int getColor(String color, String defaultColor) {
//        if (TextUtils.isEmpty(color) || !Validator.checkColor(color)) {
//            return getColor(defaultColor);
//        }
//        return Color.parseColor(color);
//    }
//
//    public static int getColor(String color, @ColorRes int defaultColor) {
//        if (TextUtils.isEmpty(color) || !Validator.checkColor(color)) {
//            return getColor(defaultColor);
//        }
//        return Color.parseColor(color);
//    }


    /**
     * 获取Drawable
     */
//    public static Drawable getDrawable(int drawable) {
//        return ContextCompat.getDrawable(App.getContext(), drawable);
//    }
//
//    public static View inflaterLayout(Context context, @LayoutRes int layoutRes) {
//        LayoutInflater inflater = LayoutInflater.from(context);
//        return inflater.inflate(layoutRes, null);
//    }





//    public static void clipContent(String content) {
//        ClipboardManager cm = (ClipboardManager) ContextHelper.getAppContext().getSystemService(Context.CLIPBOARD_SERVICE);
//        ClipData mClipData = ClipData.newPlainText("Label", content);
//        if (cm != null) {
//            cm.setPrimaryClip(mClipData);
//        }
//    }

}
