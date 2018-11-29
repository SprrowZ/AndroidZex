package com.rye.catcher.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.rye.catcher.BuildConfig;

/**
 * Created at 2018/11/29.
 *
 * @author Zzg
 */
public class PackageUtils {


    /**
     * 静态内部类单例
     */
    private static class PackageHole{
        private static PackageUtils instance=new PackageUtils();
    }

    public static  PackageUtils instance(){
        return PackageHole.instance;
    }

   public PackageInfo getPackageInfo(Context context){
        PackageManager manager=context.getPackageManager();
       try {
           PackageInfo packageInfo=manager.getPackageInfo(BuildConfig.APPLICATION_ID,PackageManager.GET_ACTIVITIES);
           return packageInfo;
       } catch (PackageManager.NameNotFoundException e) {
           e.printStackTrace();
       }
       return null;
   }
}
