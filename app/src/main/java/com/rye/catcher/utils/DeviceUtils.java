package com.rye.catcher.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.rye.catcher.RyeCatcherApp;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 *
 */
public class DeviceUtils {
    private static String tag = "DeviceUtils";
    private static Boolean isRoot = null;

    /**
     * @return 取得设备的UUID
     */
    public static String getUUID(Context context) {
        // ANDROID_ID是设备第一次启动时产生和存储的64bit的一个数，当设备被wipe后该数重置
        if (context == null) {
            context = RyeCatcherApp.getInstance().getBaseContext();
        }

        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getShortDeviceId() {
        String uuid = getUUID(null);
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
    private static DisplayMetrics getDisplayMetrics(Context context) {

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Service.WINDOW_SERVICE);
        if (manager != null) {
            manager.getDefaultDisplay().getMetrics(metrics);
        }
        return metrics;
    }

    /**
     * 获取手机像素密度（DPI）
     * @param context
     * @return
     */
    public static float getDpi(Context context) {
        DisplayMetrics metrics = getDisplayMetrics(context);
        return metrics.density * 160;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context Context
     * @return 屏幕宽度（px）
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
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
     * @param context Context
     * @return 屏幕高度（px）
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
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
     * @param context
     * @return dp
     */
    public static int getScreenSw(Context context) {

        int screenHeight = getScreenHeight(context);
        int screenWidth = getScreenWidth(context);
        int smallestWidthDP = screenHeight > screenWidth ? screenWidth : screenHeight;
        //跟getDpi里相较有点多余操作..
        int sw = (int) (smallestWidthDP * 160 / getDpi(context));
        return sw;
    }

    /**
     * 获取手机序列号，需要权限申请
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(
                Context.TELEPHONY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
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
     * @return
     */
   public static String getDeviceManufacturer(){
        return Build.MANUFACTURER;
   }

    /**
     * 手机品牌
     * @return
     */
   public static String getDeviceBrand(){
        return Build.BRAND;
   }

    /**
     * 手机型号
     * @return
     */
   public static String getDeviceModel(){
        return Build.MODEL;
   }

    /**
     * 手机主板名
     * @return
     */
   public static String getDeviceBoard(){
        return Build.BOARD;
   }

    /**
     * 手机设备名
     * @return
     */
   public static String getDeviceName2(){
        return Build.DEVICE;
    }

    /**
     * 手机ID
     * @return
     */
    public static String getDeviceID(){
        return Build.ID;
    }




}
