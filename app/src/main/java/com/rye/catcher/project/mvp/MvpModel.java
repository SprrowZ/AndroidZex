package com.rye.catcher.project.mvp;

import android.os.Handler;

/**
 * Created at 2019/2/27.
 *
 * @author Zzg
 * @function: Model层
 */
public class MvpModel {
    /**
     * 获取网络数据接口
     * @param param
     * @param callback
     */
    public static void getNetData(final String param,final  MvpCallback callback){
        /**
         * 这里用handler模拟网络请求
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (param){
                    case "normal":
                        callback.onSuccess("Every thing is Ok!");
                        break;
                    case "failure":
                        callback.onFailure("Wow,you are failed!");
                        break;
                    case "error":
                        callback.onError();
                        break;
                }
                callback.onComplete();
            }
        },2000);
    }
}
