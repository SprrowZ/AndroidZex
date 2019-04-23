package com.rye.catcher.beans.binding;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.rye.catcher.activity.FirstActivity;
import com.rye.catcher.R;
import com.rye.catcher.RetrofitActivity;
import com.rye.catcher.activity.CameraActivity;
import com.rye.catcher.activity.ORRActivity;
import com.rye.catcher.activity.ProjectMainActivity;
import com.rye.catcher.activity.ctmactivity.CtmMainActivity;
import com.rye.catcher.project.ReviewActivity;
import com.rye.catcher.project.animations.AnimMainActivity;
import com.rye.catcher.project.review.KeepJavaActivity;
import com.rye.catcher.utils.PermissionsUtil;
import com.rye.catcher.utils.ToastUtils;

/**
 * Created by 18041at 2019/4/14
 * Function: 第三屏点击事件
 */
public class ClickHandler {
    private Context mContext;
    public void clickEvent(View view){
        mContext=view.getContext();
        switch (view.getId()){
            case R.id.orr:
                mContext.startActivity(new Intent(mContext, ORRActivity.class));
                break;
            case R.id.javaMore:
                mContext.startActivity(new Intent(mContext, KeepJavaActivity.class));
                break;
            case R.id.someDemo:
                mContext.startActivity(new Intent(mContext, FirstActivity.class));
                break;
            case R.id.retrofit:
                mContext.startActivity(new Intent(mContext, RetrofitActivity.class));
                break;
            case R.id.camera:
                authority();
                Intent intent = new Intent(mContext, CameraActivity.class);
                intent.putExtra("info", "emmmm...");
                mContext.startActivity(intent);
                break;
            case R.id.review:
                Intent intent5 = new Intent(mContext, ReviewActivity.class);
                mContext.startActivity(intent5);
                break;
            case R.id.animation:
                Intent intent7 = new Intent(mContext, AnimMainActivity.class);
                mContext.startActivity(intent7);
                break;
            case R.id.custom:
                Intent intent8 = new Intent(mContext, CtmMainActivity.class);
                mContext.startActivity(intent8);
                break;
            case R.id.project:
                Intent intent9 = new Intent(mContext, ProjectMainActivity.class);
                mContext.startActivity(intent9);
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
}
