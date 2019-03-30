package com.rye.catcher.base.interfaces;

import com.rye.catcher.sdks.beans.TangBean;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by ZZG on 2018/9/10.
 */
public interface FreeApi {
    //聚合天气数据
    @GET("/weather/index")
    Call<ResponseBody> getWeather( @Query("format") int format,
                                   @Query("cityname") String cityname,
                                  @Query("key") String key);
    //
    @GET("/weather/index")
    Observable<ResponseBody> getWeather2(@Query("format") int format,
                                         @Query("cityname") String cityname,
                                         @Query("key") String key);
    //随机一首唐诗
    @POST("/recommendPoetry/")
    Observable<ResponseBody> getTangPoetry();

}
