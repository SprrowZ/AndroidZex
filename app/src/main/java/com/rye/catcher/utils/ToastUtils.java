package com.rye.catcher.utils;


import com.rye.catcher.RyeCatcherApp;

import es.dmoral.toasty.Toasty;

/**
 * 提示消息
 * Created by jinyunyang on 15/3/31.
 */
public class ToastUtils {
    public static final int LENGTH_SHORT = 2;
    public static final int LENGTH_LONG = 4;

    public static void shortMsg(String msg) {
        if (StringUtils.isEmpty(msg)) {
            return;
        }

        Toasty.custom(RyeCatcherApp.getContext(),msg,
                0, 0, LENGTH_SHORT, false, false).show();
    }

    public static void longMsg(String msg) {
        if (StringUtils.isEmpty(msg)) {
            return;
        }
        Toasty.custom(RyeCatcherApp.getContext(), msg,
               0, 0, LENGTH_LONG, false, false).show();
    }

    public static void shortMsg(int resId) {
        Toasty.custom(RyeCatcherApp.getContext(),
                RyeCatcherApp.getContext().getString(resId),
                0, 0, LENGTH_SHORT, false, false).show();
    }

    public static void longMsg(int resId) {
        Toasty.custom(RyeCatcherApp.getContext(),
                RyeCatcherApp.getContext().getString(resId),
               0, 0, LENGTH_LONG, false, false).show();
    }

}
