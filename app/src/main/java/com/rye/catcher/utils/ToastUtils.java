package com.rye.catcher.utils;


import com.rye.catcher.zApplication;

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
        Toasty.custom(zApplication.getContext(), msg,
                null, 0, LENGTH_SHORT, false, false).show();
    }

    public static void longMsg(String msg) {
        if (StringUtils.isEmpty(msg)) {
            return;
        }
        Toasty.custom(zApplication.getContext(), msg,
                null, 0, LENGTH_LONG, false, false).show();
    }

    public static void shortMsg(int resId) {
        Toasty.custom(zApplication.getContext(),
                zApplication.getContext().getString(resId),
                null, 0, LENGTH_SHORT, false, false).show();
    }

    public static void longMsg(int resId) {
        Toasty.custom(zApplication.getContext(),
                zApplication.getContext().getString(resId),
                null, 0, LENGTH_LONG, false, false).show();
    }

}
