//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.rye.base.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Handler_ extends Handler {
    private static Handler_ mHandler;
    private static Handler mHandler2;
    private Map<String, Handler_.HandlerCallback> mCallbacks;
    private static List<UncaughtExceptionHandler> handlers = new ArrayList();
    private static final String CUSTOM_HANDLER_SPE_KEY = "CUSTOM_HANDLER_SPE_KEY";

    private Handler_() {
        super(Looper.getMainLooper());
    }

    private Handler_(Looper looper) {
        super(looper);
    }

    public static Handler_ getInstance() {
        if (mHandler == null) {
            mHandler = new Handler_();
        }

        return mHandler;
    }

    public static Handler getInstance(boolean isUI) {
        if (mHandler2 == null) {
            HandlerThread ht = new HandlerThread("non-ui thread");
            ht.start();
            mHandler2 = new Handler_(ht.getLooper());
        }

        return mHandler2;
    }

    public static void addUncaughtExceptionHandler(UncaughtExceptionHandler handler) {
        if (handler != null) {
            handlers.add(handler);
        }

    }

    public void onDestroy(String tag) {
        if (this.mCallbacks != null) {
            this.mCallbacks.remove(tag);
        }

    }

    public void release() {
        mHandler.removeCallbacksAndMessages((Object)null);
        if (this.mCallbacks != null) {
            this.mCallbacks.clear();
        }

        this.mCallbacks = null;
        mHandler = null;
    }

    public void setCallback(Handler_.HandlerCallback callback) {
        if (callback != null) {
            String callbackTag = callback.getTag();
            if (TextUtils.isEmpty(callbackTag)) {
                throw new NullPointerException("The tag of Callbakc is null.");
            } else {
                if (this.mCallbacks == null) {
                    this.mCallbacks = new HashMap();
                }

                if (this.mCallbacks.containsKey(callbackTag)) {
//                    if (Global.isDebug()) {
//                        Log.w(Handler_.class.getSimpleName(), callbackTag + " already exists!");
//                    }

                    this.mCallbacks.remove(callbackTag);
                }

                this.mCallbacks.put(callbackTag, callback);
            }
        }
    }

    public final void dispatchMessage(Message msg) {
        try {
            Runnable runnable = msg.getCallback();
            if (runnable != null) {
                long now = System.currentTimeMillis();
                long threadNow = SystemClock.currentThreadTimeMillis();
                runnable.run();
                long time = System.currentTimeMillis() - now;
                long threadEnd = SystemClock.currentThreadTimeMillis();
//                if (Global.isDebug() && time >= 10L) {
//                    Log.e(Handler_.class.getSimpleName(), "Warnning: Task[" + ClassUtil.getClassInfo(runnable.getClass()) + "] cost " + time + "ms(real time " + (threadEnd - threadNow) + "ms)");
//                }

                return;
            }

            Bundle data = msg.getData();
            if (data == null) {
                throw new Handler_.MessageTagIsNull("dispatchMessage error, send or post message, must be a parameter(tag)");
            }

            String msgTag = data.getString("CUSTOM_HANDLER_SPE_KEY");
            if (TextUtils.isEmpty(msgTag)) {
                throw new Handler_.MessageTagIsNull("dispatchMessage error, send or post message, must be a parameter(tag)");
            }

            if (this.mCallbacks == null || this.mCallbacks.isEmpty() || !this.mCallbacks.containsKey(msgTag)) {
                return;
            }

            Handler_.HandlerCallback callback = (Handler_.HandlerCallback)this.mCallbacks.get(msgTag);
            if (callback == null) {
                return;
            }

            callback.handleMessage(msg);
        } catch (Exception var12) {
            Exception e = var12;
            Iterator var3 = handlers.iterator();

            while(var3.hasNext()) {
                UncaughtExceptionHandler handler = (UncaughtExceptionHandler)var3.next();

                try {
                    handler.uncaughtException(Thread.currentThread(), e);
                } catch (Throwable var11) {
                }
            }

            if (e instanceof Handler_.MessageTagIsNull) {
//                if (!Global.isDebug()) {
//                    e.printStackTrace();
//                }
            } else {
//                if (Global.isDebug()) {
//                    throw new RuntimeException(e);
//                }

                e.printStackTrace();
            }
        }

    }

    public final boolean sendEmptyMessage(int what, String tag) {
        return this.sendEmptyMessageDelayed(what, 0L, tag);
    }

    public final boolean sendEmptyMessageAtTime(int what, long uptimeMillis, String tag) {
        Message msg = Message.obtain();
        msg.what = what;
        return this.sendMessageAtTime(msg, uptimeMillis, tag);
    }

    public final boolean sendEmptyMessageDelayed(int what, long delayMillis, String tag) {
        Message msg = Message.obtain();
        msg.what = what;
        return this.sendMessageDelayed(msg, delayMillis, tag);
    }

    public final boolean sendMessage(Message msg, String tag) {
        return this.sendMessageDelayed(msg, 0L, tag);
    }

    public final boolean sendMessageAtFrontOfQueue(Message msg, String tag) {
        this.setSpeBundleForMsg(msg, tag);
        return this.sendMessageAtFrontOfQueue(msg);
    }

    public boolean sendMessageAtTime(Message msg, long uptimeMillis, String tag) {
        this.setSpeBundleForMsg(msg, tag);
        return super.sendMessageAtTime(msg, uptimeMillis);
    }

    public final boolean sendMessageDelayed(Message msg, long delayMillis, String tag) {
        if (delayMillis < 0L) {
            delayMillis = 0L;
        }

        return this.sendMessageAtTime(msg, SystemClock.uptimeMillis() + delayMillis, tag);
    }

    private Bundle setSpeBundleForMsg(Message msg, String tag) {
        Bundle data = msg.getData();
        if (data == null) {
            data = new Bundle();
        }

        data.putString("CUSTOM_HANDLER_SPE_KEY", tag);
        msg.setData(data);
        return data;
    }

    public class MessageTagIsNull extends Exception {
        private static final long serialVersionUID = 6736648049601151640L;

        public MessageTagIsNull(String ex) {
            super(ex);
        }
    }

    public static class HandlerCallback {
        private String mTag;

        public HandlerCallback(String tag) {
            this.mTag = tag;
        }

        public String getTag() {
            return this.mTag;
        }

        public void handleMessage(Message msg) {
        }
    }
}
