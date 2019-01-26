package com.rye.catcher.project.catcher;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dzw
 * on 2018\5\31 0031.
 */
public class DelayHandleUtil {

    //延时action
    public static String DELAY_ACTION_UPDATE_MSG_STATUS = "DELAY_ACTION_UPDATE_MSG_STATUS";//更新消息已读未读状态

    public static int msg_what = 1;
    private static Map<String,Handler> handlerContainer = new HashMap();

    public static synchronized void setDelay(String action, long flag, int time, final HandleListener listener){

        Handler mHandler = handlerContainer.get(action);
        if(mHandler == null){
            HandlerThread handlerThread = new HandlerThread(action);
            handlerThread.start();
            mHandler = new Handler(handlerThread.getLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if(listener!=null) listener.ReachTheTime();
                }
            };
            handlerContainer.put(action,mHandler);
        }
        if (mHandler.hasMessages(msg_what)) {
            //如果队列中的消息时间戳小于本次时间戳在time时间内,跳过,不处理本次;
            if (System.currentTimeMillis() - flag < time) {
                return;
            }
        }
        mHandler.removeMessages(msg_what);//保险;
        mHandler.sendEmptyMessageDelayed(msg_what, time);
        flag = System.currentTimeMillis();
    }

    public static void removeHandler(String action){
        Handler mHandler = handlerContainer.get(action);
        if(mHandler!=null){
            mHandler.removeMessages(msg_what);
            handlerContainer.remove(mHandler);
            mHandler = null;
        }
    }

    public interface HandleListener{
        void ReachTheTime();
    }
}
