package com.catcher.zzsdk.okhttp.response;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author admin
 * @function Json字符串回调
 */
public abstract  class JsonCallback<T extends Object> implements Callback {
    private T mClass;//传入的class
    @Override
    public void onFailure(Call call, IOException e) {
           onError(e.toString());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
       if (response.code()==200){
           if (mClass!=null){
               assert response.body() != null;//返回数据为空则
               T result= (T) new Gson().fromJson(response.body().toString(),mClass.getClass());
               onSuccess(result);
           }else{
               onSuccess((T) response.body().toString());
           }
       }else{
           onFailed(response.code());
       }

    }
    public abstract void onSuccess(T result);
    private void onFailed(int errorCode){
        Log.e("error","errorCode:"+errorCode);
        }
    public abstract  void onError(String error);
}
