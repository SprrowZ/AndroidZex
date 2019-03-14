package com.rye.catcher.activity;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.LinearLayout;

import com.rye.catcher.BaseActivity;
import com.rye.catcher.R;

import com.rye.catcher.activity.fragment.CameraFragment;
import com.rye.catcher.utils.PermissionsUtil;
import com.rye.catcher.utils.ToastUtils;
import com.rye.catcher.utils.ubean.Permissions;

import butterknife.BindView;

/**
 * Created by ZZG on 2017/10/18.
 */

public class CameraActivity extends BaseActivity {
    @BindView(R.id.linear1)
    LinearLayout linear1;
    @BindView(R.id.linear2)
    LinearLayout linear2;
    private Fragment cameraFragment;
    //静态变量内存泄露
    private static CameraActivity activity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity);
        FragmentManager fg = getSupportFragmentManager();
        FragmentTransaction transaction = fg.beginTransaction();
        cameraFragment = new CameraFragment();
        transaction.add(R.id.linear1, cameraFragment);
        transaction.commit();
        //6.0权限适配
        authority();
        memoryLeak();
    }

    /**
     * 内存泄露检测
     */
    private void memoryLeak() {
        //静态变量导致camearActivity不能被回收
        activity=this;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },100000);
    }

    private void authority() {
        //这里的this不是上下文，是Activity对象！
        PermissionsUtil.INSTANCE.checkPermissions(this,new PermissionsUtil.IPermissionsResult() {
            @Override
            public void passPermissons() {
                ToastUtils.shortMsg("权限申请成功");
            }

            @Override
            public void forbitPermissons() {
                ToastUtils.shortMsg("权限申请失败");
            }
        }, Permissions.CAMERA);
    }
}