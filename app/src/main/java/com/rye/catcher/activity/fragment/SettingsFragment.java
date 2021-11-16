package com.rye.catcher.activity.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.dawn.zgstep.ui.activity.DemoActivity;
import com.rye.base.BaseFragment;
import com.rye.catcher.R;
import com.rye.catcher.RetrofitActivity;
import com.rye.catcher.activity.ORRActivity;
import com.rye.catcher.activity.ProjectMainActivityRx;
import com.rye.catcher.activity.ZTActivity;
import com.rye.catcher.activity.ctmactivity.CtmMainActivity;

import com.rye.catcher.project.review.ReviewActivity;
import com.rye.catcher.project.animations.AnimMainActivity;
import com.rye.catcher.utils.PermissionsUtil;
import com.rye.catcher.utils.ToastUtils;

import butterknife.OnClick;

/**
 * Created by zzg on 2017/10/10.
 */

public class SettingsFragment extends BaseFragment {

    private Context mContext;

    @Override
    protected int getLayoutId() {
        return R.layout.tab04;
    }

    @Override
    public void initEvent() {
        super.initEvent();
        mContext = getContext();
    }

    @OnClick({R.id.orr, R.id.javaMore, R.id.translate, R.id.rotate, R.id.camera,
            R.id.review, R.id.animation, R.id.custom, R.id.project, R.id.fragments})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.orr:
                mContext.startActivity(new Intent(mContext, ORRActivity.class));
                break;
            case R.id.javaMore:

                break;
            case R.id.translate:
                mContext.startActivity(new Intent(mContext, ZTActivity.class));
                break;
            case R.id.rotate:
                mContext.startActivity(new Intent(mContext, RetrofitActivity.class));
                break;
            case R.id.camera:
                ToastUtils.shortMsg("相机已收敛至ProjectMain");
                break;
            case R.id.review:
                Intent intent5 = new Intent(mContext, ReviewActivity.class);
                mContext.startActivity(intent5);
                break;
            case R.id.animation:
                Intent intent7 = new Intent(mContext, AnimMainActivity.class);
                mContext.startActivity(intent7);
                break;
            case R.id.fragments:
                DemoActivity.start(mContext);
                break;
            case R.id.custom:
                Intent intent8 = new Intent(mContext, CtmMainActivity.class);
                mContext.startActivity(intent8);
                break;
            case R.id.project:

                Intent intent9 = new Intent(mContext, ProjectMainActivityRx.class);
                ((Activity) mContext).startActivityForResult(intent9, 11);
                break;

        }
    }

    private void authority() {
        //申请权限
        String[] permissions = new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        PermissionsUtil.showSystemSetting = false;//是否支持显示系统设置权限设置窗口跳转
        PermissionsUtil.IPermissionsResult permissionsResult =
                new PermissionsUtil.IPermissionsResult() {
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
