package com.rye.catcher.agocode;

import android.annotation.SuppressLint;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.rye.base.common.Constant;
import com.rye.base.utils.DateUtils;
import com.rye.catcher.RyeCatcherApp;
import com.rye.catcher.base.interfaces.FreeApi;
import com.rye.catcher.base.sdks.beans.TangBean;
import com.rye.catcher.base.sdks.beans.WeatherBean;
import com.rye.catcher.base.sdks.gmap.AmapManager;
import com.rye.catcher.base.sdks.gmap.AmapResult;
import com.rye.catcher.project.dao.KeyValueMgr;
import com.rye.catcher.utils.ExtraUtil.Bean;
import com.rye.catcher.utils.ExtraUtil.test.utils.RetrofitManager;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Create by rye
 * at 2020-07-20
 *
 * @description:
 */
public class SplashAgo {
    private static final String TAG = "SPLASH";
    /**
     * Rxjava+Retrofit+Okhttp
     */
    @SuppressLint("CheckResult")
    private void tangObservable(){
        RetrofitManager.INSTANCE
                .getClient(Constant.TANG_POETRY)
                .create(FreeApi.class)
                .getTangPoetry()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap((Function<ResponseBody, ObservableSource<TangBean>>) responseBody -> {
                    String result=responseBody.string();
                    Log.i(TAG, "tangObservable:"+result);
                    JSONObject json=JSONObject.parseObject(result);
                    TangBean bean=JSONObject.toJavaObject(json,TangBean.class);
                    return Observable.just(bean);
                }).subscribe(bean -> {
            Log.i(TAG, "acceptZZZ: "+ bean.toString());
            EventBus.getDefault().postSticky(bean);
        });

    }
    private void weatherObservable(){
        if ("".equals(KeyValueMgr.getValue(Constant.WEATHER_UPDATE_TIME))) {
            KeyValueMgr.saveValue(Constant.WEATHER_UPDATE_TIME, System.currentTimeMillis());
            getWeather();
            Log.i(TAG, "getWeather: 字段为空");
        } else {
            boolean needRefreshWeather = !DateUtils.isToday(Long.parseLong(KeyValueMgr.getValue(Constant.WEATHER_UPDATE_TIME)));
            Log.i(TAG, "getWeather: " + String.valueOf(needRefreshWeather));
            if (needRefreshWeather) {
                getWeather();
            } else {
                Log.i(TAG, "getWeather: 未满一天");
            }
        }

    }

    /**
     * 获取天气信息，Okhttp+Retrofit+Rxjava；
     */
    private void getWeather(){
        KeyValueMgr.saveValue(Constant.WEATHER_UPDATE_TIME, System.currentTimeMillis());
        Observable.just(AmapManager.getInstance().initLocation(RyeCatcherApp.getContext()))
                .flatMap((Function<AmapResult, ObservableSource<String>>) amapResult -> {
                    Log.i(TAG, "apply: "+amapResult.toString());
                    return RetrofitManager.INSTANCE
                            .getClient(Constant.JUHE_WEATHER)
                            .create(FreeApi.class)
                            .getWeather2(1,amapResult.getCity(),Constant.JUHE_WEATHER_KEY)
                            .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> {
                                String result= responseBody.string();
                                return Observable.just(result);
                            });
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    Log.i(TAG, "accept: "+result);
                    dealWeather(result);
                });
    }
    private void dealWeather(String result) {
        Log.i(TAG, "onResponse:weatherApi " + result);
        JSONObject todayTemperature = null;
        todayTemperature = JSONObject.parseObject(result)
                .getJSONObject(WeatherBean.WEATHER_RESULT)
                .getJSONObject(WeatherBean.WEATHER_TODAY);
        if (todayTemperature != null) {
            String temperature = todayTemperature.getString(WeatherBean.TEMPERATURE);
            String weather = todayTemperature.getString(WeatherBean.WEATHER);
            Log.i(TAG, "weatherApi: --->" + "temperature:" + temperature + "weather:" + weather);
            Bean bean = new Bean();
            bean.set(WeatherBean.TEMPERATURE, temperature);
            bean.set(WeatherBean.WEATHER, weather);
            //发送广播
            EventBus.getDefault().postSticky(bean);
        }
    }
}
