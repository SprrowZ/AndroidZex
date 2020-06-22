package com.rye.catcher.activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;


import com.alibaba.fastjson.JSONObject;
import com.rye.catcher.BaseOldActivity;
import com.rye.catcher.R;
import com.rye.catcher.base.interfaces.FreeApi;
import com.rye.catcher.activity.fragment.LMFragment;
import com.rye.catcher.activity.fragment.SettingsFragment;
import com.rye.catcher.activity.fragment.YLJFragment;

import com.rye.catcher.project.dao.KeyValueMgr;

import com.rye.catcher.base.sdks.HttpLogger;
import com.rye.catcher.base.sdks.beans.WeatherBean;
import com.rye.catcher.base.sdks.gmap.AmapResult;
import com.rye.base.utils.DateUtils;
import com.rye.catcher.project.helpers.kotlins.BroadcastManager;
import com.rye.catcher.utils.ExtraUtil.Bean;
import com.rye.base.common.Constant;

import com.rye.catcher.utils.FileUtil;
import com.rye.catcher.utils.ToastUtils;


import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import butterknife.BindView;

import butterknife.ButterKnife;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ZZG on 2018/8/12.
 */
public class MainActivityEx extends BaseOldActivity {
    private static final String TAG = "MainActivityEx";

    private static final int DEVICE_REQUEST_CODE=99;
    private long back_pressed;
    //地理位置
    private AmapResult amapResult;

    @BindView(R.id.design_bottom_sheet)
    BottomNavigationView bottom;
    @BindView(R.id.design_nav_view)
    NavigationView design_view;

    private View  headerLayout;

   private  LinearLayout left_first;
   private LinearLayout left_second;

    private Fragment currentFragment;
    private int currentPos = -1;
   // private final Handler mapHandler = new MapHandler(this);
  //本地广播demo
   private LoginSuccessReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ex);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        selectItem(0);
        FileUtil.writeUserLog(TAG + "onCreate:");
        bottom.setOnNavigationItemSelectedListener(item -> {
            item.setChecked(true);
            switch (item.getItemId()) {
                case R.id.first:
                    selectItem(0);
                    break;
                case R.id.second:
                    selectItem(1);
                    break;
                case R.id.third:
                    selectItem(2);
                    break;
            }
            return false;
        });
        //开始注册系统广播
        startScreenBroadcastReceiver();

        //左侧
        headerLayout= design_view.inflateHeaderView(R.layout.left_details);

        left_first=headerLayout.findViewById(R.id.left_first);
        //左侧第一个点击事件
        left_first.setOnClickListener(v -> {
            Intent intent=new Intent(MainActivityEx.this,
                    LeftDetailActivityRx.class);
            startActivityForResult(intent,DEVICE_REQUEST_CODE);
        });
        //第二个点击事件，切换语言
        left_second=headerLayout.findViewById(R.id.left_second);
        left_second.setOnClickListener(v ->{

        });

        //注册登录成功广播
         receiver=new LoginSuccessReceiver();
         BroadcastManager.INSTANCE.registerLoginSuccessReceiver(receiver);

    }

    /**
     * 获取当前Fragment
     */
    private void selectItem(int pos) {
        //点击的正是当前正在显示的，直接返回
        if (currentPos == pos) return;
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (currentFragment != null) {
            //隐藏当前正在显示的fragment
            transaction.hide(currentFragment);
        }
        currentPos = pos;
        Fragment fragment = manager.findFragmentByTag(getTag(pos));
        //通过findFragmentByTag判断是否已存在目标fragment，若存在直接show，否则去add
        if (fragment != null) {
            currentFragment = fragment;
            transaction.show(fragment);
        } else {
            transaction.add(R.id.container, getFragment(pos), getTag(pos));//加TAG
        }
        transaction.commitAllowingStateLoss();
        //改变颜色值
        //  setSelect(pos);
    }
    private String getTag(int pos) {
        return "Zzg_" + pos;
    }
    private Fragment getFragment(int pos) {
        switch (pos) {
            case 0:
                currentFragment = new YLJFragment();
                break;
            case 1:
                currentFragment = new LMFragment();
                break;
            case 2:
                currentFragment = new SettingsFragment();
                break;
            default:
                currentFragment = new YLJFragment();
                break;
        }
        return currentFragment;
    }

    /**
     * 获取天气信息
     */
    private void getWeather(AmapResult amapResult) {
        if ("".equals(KeyValueMgr.getValue(Constant.WEATHER_UPDATE_TIME))) {
            Log.i(TAG, "getWeather: 字段为空");
            KeyValueMgr.saveValue(Constant.WEATHER_UPDATE_TIME, System.currentTimeMillis());
            weatherThread();
        } else {
            boolean needRefreshWeather = !DateUtils.isToday(Long.parseLong(KeyValueMgr.getValue(Constant.WEATHER_UPDATE_TIME)));
            Log.i(TAG, "getWeather: " + String.valueOf(needRefreshWeather));
            if (needRefreshWeather) {
                weatherThread();
            } else {
                Log.i(TAG, "getWeather: 未满一天");
            }
        }
    }

    /**
     * 天气请求Thread
     */
    private void weatherThread() {
        new Thread(() -> {
            KeyValueMgr.saveValue(Constant.WEATHER_UPDATE_TIME, System.currentTimeMillis());
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.JUHE_WEATHER)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(HttpLogger.getOkHttpClient())//增加日志拦截
                    .build();
            FreeApi weatherApi = retrofit.create(FreeApi.class);
            Call<ResponseBody> call = weatherApi.getWeather(1, amapResult.getCity(), Constant.JUHE_WEATHER_KEY);
            Log.i(TAG, "getWeather: weatherApi");
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String result = response.body().string();//返回结果
                        FileUtil.writeUserLog("getWeather-->onResponse:" + result);
                        dealWeather(result);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.i(TAG, "onFailure:weatherApi ");
                    FileUtil.writeUserLog("getWeather-->onFailure" + t.toString());
                }
            });
        }).start();
    }
     /**
      * 天气结果处理
      *
      */
    private void dealWeather(String result) {
        Log.i(TAG, "onResponse:weatherApi " + result);
        JSONObject todayTemperature = null;
        //            com.alibaba.fastjson.JSONObject result2= com.alibaba.fastjson.JSONObject.parseObject(result)
//            JuHeBean bean= JSON.toJavaObject(result2,JuHeBean.class);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG,"onActivityResult");
        if (resultCode==RESULT_OK){ //多语言适配
         recreate();
        }
    }

    //应该单独抽出一个类
    private class LoginSuccessReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "onReceive: "+intent.getAction()+","+intent.getStringExtra(BroadcastManager.BROADCAST_KEY));
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ...");
         FileUtil.writeUserLog(TAG + "onDestroy:");
         stopScreenStateUpdate();
         //局部广播案例
         BroadcastManager.INSTANCE.unregisterReceiver(receiver);
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            ToastUtils.shortMsg("再点一次退出应用");
        }
        back_pressed = System.currentTimeMillis();
    }


}
