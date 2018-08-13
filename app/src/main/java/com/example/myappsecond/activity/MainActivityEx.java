package com.example.myappsecond.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myappsecond.BaseActivity;
import com.example.myappsecond.R;
import com.example.myappsecond.fragment.FrdFragment;
import com.example.myappsecond.fragment.SettingsFragment;
import com.example.myappsecond.fragment.WeixinFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZZG on 2018/8/12.
 */
public class MainActivityEx extends BaseActivity implements View.OnClickListener {
    private WeixinFragment weixinFragment;
    private FrdFragment frdFragment;
    private SettingsFragment settingsFragment;
    private LinearLayout message;
    private LinearLayout friend;
    private LinearLayout dynamic;
    private ImageView message_iv;
    private TextView message_tv;
    private ImageView friend_iv;
    private TextView friend_tv;
    private ImageView dynamic_iv;
    private TextView dynamic_tv;
    private List<Fragment> mFragments;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main_ex);
       initView();
       init();
    }

    private void initView() {
        message=findViewById(R.id.message);
        friend=findViewById(R.id.friend);
        dynamic=findViewById(R.id.dynamic);
        message_iv=findViewById(R.id.message_iv);
        message_tv=findViewById(R.id.message_tv);
        friend_iv=findViewById(R.id.friend_iv);
        friend_tv=findViewById(R.id.friend_tv);
        dynamic_iv=findViewById(R.id.dynamic_iv);
        dynamic_tv=findViewById(R.id.dynamic_tv);
        message.setOnClickListener(this);
        friend.setOnClickListener(this);
        dynamic.setOnClickListener(this);
    }

    private void init() {
        mFragments=new ArrayList<>();
        weixinFragment=new WeixinFragment();
        frdFragment=new FrdFragment();
        settingsFragment=new SettingsFragment();
        mFragments.add(weixinFragment);
        mFragments.add(frdFragment);
        mFragments.add(settingsFragment);

       getSupportFragmentManager().beginTransaction()
               .add(R.id.container,mFragments.get(0))
               .add(R.id.container,mFragments.get(1))
               .add(R.id.container,mFragments.get(2))
               .hide(mFragments.get(1))
               .hide(mFragments.get(2))
               .show(mFragments.get(0)).commit();

    }

    @Override
    public void onClick(View v) {
        reset();
        switch (v.getId()){
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
    private void reset(){
        message_iv.setImageResource(R.drawable.icon_normal1);
        friend_iv.setImageResource(R.drawable.icon_normal2);
        dynamic_iv.setImageResource(R.drawable.icon_normal3);
        message_tv.setTextColor(Color.parseColor("#ececec"));
        friend_tv.setTextColor(Color.parseColor("#ececec"));
        dynamic_tv.setTextColor(Color.parseColor("#ececec"));
    }
    private void setSelect(int number){
        switch (number){
            case 0:
                message_iv.setImageResource(R.drawable.icon_pressed1);
                message_tv.setTextColor(Color.parseColor("#583aca"));
                if (mFragments.get(0).isVisible()){

                }else {
                    getSupportFragmentManager().beginTransaction()
                            .hide(mFragments.get(1))
                            .hide(mFragments.get(2))
                            .show(mFragments.get(0)).commit();
                }


                break;
            case 1:
                friend_iv.setImageResource(R.drawable.icon_pressed2);
                friend_tv.setTextColor(Color.parseColor("#583aca"));
                if (mFragments.get(1).isVisible()){

                }else {
                    getSupportFragmentManager().beginTransaction()
                            .hide(mFragments.get(0))
                            .hide(mFragments.get(2))
                            .show(mFragments.get(1)).commit();
                }
                break;
            case 2:
                dynamic_iv.setImageResource(R.drawable.icon_pressed3);
                dynamic_tv.setTextColor(Color.parseColor("#583aca"));
                if (mFragments.get(2).isVisible()){

                }else {
                    getSupportFragmentManager().beginTransaction()
                            .hide(mFragments.get(1))
                            .hide(mFragments.get(0))
                            .show(mFragments.get(2)).commit();
                }
                break;
        }
    }
}
