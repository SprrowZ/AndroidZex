package com.rye.catcher.utils;

import android.content.Context;
import android.os.Handler;

/**
 *全局Context
 */

public class EchatAppUtil {
    private EchatAppUtil(){}
    public static String logout = "logoutsdk";
    private static Context context = null;

    public static void setContext(Context c){
        context = c;
    }

    public static Context getAppContext(){
        return context;
    }

    private static Handler handler = null;

    public static void setHandler(Handler mhandler){
        handler = mhandler;
    }

    public static Handler getHandler(){
        return handler;
    }

}
