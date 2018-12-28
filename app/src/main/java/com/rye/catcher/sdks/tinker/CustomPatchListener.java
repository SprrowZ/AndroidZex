package com.rye.catcher.sdks.tinker;

import android.content.Context;

import com.tencent.tinker.lib.listener.DefaultPatchListener;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created at 2018/12/27.
 * 1.校验patch文件是否合法 2.启动Service来去安装patch文件
 * @author Zzg
 */
public class CustomPatchListener extends DefaultPatchListener {
    private String currentMD5;

    public String getCurrentMD5(){
        return currentMD5;
    }
    public void setCurrentMD5(String md5Value){
        this.currentMD5=md5Value;
    }
    public CustomPatchListener(Context context) {
        super(context);
    }

    /**
     * md5校验
     * @param path
     * @param patchMd5
     * @return
     */
    @Override
    protected int patchCheck(String path, String patchMd5) {
        //patch文件md5校验
        if (!TinkerUtils.isFileMD5Matched(path,currentMD5)){
            return ShareConstants.ERROR_PATCH_DISABLE;
        }

        return super.patchCheck(path, patchMd5);
    }
}
