package com.rye.catcher.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.collection.SparseArrayCompat;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.rye.catcher.BaseOldActivity;
import com.rye.catcher.R;
import com.rye.catcher.base.interfaces.FreeApi;
import com.rye.catcher.project.dao.KeyValueMgr;
import com.rye.catcher.base.sdks.beans.TangBean;
import com.rye.catcher.base.sdks.beans.WeatherBean;
import com.rye.catcher.base.sdks.gmap.AmapManager;
import com.rye.catcher.base.sdks.gmap.AmapResult;
import com.rye.base.utils.DateUtils;
import com.rye.base.utils.DeviceUtils;
import com.rye.catcher.utils.FileUtil;
import com.rye.catcher.utils.Old_ApplicationUtil;
import com.rye.catcher.utils.ExtraUtil.Bean;
import com.rye.base.common.Constant;
import com.rye.catcher.utils.ExtraUtil.test.utils.RetrofitManager;
import com.rye.catcher.utils.permission.PermissionUtils;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by ZZG on 2018/9/7.
 */
public class SplashActivity extends BaseOldActivity {
    private static  final  String TAG="SplashActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_splash);
        ButterKnife.bind(this);
        initEx();
        SparseArrayCompat map =new SparseArrayCompat();
        map.size();
      Log.i("rrtt", Color.parseColor("#FF6186")+"\n" +Color.parseColor("#FAAB4B"));
    }



    private void initEx() {
        //安装申请权限

        authority();

        tangObservable();
      //  weatherObservable();

        //获取设备尺寸信
        int sw= DeviceUtils.getScreenSw();
        Log.i(TAG, "Size: "+sw);
        FileUtil.writeUserLog("DEVICE-SIZE:"+sw);
    }

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
        Observable.just(AmapManager.getInstance().initLocation(this))
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

    private void authority() {
        PermissionUtils.requestPermission(this,"本地文件读写和手机状态需要此类权限,请去设置里授予权限",
                false,data ->{
                    openMain();
                },0,
                Permission.WRITE_EXTERNAL_STORAGE,Permission.ACCESS_COARSE_LOCATION);//CALL_PHONE先去掉
    }

    private void openMain(){
        Observable.timer(1000, TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                startActivityByAlpha(new Intent(SplashActivity.this,MainActivityEx.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        AmapManager.getInstance().destroyLocation();
        super.onDestroy();
    }


}
