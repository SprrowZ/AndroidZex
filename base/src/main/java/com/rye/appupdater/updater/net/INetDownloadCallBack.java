package com.rye.appupdater.updater.net;

import java.io.File;

/**
 * Created By RyeCatcher
 * at 2019/9/2
 */
public interface INetDownloadCallBack {

    void success(File apkFile);

    void progress(int progress);

    void failed(Throwable throwable);
}
