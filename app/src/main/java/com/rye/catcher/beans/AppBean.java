package com.rye.catcher.beans;

import android.graphics.drawable.Drawable;

/**
 * Created at 2018/11/30.
 *
 * @author Zzg
 */
public class AppBean {
   private  String packageName;
   private  String appName;
   private  Drawable appIcon;
   private  String versionName;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
}
