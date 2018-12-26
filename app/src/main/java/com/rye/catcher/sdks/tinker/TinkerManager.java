package com.rye.catcher.sdks.tinker;

import android.content.Context;

import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.ApplicationLike;

/**
 * Created at 2018/12/24.
 *
 * @author Zzg
 */
public class TinkerManager {
    private static boolean isInstalled=false;
    private static ApplicationLike mAppLike;
    public static void installTinker(ApplicationLike applicationLike){
        mAppLike=applicationLike;
        if (isInstalled){
            return;
        }
        TinkerInstaller.install(mAppLike);//完成tinker初始化
    }

    public static  void loadPatch(String path){
        if (Tinker.isTinkerInstalled()){
            TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(),path);
        }
    }
    private static Context getApplicationContext(){
        if (mAppLike!=null){
            return mAppLike.getApplication().getApplicationContext();
        }
        return  null;
    }
}
