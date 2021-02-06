package com.rye.router_api.api;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dalvik.system.DexFile;

/**
 * Create by rye
 * at 2021/1/23
 *
 * @description:
 */
class ClassUtils {

    public static Set<String> getFileNameByPackageName(Application context, final String packageName)
            throws PackageManager.NameNotFoundException {
        //拿到apk中的dex文件路径
        final Set<String> classNames = new HashSet<>();
        List<String> paths = getSourcePath(context);

        for (final String path : paths) {
            DexFile dexFile = null;
            try {
                //加载apk中的dex并遍历获取所有packageName的类
                dexFile = new DexFile(path);
                Enumeration<String> dexEntries = dexFile.entries();
                while (dexEntries.hasMoreElements()) {
                    //整个dex中所有的类
                    String className = dexEntries.nextElement();
                    if (className.startsWith(packageName)) {
                        classNames.add(className);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != dexFile) {
                    try {
                        dexFile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return classNames;
    }

    /**
     * 获取程序中所有的apk(instant run会产生很多的split apk)
     *
     * @param context
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    private static List<String> getSourcePath(Context context) throws PackageManager.NameNotFoundException {
        ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
        List<String> sourcePaths = new ArrayList<>();
        //当前应用的apk文件
        sourcePaths.add(applicationInfo.sourceDir);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (null != applicationInfo.splitSourceDirs) {
                sourcePaths.addAll(Arrays.asList(applicationInfo.splitSourceDirs));
            }
        }
        return sourcePaths;
    }
}
