package com.rye.catcher.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.rye.catcher.BuildConfig;
import com.rye.catcher.beans.AppBean;

import java.util.ArrayList;
import java.util.List;

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


    /**
     * 得到当前包相关信息
     * @param context
     * @return
     */
   public static PackageInfo getPackageInfo(Context context){
        PackageManager manager=context.getPackageManager();
       try {
           PackageInfo packageInfo=manager.getPackageInfo(BuildConfig.APPLICATION_ID,PackageManager.GET_ACTIVITIES);
           return packageInfo;
       } catch (PackageManager.NameNotFoundException e) {
           e.printStackTrace();
       }
       return null;
   }

    /**
     * 得到手机上所有已安装应用的包信息
     * @param context
     * @return
     */
   public static List<PackageInfo> getAllPackageInfo(Context context){
       PackageManager manager=context.getPackageManager();
       List<PackageInfo>  packageInfos=manager.getInstalledPackages(0);
       return packageInfos;
   }

    /**
     * 得到手机上所有已安装应用的包名
     * @param context
     * @return
     */
   public static List<String> getAllPackageNames(Context context){
       List<String> packageNames=new ArrayList<>();
       List<PackageInfo> packageInfos=getAllPackageInfo(context);
       for (PackageInfo packageInfo:packageInfos
            ) {
          // packageInfo.applicationInfo.loadIcon(context.getPackageManager());//appIcon
         //  String.valueOf(packageInfo.applicationInfo.loadLabel(context.getPackageManager())) ;//appTrueName
         //   packageNames.add(packageInfo.packageName);
           packageNames.add(String.valueOf(packageInfo.applicationInfo.loadLabel(context.getPackageManager())));
       }
       return packageNames;
   }

    /**
     * 拿到所有第三方应用程序的四点信息
     * @param context
     * @return
     */
   public static List<AppBean> getPackagesInfo(Context context){
       List<PackageInfo> packageInfos=getAllPackageInfo(context);
       List<AppBean> beans=new ArrayList<>();

       for (PackageInfo packageInfo:packageInfos){
           if ((packageInfo.applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM)<=0){
               //第三方应用
               AppBean bean=new AppBean();
               bean.setPackageName(packageInfo.packageName);
               bean.setAppName(String.valueOf(packageInfo.applicationInfo.loadLabel(context.getPackageManager())));
               bean.setVersionName(packageInfo.versionName);
               bean.setAppIcon(packageInfo.applicationInfo.loadIcon(context.getPackageManager()));
               beans.add(bean);
           }else{
               //系统应用
           }
       }
       return beans;
   }
}
