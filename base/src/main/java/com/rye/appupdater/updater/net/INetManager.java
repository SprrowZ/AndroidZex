package com.rye.appupdater.updater.net;

import java.io.File;

/**
 * Created By RyeCatcher
 * at 2019/9/2
 * 接口化网络请求
 */
public interface INetManager {
    /**
     * 请求
     * @param url
     * @param callback
     */
    void get(String url,INetCallback callback,Object tag);

    /**
     * 下载更新包
     * @param url
     * @param targetFile
     * @param callBack
     */
    void download(String url, File targetFile,INetDownloadCallBack callBack,Object tag);

    /**
     * 取消下载，需要根据tag决定取消哪次call，
     * 所以get和download里也要新加tag
     * @param tag
     */
    void cancel(Object tag);
}
