package com.rye.catcher.sdks.tinker;

import android.content.Context;

import com.tencent.tinker.lib.patch.AbstractPatch;
import com.tencent.tinker.lib.patch.UpgradePatch;
import com.tencent.tinker.lib.reporter.DefaultLoadReporter;
import com.tencent.tinker.lib.reporter.DefaultPatchReporter;
import com.tencent.tinker.lib.reporter.LoadReporter;
import com.tencent.tinker.lib.reporter.PatchReporter;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.ApplicationLike;

/**
 * Created at 2018/12/24.
 *对Tinker的所有api左一层封装
 * @author Zzg
 */
public class TinkerManager {
    private static boolean isInstalled=false;
    private static ApplicationLike mAppLike;

    //
    private static  CustomPatchListener mPatchListener;
    /**
     * 完成tinker的初始化
     * @param applicationLike
     */
    public static void installTinker(ApplicationLike applicationLike){
        mAppLike=applicationLike;
        if (isInstalled){
            return;
        }
        mPatchListener=new CustomPatchListener(getApplicationContext());

        //patch加载阶段异常监听，出问题的日志
        LoadReporter loadReporter=new DefaultLoadReporter(getApplicationContext());
        //patch合成阶段异常监听，出问题的日志
        PatchReporter patchReporter =new DefaultPatchReporter(getApplicationContext());
        AbstractPatch upgradePatchProcessor = new UpgradePatch();

        TinkerInstaller.install(mAppLike,
                loadReporter,
                patchReporter,
                mPatchListener,
                CustomResultService.class,
                upgradePatchProcessor);//完成tinker初始化
        isInstalled=true;
    }

    /**
     * 完成Patch文件的加载
     * @param path
     */
    public static  void loadPatch(String path,String md5Value){
        if (Tinker.isTinkerInstalled()){
            mPatchListener.setCurrentMD5(md5Value);
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
