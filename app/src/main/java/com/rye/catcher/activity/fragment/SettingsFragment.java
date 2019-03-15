package com.rye.catcher.activity.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.rye.catcher.activity.CameraActivity;
import com.rye.catcher.FirstActivity;
import com.rye.catcher.R;
import com.rye.catcher.SecondActivity;
import com.rye.catcher.activity.ORRActivity;
import com.rye.catcher.activity.ProjectMainActivity;
import com.rye.catcher.activity.ctmactivity.CtmMainActivity;
import com.rye.catcher.project.ReviewTest;
import com.rye.catcher.project.animations.AnimMainActivity;
import com.rye.catcher.project.review.KeepJava;
import com.rye.catcher.utils.PermissionsUtil;
import com.rye.catcher.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zzg on 2017/10/10.
 */

public class SettingsFragment extends BaseFragment {
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;
    @BindView(R.id.cameraOne)
    Button cameraOne;
    @BindView(R.id.review)
    Button review;
    @BindView(R.id.animation)
    Button animation;
    @BindView(R.id.custom)
    Button custom;
    @BindView(R.id.project)
    Button project;
    @BindView(R.id.editText)
    EditText editText;



    @Override
    protected int getLayoutResId() {
        return R.layout.tab04;
    }

    @Override
    protected void initData() {
        setBarTitle("苍海");
    }
    @OnClick({R.id.btn1,R.id.btn2,R.id.btn3,R.id.btn4,
    R.id.progressBar,R.id.cameraOne,R.id.review,R.id.animation,
    R.id.custom,R.id.project})
    public void onViewCreated(View view){
        switch (view.getId()){
            case R.id.btn1:
                startActivity(new Intent(getActivity(), ORRActivity.class));
                break;
            case R.id.btn2:
                Intent intent4 = new Intent(getActivity(), KeepJava.class);
                startActivity(intent4);
                break;
            case R.id.btn3:
                Intent intent1 = new Intent(getActivity(), FirstActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn4:
                Intent intent2 = new Intent(getActivity(), SecondActivity.class);
                startActivity(intent2);
                break;
            case R.id.progressBar:
                new Thread(() -> {
                    try {
                        Thread.sleep(3000);
                        if (getActivity() == null) {
                            return;
                        } else {

                            getActivity().runOnUiThread(() -> progressBar.setVisibility(View.GONE));
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
                break;
            case R.id.cameraOne:
                authority();
                Intent intent3 = new Intent(getActivity(), CameraActivity.class);
                intent3.putExtra("info", "fuck...");
                startActivity(intent3);
                break;
            case R.id.review:
                Intent intent5 = new Intent(getActivity(), ReviewTest.class);
                startActivity(intent5);
                break;
            case R.id.animation:
                Intent intent7 = new Intent(getActivity(), AnimMainActivity.class);
                startActivity(intent7);
                break;
            case R.id.custom:
                Intent intent9 = new Intent(getActivity(), CtmMainActivity.class);
                startActivity(intent9);
                break;
            case R.id.project:
                Intent intent10 = new Intent(getActivity(), ProjectMainActivity.class);
                startActivity(intent10);
                break;
        }
    }



    private void authority() {
        //申请权限
        String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        PermissionsUtil.showSystemSetting = false;//是否支持显示系统设置权限设置窗口跳转
        PermissionsUtil.IPermissionsResult permissionsResult = new PermissionsUtil.IPermissionsResult() {
            @Override
            public void passPermissons() {
                ToastUtils.shortMsg("权限申请成功");
            }

            @Override
            public void forbitPermissons() {
                ToastUtils.shortMsg("权限申请失败");
            }
        };
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
