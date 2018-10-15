package com.rye.catcher.utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.rye.catcher.zApplication;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jinyunyang on 15/3/6.
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
            context = zApplication.getInstance().getBaseContext();
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
                if(str2.startsWith(MEM_TOTAL)) {
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

    /**
     *
     * @return 获取最大CPU频率
     */
    public static String getMaxCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        InputStream in = null;
        try {
            String[] args = {"/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"};

            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }

            in.close();

        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        } finally {
            IOUtils.closeQuietly(in);
        }
        return result.trim();
    }


    /**
     * The default return value of any method in this class when an
     * error occurs or when processing fails (Currently set to -1). Use this to check if
     * the information about the device in question was successfully obtained.
     */
    public static final int DEVICEINFO_UNKNOWN = 0;

    /**
     * Reads the number of CPU cores from {@code /sys/devices/system/cpu/}.
     *
     * @return Number of CPU cores in the phone, or DEVICEINFO_UKNOWN = -1 in the event of an error.
     */
    public static int getNumberOfCPUCores() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
            // Gingerbread doesn't support giving a single application access to both cores, but a
            // handful of devices (Atrix 4G and Droid X2 for example) were released with a dual-core
            // chipset and Gingerbread; that can let an app in the background run without impacting
            // the foreground application. But for our purposes, it makes them single core.
            return 1;
        }
        int cores;
        try {
            cores = new File("/sys/devices/system/cpu/").listFiles(CPU_FILTER).length;
        } catch (SecurityException e) {
            cores = DEVICEINFO_UNKNOWN;
        } catch (NullPointerException e) {
            cores = DEVICEINFO_UNKNOWN;
        }
        return cores;
    }

    private static final FileFilter CPU_FILTER = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            String path = pathname.getName();
            //regex is slow, so checking char by char.
            if (path.startsWith("cpu")) {
                for (int i = 3; i < path.length(); i++) {
                    if (path.charAt(i) < '0' || path.charAt(i) > '9') {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
    };

}
