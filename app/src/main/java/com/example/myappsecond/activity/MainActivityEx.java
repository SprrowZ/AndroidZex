package com.example.myappsecond.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.myappsecond.BaseActivity;
import com.example.myappsecond.R;
import com.example.myappsecond.base.interfaces.JuheWeatherApi;
import com.example.myappsecond.activity.fragment.FrdFragment;
import com.example.myappsecond.activity.fragment.SettingsFragment;
import com.example.myappsecond.activity.fragment.WeixinFragment;

import com.example.myappsecond.project.dao.KeyValueMgr;
import com.example.myappsecond.sdks.beans.WeatherBean;
import com.example.myappsecond.sdks.gmap.AmapAPI;
import com.example.myappsecond.sdks.gmap.AmapResult;
import com.example.myappsecond.utils.DateUtils;
import com.example.myappsecond.utils.ExtraUtil.Bean;
import com.example.myappsecond.utils.ExtraUtil.Constant;
import com.example.myappsecond.utils.JsonUtils;


import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindBitmap;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ZZG on 2018/8/12.
 */
public class MainActivityEx extends BaseActivity {
    private static  final  String TAG="MainActivityEx";
    @BindView(R.id.message) //被修饰的不能用private Or static修饰
    public LinearLayout message;
    @BindView(R.id.friend)
    public LinearLayout friend;
    @BindView(R.id.dynamic)
    public LinearLayout dynamic;
    @BindViews({R.id.message_iv, R.id.friend_iv, R.id.dynamic_iv})
    public List<ImageView> imageViewList;
    @BindViews({R.id.message_tv, R.id.friend_tv, R.id.dynamic_tv})
    public List<TextView> textViewList;
    @BindColor(R.color.grayBottom)
    int grayBottom;
    @BindColor(R.color.violetBottom)
    int violetBottom;
    @BindBitmap(R.drawable.icon_normal1)
     Bitmap icon_normal1;
    @BindBitmap(R.drawable.icon_normal2)
    Bitmap icon_normal2;
    @BindBitmap(R.drawable.icon_normal3)
    Bitmap icon_normal3;
    @BindBitmap(R.drawable.icon_pressed1)
    Bitmap icon_pressed1;
    @BindBitmap(R.drawable.icon_pressed2)
    Bitmap icon_pressed2;
    @BindBitmap(R.drawable.icon_pressed3)
    Bitmap icon_pressed3;
//    @OnLongClick(R.id.dynamic_tv)
//    public boolean start(){
//        return true;
//    }
    //地理位置
    private AmapResult amapResult;
    private WeixinFragment weixinFragment;
    private FrdFragment frdFragment;
    private SettingsFragment settingsFragment;
    private List<Fragment> mFragments;
    //fragment动态切换
    private FragmentManager manager = getSupportFragmentManager();
    private FragmentTransaction transaction = manager.beginTransaction();
    Handler mapHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1://返回一次定位结果
                    amapResult= (AmapResult) msg.obj;
                    getWeather(amapResult);
                    Log.i(TAG, "errorCode"+amapResult.getErrorCode());
                    break;
                case 11://LastKnowLocation
                    amapResult= (AmapResult) msg.obj;
                    getWeather(amapResult);
                    Log.i(TAG, "LastKnowLocationCallback: "+amapResult.getErrorCode());
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ex);
        ButterKnife.bind(this);
  //      EventBus.getDefault().register(this);
        init();
    }

    private void init() {
        mFragments = new ArrayList<>();
        weixinFragment = new WeixinFragment();
        frdFragment = new FrdFragment();
        settingsFragment = new SettingsFragment();
        mFragments.add(weixinFragment);
        mFragments.add(frdFragment);
        mFragments.add(settingsFragment);
        transaction.add(R.id.container, mFragments.get(0))
                .add(R.id.container, mFragments.get(1))
                .add(R.id.container, mFragments.get(2))
                .hide(mFragments.get(1))
                .hide(mFragments.get(2))
                .show(mFragments.get(0)).commit();

        //获取定位数据
        AmapAPI.getInstance().initLocation(this,mapHandler);
    }

    /**
     * 获取天气信息
     */
    private void getWeather(AmapResult amapResult) {
        if ("".equals(KeyValueMgr.getValue(Constant.WEATHER_UPDATE_TIME))){
           KeyValueMgr.saveValue(Constant.WEATHER_UPDATE_TIME,System.currentTimeMillis());
        }
        boolean needRefreshWeather=!DateUtils.isToday(Long.parseLong(KeyValueMgr.getValue(Constant.WEATHER_UPDATE_TIME)));
        Log.i(TAG, "getWeather: "+String.valueOf(needRefreshWeather));
        if (needRefreshWeather){
            new Thread(()->{
                KeyValueMgr.saveValue(Constant.WEATHER_UPDATE_TIME,System.currentTimeMillis());
                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl(Constant.JUHE_WEATHER)
                        .addConverterFactory(GsonConverterFactory.create())
                        //   .client(HttpLogger.getOkHttpClient())//增加日志拦截
                        .build();
                JuheWeatherApi weatherApi=retrofit.create(JuheWeatherApi.class);
                Call<ResponseBody> call= weatherApi.getWeather(1,amapResult.getCity(),Constant.JUHE_WEATHER_KEY);
                Log.i(TAG, "getWeather: weatherApi");
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String result=response.body().string();//返回结果
                            dealWeather(result);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.i(TAG, "onFailure:weatherApi ");
                    }
                });
            }).start();
        }else {
            Log.i(TAG, "getWeather: 未满一天");
        }
  
    }

    private void dealWeather(String result) {
        Log.i(TAG, "onResponse:weatherApi "+result);
        JSONObject todayTemperature= null;
        try {
            todayTemperature = JsonUtils.toJSONObject(result)
                    .getJSONObject(WeatherBean.WEATHER_RESULT)
                    .getJSONObject(WeatherBean.WEATHER_TODAY);
            if (todayTemperature!=null){
                String temperature=todayTemperature.getString(WeatherBean.TEMPERATURE);
                String weather=todayTemperature.getString(WeatherBean.WEATHER);
                Log.i(TAG, "weatherApi: "+"temperature:"+temperature+"weather:"+weather);
                Bean bean=new Bean();
                bean.set(WeatherBean.TEMPERATURE,temperature);
                bean.set(WeatherBean.WEATHER,weather);
                //发送广播
                EventBus.getDefault().postSticky(bean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void reset() {
        imageViewList.get(0).setImageBitmap(icon_normal1);
        imageViewList.get(1).setImageBitmap(icon_normal2);
        imageViewList.get(2).setImageBitmap(icon_normal3);
        textViewList.get(0).setTextColor(grayBottom);
        textViewList.get(1).setTextColor(grayBottom);
        textViewList.get(2).setTextColor(grayBottom);
    }

    private void setSelect(int number) {
        switch (number) {
            case 0:
                imageViewList.get(0).setImageBitmap(icon_pressed1);
                textViewList.get(0).setTextColor(violetBottom);
                if (mFragments.get(0).isVisible()) {//可见

                } else {
                    transaction.hide(mFragments.get(1))
                            .hide(mFragments.get(2))
                            .show(mFragments.get(0));
                }
                break;
            case 1:
                imageViewList.get(1).setImageBitmap(icon_pressed2);
                textViewList.get(1).setTextColor(violetBottom);
                if (mFragments.get(1).isVisible()) {

                } else {
                    transaction.hide(mFragments.get(0))
                            .hide(mFragments.get(2))
                            .show(mFragments.get(1));
                }
                break;
            case 2:
                imageViewList.get(2).setImageBitmap(icon_pressed3);
                textViewList.get(2).setTextColor(violetBottom);
                if (mFragments.get(2).isVisible()) {

                } else {
                    transaction.hide(mFragments.get(1))
                            .hide(mFragments.get(0))
                            .show(mFragments.get(2));
                }
                break;
        }
        transaction.commit();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {//为了解决崩溃点击无效的问题
        // super.onSaveInstanceState(outState);
    }

    @OnClick({R.id.message, R.id.friend, R.id.dynamic})
    public void onViewClicked(View view) {
        reset();
        // 点击时启动trancaction事件,不能重复commit
        transaction = manager.beginTransaction();
        switch (view.getId()) {
            case R.id.message:
                setSelect(0);
                break;
            case R.id.friend:
                setSelect(1);
                break;
            case R.id.dynamic:
                setSelect(2);
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
