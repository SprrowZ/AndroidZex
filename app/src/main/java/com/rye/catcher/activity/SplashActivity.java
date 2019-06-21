package com.rye.catcher.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.rye.catcher.BaseActivity;
import com.rye.catcher.R;
import com.rye.catcher.base.interfaces.FreeApi;
import com.rye.catcher.common.KeyValueMgrZ;
import com.rye.catcher.project.dao.KeyValueMgr;
import com.rye.catcher.base.sdks.beans.TangBean;
import com.rye.catcher.base.sdks.beans.WeatherBean;
import com.rye.catcher.base.sdks.gmap.AmapManager;
import com.rye.catcher.base.sdks.gmap.AmapResult;
import com.rye.catcher.utils.DateUtils;
import com.rye.catcher.utils.DeviceUtils;
import com.rye.catcher.utils.EchatAppUtil;
import com.rye.catcher.utils.ExtraUtil.Bean;
import com.rye.catcher.utils.ExtraUtil.Constant;
import com.rye.catcher.utils.ExtraUtil.test.utils.RetrofitManager;
import com.rye.catcher.utils.FileUtils;
import com.rye.catcher.utils.permission.PermissionUtils;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;

import butterknife.BindView;
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
public class SplashActivity extends BaseActivity {
    private static  final  String TAG="SplashActivity";
    @BindView(R.id.image)
     ImageView image;
    private JumpHandler mHandler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_splash);
        ButterKnife.bind(this);
        initEx();

    }



    private void initEx() {
        //安装申请权限
        mHandler=new JumpHandler(this);
        authority();
        Glide.with(this)
                .asDrawable()
                .load(R.drawable.ling).into(image);
        tangObservable();
      //  weatherObservable();

        //获取设备尺寸信
        int sw= DeviceUtils.getScreenSw(EchatAppUtil.getAppContext());
        Log.i(TAG, "Size: "+sw);
        FileUtils.writeUserLog("DEVICE-SIZE:"+sw);
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
                .flatMap(new Function<ResponseBody, ObservableSource<TangBean>>() {
                    @Override
                    public ObservableSource<TangBean> apply(ResponseBody responseBody) throws Exception {
                        String result=responseBody.string();
                        Log.i(TAG, "tangObservable:"+result);
                        JSONObject json=JSONObject.parseObject(result);
                        TangBean bean=JSONObject.toJavaObject(json,TangBean.class);
                        return Observable.just(bean);
                    }
                }).subscribe(new Consumer<TangBean>() {
            @Override
            public void accept(TangBean bean) throws Exception {
                Log.i(TAG, "acceptZZZ: "+ bean.toString());
                EventBus.getDefault().postSticky(bean);
            }
        });

    }
    private void weatherObservable(){
        if ("".equals(KeyValueMgrZ.getValue(Constant.WEATHER_UPDATE_TIME))) {
            KeyValueMgrZ.saveValue(Constant.WEATHER_UPDATE_TIME, System.currentTimeMillis());
            getWeather();
            Log.i(TAG, "getWeather: 字段为空");
        } else {
            boolean needRefreshWeather = !DateUtils.isToday(Long.parseLong(KeyValueMgrZ.getValue(Constant.WEATHER_UPDATE_TIME)));
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
                    Message message=mHandler.obtainMessage();
                    message.what=1;
                    mHandler.sendMessageDelayed(message,4000);
                },0,
                Permission.WRITE_EXTERNAL_STORAGE,Permission.CALL_PHONE,Permission.ACCESS_COARSE_LOCATION);
    }


    @Override
    protected void onDestroy() {
        AmapManager.getInstance().destroyLocation();
        super.onDestroy();
    }

    /**
     * 静态内部类，弱引用防止内存泄露
     */
    private static class JumpHandler extends Handler{
        private WeakReference<SplashActivity> weakReference;
        public JumpHandler(SplashActivity activity){
            weakReference=new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            SplashActivity zActivity=weakReference.get();
            switch (msg.what){
                case 1:
                    zActivity.startActivityByAlpha(new Intent(zActivity,MainActivityEx.class));
                    zActivity.finish();
                    break;
            }
            super.handleMessage(msg);
        }
    }
}
