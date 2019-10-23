package com.rye.catcher.project.mvp;

/**
 * Created at 2019/2/27.
 *
 * @author Zzg
 * @function:
 */
public interface BaseCallback<T> {
    void onSuccess(T data);
    void onFailure(String msg);
    void onError();
    void onComplete();
}
