/*
 * Copyright (c) 2016. Bilibili Inc.
 */

package com.rye.base.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

import android.view.View;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;


/**
 * Helper class for {@link android.widget.Toast}
 *
 * @author yrom
 */
public class ToastHelper {
    private static final String TAG = "ToastHelper";

    private static WeakReference<Toast> sLastToast;
    private static Field sContext;

    /**
     * Show a toast with specified text and duration
     */
    public static void showToast(@Nullable Context context, @StringRes int textResId, int duration) {
        if (context != null)
            showToast(context, context.getString(textResId), duration);
    }

    public static void showToastSafely(@NonNull Toast toast) {
        // hook view's context
        hookContext(toast.getView());
        try {
            toast.show();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    private static void hookContext(View view) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
            final Context context = view.getContext();
            if (!(context instanceof SafeToastContext)) {
                try {
                    if (sContext == null) {
                        Field c = View.class.getDeclaredField("mContext");
                        c.setAccessible(true);
                        sContext = c;
                    }
                    sContext.set(view, new SafeToastContext(context));
                } catch (Exception ignored) {
                }
            }
        }
    }

    /**
     * Show a toast with specified text and duration
     */
    public static void showToast(@Nullable Context context, String text, int duration) {
        if (context == null) {
            return;
        }
        if (sLastToast != null) {
            Toast toast = sLastToast.get();
            if (toast != null) {
                toast.cancel();
                sLastToast.clear();
            }
        }
        // wrap context on API 25
        final Context c = Build.VERSION.SDK_INT == 25 ? new SafeToastContext(context) : context;
        @SuppressLint("ShowToast")
        Toast toast = Toast.makeText(c, text, duration);
        showToastSafely(toast);
        sLastToast = new WeakReference<>(toast);

    }

    /**
     * Try to cancel the last showing toast
     *
     * @see #showToast(Context, String, int)
     * @see #showToast(Context, int, int)
     */
    public static void cancel() {
        if (sLastToast != null) {
            Toast toast = sLastToast.get();
            if (toast != null)
                toast.cancel();
        }
    }

    /**
     * Show a toast with specified text and {@link Toast#LENGTH_LONG}
     */
    public static void showToastLong(@Nullable Context context, @StringRes int textResId) {
        showToast(context, textResId, Toast.LENGTH_LONG);
    }

    /**
     * Show a toast with specified text and {@link Toast#LENGTH_LONG}
     */
    public static void showToastLong(@Nullable Context context, String text) {
        showToast(context, text, Toast.LENGTH_LONG);
    }

    /**
     * Show a toast with specified text and {@link Toast#LENGTH_SHORT}
     */
    public static void showToastShort(@Nullable Context context, int textResId) {
        showToast(context, textResId, Toast.LENGTH_SHORT);
    }

    /**
     * Show a toast with specified text and {@link Toast#LENGTH_SHORT}
     */
    public static void showToastShort(@Nullable Context context, String text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }

}
