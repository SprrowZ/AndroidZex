package com.rye.appupdater.updater;

import com.rye.appupdater.updater.net.INetManager;
import com.rye.appupdater.updater.net.OkhttpNetManager;

/**
 * Created By RyeCatcher
 * at 2019/9/2
 * ---------------------------------应用内部更新
 */
public class AppUpdater {

    public static final String CHECK_URL="http://59.110.162.30/app_updater_version.json";
    //网络请求，下载的能力
    //okhttp,volley,httpclient,httpurlconnection
    /**
     * 可替换INetManager的实现，volley，zhttp等等
     */
    private INetManager mNetManager=new OkhttpNetManager();

    /**
     * 设置INetManager的具体实现
     * @param manager
     */
    public  void setNetManager(INetManager manager){
        mNetManager=manager;
    }

    public static AppUpdater getInstance(){
        return Singleton.INSTANCE;
    }

    public INetManager getNetManager(){
        return mNetManager;
    }
    private static  class Singleton{
        private static final AppUpdater INSTANCE=new AppUpdater();
    }
}
