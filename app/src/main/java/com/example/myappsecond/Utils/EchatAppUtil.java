package com.example.myappsecond.Utils;

import android.content.Context;
import android.os.Handler;

/**
 * Created by ruaho on 2017/7/5.
 */

public class EchatAppUtil {
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

//    public static void gotoMain(final EMCallBack emCallBack, final boolean showMsg, String message, String deviceName){
//        Intent intent =  new Intent(logout);
//        intent.putExtra("showMsg",showMsg);
//        intent.putExtra("message",message);
//        intent.putExtra("deviceName",deviceName);
//        context.sendBroadcast(intent);
//    }

}
