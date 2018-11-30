package com.rye.catcher.activity;

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


import com.rye.catcher.BaseActivity;
import com.rye.catcher.R;
import com.rye.catcher.base.interfaces.JuheWeatherApi;
import com.rye.catcher.activity.fragment.LMFragment;
import com.rye.catcher.activity.fragment.SettingsFragment;
import com.rye.catcher.activity.fragment.YLJFragment;

import com.rye.catcher.project.dao.KeyValueMgr;
import com.rye.catcher.sdks.HttpLogger;
import com.rye.catcher.sdks.beans.WeatherBean;
import com.rye.catcher.sdks.gmap.AmapAPI;
import com.rye.catcher.sdks.gmap.AmapResult;
import com.rye.catcher.utils.DateUtils;
import com.rye.catcher.utils.ExtraUtil.Bean;
import com.rye.catcher.utils.ExtraUtil.Constant;
import com.rye.catcher.utils.FileUtils;
import com.rye.catcher.utils.JsonUtils;
import com.rye.catcher.utils.ToastUtils;


import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
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
    private long back_pressed;
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

    //地理位置
    private AmapResult amapResult;
    //当前Fragment
    private  Fragment currentFragment;
    private  int currentPos=-1;
    private final  Handler mapHandler=new MapHandler(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ex);
        ButterKnife.bind(this);
  //      EventBus.getDefault().register(this);
        init();
    }

    private void init() {
        selectItem(0);
        //获取定位数据
        AmapAPI.getInstance().initLocation(this,mapHandler);
        FileUtils.writeUserLog(TAG+"onCreate:");
        Log.i(TAG, "onCrate: ...");
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
        setSelect(pos);
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
        if ("".equals(KeyValueMgr.getValue(Constant.WEATHER_UPDATE_TIME))){
            Log.i(TAG, "getWeather: 字段为空");
           KeyValueMgr.saveValue(Constant.WEATHER_UPDATE_TIME,System.currentTimeMillis());
           weatherThread();
        }else{
            boolean needRefreshWeather=!DateUtils.isToday(Long.parseLong(KeyValueMgr.getValue(Constant.WEATHER_UPDATE_TIME)));
            Log.i(TAG, "getWeather: "+String.valueOf(needRefreshWeather));
            if (needRefreshWeather){
                weatherThread();
            }else {
                Log.i(TAG, "getWeather: 未满一天");
            }
        }

  
    }

    /**
     * 天气请求Thread
     */
    private void weatherThread(){
        new Thread(()->{
            KeyValueMgr.saveValue(Constant.WEATHER_UPDATE_TIME,System.currentTimeMillis());
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl(Constant.JUHE_WEATHER)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(HttpLogger.getOkHttpClient())//增加日志拦截
                    .build();
            JuheWeatherApi weatherApi=retrofit.create(JuheWeatherApi.class);
            Call<ResponseBody> call= weatherApi.getWeather(1,amapResult.getCity(),Constant.JUHE_WEATHER_KEY);
            Log.i(TAG, "getWeather: weatherApi");
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String result=response.body().string();//返回结果
                        FileUtils.writeUserLog("getWeather-->onResponse:"+result);
                        dealWeather(result);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.i(TAG, "onFailure:weatherApi ");
                    FileUtils.writeUserLog("getWeather-->onFailure"+t.toString());
                }
            });
        }).start();
    }

    /**
     * 天气结果处理
     *
     */
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
                Log.i(TAG, "weatherApi: --->"+"temperature:"+temperature+"weather:"+weather);
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

                break;
            case 1:
                imageViewList.get(1).setImageBitmap(icon_pressed2);
                textViewList.get(1).setTextColor(violetBottom);

                break;
            case 2:
                imageViewList.get(2).setImageBitmap(icon_pressed3);
                textViewList.get(2).setTextColor(violetBottom);
                break;
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {//为了解决崩溃点击无效的问题
        // super.onSaveInstanceState(outState);
    }

    @OnClick({R.id.message, R.id.friend, R.id.dynamic})
    public void onViewClicked(View view) {
        reset();// 重置底部栏
        switch (view.getId()) {
            case R.id.message:
             selectItem(0);
                break;
            case R.id.friend:
              selectItem(1);
                break;
            case R.id.dynamic:
               selectItem(2);
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ...");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ...");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ...");
        new Thread(()->{
            FileUtils.writeUserLog(TAG+"onDestroy:");
        }).start();

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

    /**
     * handler内存泄露处理
     */
    private static class MapHandler extends Handler{
        WeakReference<MainActivityEx> mActivity;
        public MapHandler(MainActivityEx mainActivityEx){
            mActivity=new WeakReference<>(mainActivityEx);
        }
        @Override
        public void handleMessage(Message msg) {
            MainActivityEx activityEx=mActivity.get();
            switch (msg.what){
                case 1://返回一次定位结果
                    activityEx.amapResult= (AmapResult) msg.obj;
                    activityEx.getWeather(activityEx.amapResult);
                    Log.i(TAG, "errorCode"+activityEx.amapResult.getErrorCode());
                    break;
                case 11://LastKnowLocation
                    activityEx.amapResult= (AmapResult) msg.obj;
                    activityEx.getWeather(activityEx.amapResult);
                    Log.i(TAG, "LastKnowLocationCallback: "+activityEx.amapResult.getErrorCode());
            }
            super.handleMessage(msg);
        }
    }
}
