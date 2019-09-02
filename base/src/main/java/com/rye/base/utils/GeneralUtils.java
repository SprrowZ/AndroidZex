package com.rye.base.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by SiKang on 2018/9/17.
 * 常用工具
 */
public class GeneralUtils {

    /**
     * List是否无内容
     */
    public static boolean isEmpty(List list) {
        if (list == null || list.size() == 0)
            return true;
        return false;
    }

    /**
     * 是否有空值
     */
    public static boolean hasEmpty(String value, String... values) {
        if (TextUtils.isEmpty(value)) {
            return true;
        } else {
            for (String str : values) {
                if (TextUtils.isEmpty(str)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 获取应用程序名称
     */
    public static synchronized String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取AndroidID
     */
    @SuppressLint("HardwareIds")
    public static String getAndroidID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }


    public static void findAndModifyOpInBackStackRecord(FragmentManager fragmentManager, int backStackIndex, OPHandler handler) {
        if (fragmentManager == null || handler == null) {
            return;
        }
        int backStackCount = fragmentManager.getBackStackEntryCount();
        if (backStackCount > 0) {
            if (backStackIndex >= backStackCount || backStackIndex < -backStackCount) {

                return;
            }
            if (backStackIndex < 0) {
                backStackIndex = backStackCount + backStackIndex;
            }
            try {
                FragmentManager.BackStackEntry backStackEntry = fragmentManager.getBackStackEntryAt(backStackIndex);

                Field opsField = backStackEntry.getClass().getDeclaredField("mOps");
                opsField.setAccessible(true);
                Object opsObj = opsField.get(backStackEntry);
                if (opsObj instanceof List<?>) {
                    List<?> ops = (List<?>) opsObj;
                    for (Object op : ops) {
                        if (handler.handle(op)) {
                            return;
                        }
                    }
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public interface OPHandler {
        boolean handle(Object op);
    }
}
