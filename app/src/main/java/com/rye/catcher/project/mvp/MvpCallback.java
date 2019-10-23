package com.rye.catcher.project.mvp;

/**
 * Created at 2019/2/27.
 *
 * @author Zzg
 * @function: Presenter与Model交互的中间层
 */
public interface MvpCallback extends  BaseCallback<String>{
    void onSuccess(String data);
    void onFailure(String msg);
    void onError();
    void onComplete();
}
