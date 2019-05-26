package com.rye.catcher.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by ZZG on 2018/7/29.
 */
public class TypeFaceUtil {
    //私有构造器
    private TypeFaceUtil(){}

    public  static  TypeFaceUtil getInstance() {
        return Singleton.INSTANCE;
    }

    private static class Singleton{
        private static final TypeFaceUtil INSTANCE=new TypeFaceUtil();
    }

    public Typeface tf(Activity activity) {
        Typeface tfs = Typeface.createFromAsset(activity.getAssets() , "fonts/fontzipMinEx.ttf");
        return tfs;
    }
    public Typeface tf(Context context) {
        Typeface tfs = Typeface.createFromAsset(context.getAssets() , "fonts/fontzipMinEx.ttf");
        return tfs;
    }
}
