package com.rye.catcher.activity;


import android.content.Context;
import android.content.Intent;


import androidx.annotation.Nullable;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import com.rye.base.BaseActivity;
import com.rye.catcher.R;
import com.rye.catcher.activity.fragment.LMFragment;
import com.rye.catcher.activity.fragment.SettingsFragment;
import com.rye.catcher.activity.fragment.YLJFragment;

import com.rye.catcher.utils.FileUtil;
import com.rye.catcher.utils.ToastUtils;
import butterknife.BindView;
/**
 * Created by ZZG on 2018/8/12.
 */
public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    private static final int DEVICE_REQUEST_CODE = 99;
    private long back_pressed;

    @BindView(R.id.design_bottom_sheet)
    BottomNavigationView bottom;
    @BindView(R.id.design_nav_view)
    NavigationView design_view;

    private View headerLayout;

    private LinearLayout left_first;
    private LinearLayout left_second;

    private Fragment currentFragment;
    private int currentPos = -1;


    public static void start(Context context){
        Intent intent = new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }



    @Override
    public int getLayoutId() {
        return R.layout.activity_main_ex;
    }

    @Override
    public void initEvent() {
        selectItem(0);

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


        //左侧
        headerLayout = design_view.inflateHeaderView(R.layout.left_details);

        left_first = headerLayout.findViewById(R.id.left_first);
        //手机详情
        left_first.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,
                    LeftDetailActivityRx.class);
            startActivityForResult(intent, DEVICE_REQUEST_CODE);
        });
        //uri模仿外部跳转
        left_second = headerLayout.findViewById(R.id.left_second);
        left_second.setOnClickListener(v -> {
           ProjectMainActivityRx.start(this);
        });
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
    }

    private String getTag(int pos) {
        return "Zzg_" + pos;
    }

    private Fragment getFragment(int pos) {
        switch (pos) {
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult");
        if (resultCode == RESULT_OK) { //多语言适配
            recreate();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ...");
        FileUtil.writeUserLog(TAG + "onDestroy:");
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
