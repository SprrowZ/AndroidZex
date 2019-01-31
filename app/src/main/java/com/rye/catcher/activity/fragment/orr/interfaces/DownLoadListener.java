package com.rye.catcher.activity.fragment.orr.interfaces;

/**
 * Created at 2019/1/29.
 *
 * @author Zzg
 * @function:
 */
public interface DownLoadListener {
    void onStart();
    void onProgress(int progress);
    void onFinish(String path);
    void onFail(String errorInfo);
}
