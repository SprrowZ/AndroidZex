package com.rye.catcher.sdks.tinker;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.rye.catcher.sdks.tinker.module.BasePatch;
import com.rye.catcher.sdks.tinker.network.RequestCenter;
import com.rye.catcher.sdks.tinker.network.listener.DisposeDataHandle;
import com.rye.catcher.sdks.tinker.network.listener.DisposeDataListener;
import com.rye.catcher.sdks.tinker.network.listener.DisposeDownloadListener;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Created at 2018/12/25.
 * 应用程序Tinker更新服务
 * 1.从服务器下载patch文件
 * 2.使用TinkerManager完成patch文件加载
 * 3.patch文件会在下次进程启动时生效
 * @author Zzg
 */
public class TinkerService extends Service {
    private static  final  String FILE_END=".apk";//文件后缀名
    private static  final  int  DOWNLOAD_PATCH=0x01;//下载patch文件信息
    private static final   int  UPDATE_PATCH=0x02;//检查是否有patch更新

    private    String mPatchFileDir;//patch要保存的文件夹
    private    String mFilePatch;//patch文件保存路径
    private    BasePatch mBasePatchInfo;//服务器patch信息

    private DealHandler dealHandler=new DealHandler(this);
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //检查是否有patch更新
        dealHandler.sendEmptyMessage(UPDATE_PATCH);
        return START_NOT_STICKY;//被系统回收不再重启
    }
    //初始化变量
    private void init(){
        mPatchFileDir=getExternalCacheDir().getAbsolutePath()+"/tpatch/";
        File patchFileDir=new File(mPatchFileDir);
        try {
            if (patchFileDir==null || !patchFileDir.exists()){
                patchFileDir.mkdir();
            }
        }catch (Exception e){
            e.printStackTrace();
            stopSelf();//无法正常创建文件，则终止服务
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 防止handler内存泄露,静态内部类
     */
    private static class DealHandler extends Handler {
        WeakReference<TinkerService> mService;
        public DealHandler(TinkerService service){
            mService=new WeakReference<>(service);
        }
        @Override
        public void handleMessage(Message msg) {
            TinkerService zService=mService.get();//获取不到，就操作不了
            switch (msg.what){
                case UPDATE_PATCH:
                    zService.checkPatchInfo();
                    break;
                case DOWNLOAD_PATCH:
                    zService.downloadPatch();
                    break;
            }
        }

    }
    //检查是否有patch包需要更新
    private void checkPatchInfo(){

        RequestCenter.requestPatchUpdateInfo(new DisposeDataListener(){
            @Override
            public void onSuccess(Object responseObj) {
                 mBasePatchInfo=(BasePatch)responseObj;
                 dealHandler.sendEmptyMessage(DOWNLOAD_PATCH);
            }

            @Override
            public void onFailure(Object reasonObj) {
               stopSelf();
            }
        });
    }


    private void downloadPatch(){
        mFilePatch=mPatchFileDir.concat(String.valueOf(System.currentTimeMillis()))
                .concat(FILE_END);
      RequestCenter.downloadFile(mBasePatchInfo.data.downloadUrl, mFilePatch, new DisposeDownloadListener() {
          @Override
          public void onProgress(int progrss) {
               //可以打印进度
          }

          @Override
          public void onSuccess(Object responseObj) {

                TinkerManager.loadPatch(mFilePatch,mBasePatchInfo.data.md5);
          }

          @Override
          public void onFailure(Object reasonObj) {
               stopSelf();
          }
      });

    }
}
