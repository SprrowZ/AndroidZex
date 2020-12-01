package com.rye.base.utils;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;

import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;


import com.rye.base.BaseApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 *
 */
public class DeviceUtils {
    private static String tag = "DeviceUtils";
    private static Boolean isRoot = null;
    private static Context mContext;

    //私有构造器
    private DeviceUtils() {
    }

    static {
        mContext = BaseApplication.getInstance();
    }

    /**
     * @return 取得设备的UUID
     */
    public static String getUUID() {
        // ANDROID_ID是设备第一次启动时产生和存储的64bit的一个数，当设备被wipe后该数重置
        return Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getShortDeviceId() {
        String uuid = getUUID();
        if (uuid == null) {
            uuid = "";
        }
        if (uuid.length() <= 8) {
            return uuid;
        } else {
            return uuid.substring(0, 8);
        }

    }

    /**
     * @return 厂商 + 产品名称，如：SONY L36H
     */
    public static String getDeviceName() {
        return Build.MANUFACTURER + " " + Build.MODEL;
    }

    /**
     * @return 取得安卓系统的版本号。例如：Android 4.1.2
     */
    public static String getReleaseVersion() {
        return "Android " + Build.VERSION.RELEASE;
    }

    /**
     * @return 当前操作系统是否被ROOT
     */
    public static boolean isRoot() {
        if (isRoot == null) {
            isRoot = isRootSystem();
        }

        return isRoot;
    }

    //    我们可以在环境变量$PATH所列出的所有目录中查找是否有su文件来判断一个手机是否Root。
//    当然即使有su文件，也并不能完全表示手机已经Root，但是实际使用中作为一个初略的判断已经很好了。
//    另外出于效率的考虑，我们可以在代码中直接把$PATH写死。


    private static boolean isRootSystem() {
        File f;
        final String kSuSearchPaths[] = {"/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/"};
        try {
            for (int i = 0; i < kSuSearchPaths.length; i++) {
                f = new File(kSuSearchPaths[i] + "su");
                if (f != null && f.exists()) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 获取总的内存数量
     */
    public static String getTotalMemory() {
        String str1 = "/proc/meminfo";
        FileReader fr = null;
        BufferedReader localBufferedReader = null;
        final String MEM_TOTAL = "MemTotal:";
        try {
            String str2;
            fr = new FileReader(str1);
            localBufferedReader = new BufferedReader(fr, 8192);
            while ((str2 = localBufferedReader.readLine()) != null) {
                if (str2.startsWith(MEM_TOTAL)) {
                    String result = str2.substring(MEM_TOTAL.length());
                    result = result.trim();
                    return result;
                }
            }
        } catch (IOException e) {
            Log.e(tag, e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(localBufferedReader);
            IOUtils.closeQuietly(fr);
        }

        return "N/A";
    }

    /***********************************新增*****************************************/
    private static DisplayMetrics getDisplayMetrics() {

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) mContext.getSystemService(Service.WINDOW_SERVICE);
        if (manager != null) {
            manager.getDefaultDisplay().getMetrics(metrics);
        }
        return metrics;
    }

    /**
     * 获取手机像素密度（DPI）
     *
     * @return
     */
    public static float getDpi() {
        DisplayMetrics metrics = getDisplayMetrics();
        return metrics.density * 160;
    }

    /**
     * 获取屏幕宽度
     *
     * @return 屏幕宽度（px）
     */
    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }

    /**
     * 获取屏幕高度
     *
     * @return 屏幕高度（px）
     */
    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    /**
     * 获取最小宽度sw（dp）
     *
     * @return dp
     */
    public static int getScreenSw() {
        int screenHeight = getScreenHeight();
        int screenWidth = getScreenWidth();
        Log.i("smallWidth", "getScreenSw: height->" + screenHeight + ",width->" + screenWidth);
        int smallestWidthDP = screenHeight > screenWidth ? screenWidth : screenHeight;
        //跟getDpi里相较有点多余操作..
        int sw = (int) (smallestWidthDP * 160 / getDpi());
        return sw;
    }

    /**
     * 获取手机序列号，需要权限申请
     *
     * @return
     */
    public static String getIMEI() {
        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(
                Context.TELEPHONY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                String deviceId = tm.getImei();
                return deviceId;
            }

        }
        return "";
    }

    /**
     * 手机厂商
     *
     * @return
     */
    public static String getDeviceManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 手机品牌
     *
     * @return
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 手机型号
     *
     * @return
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * 手机主板名
     *
     * @return
     */
    public static String getDeviceBoard() {
        return Build.BOARD;
    }

    /**
     * 手机设备名
     *
     * @return
     */
    public static String getDeviceName2() {
        return Build.DEVICE;
    }

    /**
     * 手机ID
     *
     * @return
     */
    public static String getDeviceID() {
        return Build.ID;
    }

    /**
     * 判断当前手机是ART虚拟机还是Dalvik虚拟机
     *
     * @return
     */
    public static String getVirtualMachine() {
        final String vmVersion = System.getProperty("java.vm.version");
        String result = "";
        if (vmVersion.startsWith("2")) {
            result = "ART虚拟机，版本号：" + vmVersion;
        } else {
            result = "Dalvik虚拟机，版本号：" + vmVersion;
        }
        return result;

    }

    /**
     * 获取一个设备 单个应用可用的最大内存
     *
     * @return
     */
    public static String getMaxMemory(WeakReference<Context> wrf) {
        Context context = wrf.get();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        int maxMemory = activityManager.getMemoryClass();
        return maxMemory + "m";
    }

    /**
     * 获取一个设备 剩余可用内存
     *
     * @return
     */
    public static String getDeviceActiveMemory() {
        long maxMemory = Runtime.getRuntime().freeMemory();
        return maxMemory / (8 * 1024 * 1024) + "MB";
    }

    /**
     * 获取一个设备 最大内存
     *
     * @return
     */
    public static String getDeviceMaxMemory() {
        long maxMemory = Runtime.getRuntime().maxMemory();
        return maxMemory / (8 * 1024 * 1024) + "MB";
    }

    /**
     * 获取一个设备已经使用的内存
     *
     * @return
     */
    public static String getDeviceTotalMemory() {
        long maxMemory = Runtime.getRuntime().totalMemory();
        return maxMemory / (8 * 1024 * 1024) + "MB";
    }
}
