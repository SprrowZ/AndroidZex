package com.rye.catcher.base;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created at 2019/4/8.
 *
 * @author Zzg
 * @function: 全局Handler
 */
public class OverallHandler extends Handler {
    private static final String TAG="OverallHandler";

    public static final int backgroundCode = 1;
    public static final int connectionCode = 2;
    public static final int connectionLogin = 3;
    public static final int keepAlive = 4;
    public static final int exit = 79;
    public static final int updateOffTime = 210;

    public static final long howlongtofinish = 2 * 60 * 1000;
    //弱引用
    private WeakReference<Context> weakReference;

    public OverallHandler(Context context){
     weakReference=new WeakReference<>(context);
    }
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case backgroundCode:
            //退入到后台

                break;
        }
    }
}
