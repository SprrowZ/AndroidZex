package com.rye.catcher.utils;


import com.rye.base.utils.StringUtils;
import com.rye.catcher.RyeCatcherApp;

import es.dmoral.toasty.Toasty;

/**
 * 提示消息
 *
 */
public class ToastUtils {
    public static final int LENGTH_SHORT = 2;
    public static final int LENGTH_LONG = 4;
    //私有构造器
    private ToastUtils(){}
    public static void shortMsg(String msg) {
        if (StringUtils.isEmpty(msg)) {
            return;
        }

        Toasty.normal(RyeCatcherApp.getInstance(),msg).show();
    }

    public static void longMsg(String msg) {
        if (StringUtils.isEmpty(msg)) {
            return;
        }
        Toasty.info(RyeCatcherApp.getInstance(), msg,
                LENGTH_LONG, true).show();
    }

    public static void shortMsg(int resId) {
        Toasty.normal(RyeCatcherApp.getInstance(),RyeCatcherApp.getInstance().getString(resId)).show();
    }

    public static void longMsg(int resId) {
        Toasty.info(RyeCatcherApp.getInstance(), RyeCatcherApp.getInstance().getString(resId),
                LENGTH_LONG, true).show();
    }

}
