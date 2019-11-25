package com.catcher.zzsdk.okhttp.response;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author admin
 * @function Json字符串回调
 */
public abstract  class JsonCallback<T extends Object> implements Callback {
    private Type  mClass;//传入的class

    public JsonCallback(){
        this.mClass=getType();
    }
    @Override
    public void onFailure(Call call, IOException e) {
           onError(e.toString());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String re=response.body().string();
       if (response.code()==200){
           if (mClass!=null){
               T result= (T) new Gson().fromJson(re,mClass);
               onSuccess(result);
           }else{
               onSuccess((T) response.body().string());
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

    /**
     * 泛型获取Class-------------So Important！！！！！！！！！！！！！！
     * @return
     */
    private Class<T> getType(){
        Class<T> tClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]; // 根据当前类获取泛型的Type

        return  tClass;
    }
}
