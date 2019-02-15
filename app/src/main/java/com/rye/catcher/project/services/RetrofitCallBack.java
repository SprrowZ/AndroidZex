package com.rye.catcher.project.services;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created at 2019/2/15.
 *
 * @author Zzg
 * @function:
 */
public abstract class RetrofitCallBack<T> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
       if (response.isSuccessful()){
           onSuccess(call,response);
       }else{
           onFailure(call, new Throwable(response.message()));
       }
    }


    public abstract void onSuccess(Call<T> call, Response<T> response);
    //用于进度的回调
    public abstract void onLoading(long total, long progress) ;
}
