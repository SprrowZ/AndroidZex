package com.rye.catcher.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.rye.catcher.BuildConfig;
import com.rye.catcher.R;
import com.rye.catcher.agocode.beans.AppBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created at 2018/11/29.
 *获取手机上包相关内容
 * @author Zzg
 */
public class PackageUtils {
    //私有构造器
    private PackageUtils(){}
    /**
     * 静态内部类单例
     */
    private static class PackageHole{
        private static final PackageUtils instance=new PackageUtils();
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
   public static List<AppBean> getThirdApplications(Context context){
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

    /**
     * 获取系统应用
     * @param context
     * @return
     */
   public static List<AppBean> getSystemApplications(Context context){
       List<PackageInfo> packageInfos=getAllPackageInfo(context);
       List<AppBean> beans=new ArrayList<>();

       for (PackageInfo packageInfo:packageInfos){
           if ((packageInfo.applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM)<=0){
               //第三方应用

           }else{
               //系统应用
               AppBean bean=new AppBean();
               bean.setPackageName(packageInfo.packageName);
               bean.setAppName(String.valueOf(packageInfo.applicationInfo.loadLabel(context.getPackageManager())));
               bean.setVersionName(packageInfo.versionName);
               bean.setAppIcon(packageInfo.applicationInfo.loadIcon(context.getPackageManager()));
               beans.add(bean);
           }
       }
       return beans;
   }

    /**
     * 检查app是否已经在手机上下载！
     * @param packName
     * @param context
     * @return
     */
   public static boolean isAppInstalled(String packName,Context context){
       PackageInfo info=null;
       try {
           info=context.getPackageManager().getPackageInfo(packName,0);
       } catch (PackageManager.NameNotFoundException e) {
           e.printStackTrace();
       }
       return info!=null;
   }

    /**
     * 通过包名打开外部APP
     * @param packageName
     * @param context
     */
   public static void openOtherApp(String packageName,Context context){
       if (isAppInstalled(packageName,context)){
           Intent intent=context.getPackageManager().getLaunchIntentForPackage(packageName);
           intent.addCategory(Intent.CATEGORY_LAUNCHER);
           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                   | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
           context.startActivity(intent);
       }else{
           ToastUtils.shortMsg(context.getString(R.string.app_not_installed));
       }

   }

    /**
     * 通过指定的ActivityName打开第三方App
     * @param packageName
     * @param activityName
     * @param context
     */
    public static void openOtherAppByMain(String packageName,String activityName,Context context){
        if (isAppInstalled(packageName,context)){
            Intent intent=new Intent();
            ComponentName componentName=new ComponentName(packageName,activityName);
            intent.setComponent(componentName);
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }else {
            ToastUtils.shortMsg(context.getString(R.string.app_not_installed));
        }
    }


}
