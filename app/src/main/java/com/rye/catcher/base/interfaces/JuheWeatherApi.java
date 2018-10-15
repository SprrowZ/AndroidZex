package com.rye.catcher.base.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ZZG on 2018/9/10.
 */
public interface JuheWeatherApi {
    @GET("/weather/index")
    Call<ResponseBody> getWeather( @Query("format") int format,
                                   @Query("cityname") String cityname,
                                  @Query("key") String key);
}
