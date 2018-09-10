package com.example.myappsecond.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
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
import com.example.myappsecond.fragment.FrdFragment;
import com.example.myappsecond.fragment.SettingsFragment;
import com.example.myappsecond.fragment.WeixinFragment;

import com.example.myappsecond.sdks.gmap.AmapAPI;
import com.example.myappsecond.sdks.gmap.AmapResult;
import com.example.myappsecond.utils.ExtraUtil.Constant;
import com.example.myappsecond.utils.ToastUtils;
import com.example.myappsecond.utils.permission.PermissionUtils;
import com.yanzhenjie.permission.Permission;


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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ZZG on 2018/8/12.
 */
public class MainActivityEx extends BaseActivity {
    private WeixinFragment weixinFragment;
    private FrdFragment frdFragment;
    private SettingsFragment settingsFragment;

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
    private List<Fragment> mFragments;
    //fragment动态切换
    private FragmentManager manager = getSupportFragmentManager();
    private FragmentTransaction transaction = manager.beginTransaction();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ex);
        ButterKnife.bind(this);
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
        getWeather();
    }

    private void getWeather() {
        new Thread(()->{
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl(Constant.JUHE_WEATHER)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Call<ResponseBody> call=
        })
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
}
