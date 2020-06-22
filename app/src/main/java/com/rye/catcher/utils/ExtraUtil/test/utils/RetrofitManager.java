package com.rye.catcher.utils.ExtraUtil.test.utils;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created at 2019/3/18.
 *
 * @author Zzg
 * @function:
 */
public enum RetrofitManager {
    INSTANCE;
   public Retrofit getClient(String baseUrl){
       Retrofit client=new Retrofit.Builder()
               .client(OkHttpManager.getInstance().getClient())
               .addConverterFactory(GsonConverterFactory.create())
               .baseUrl(baseUrl)
               .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
               .build();
     return client;
   }
}
