package com.rye.catcher.activity.splash;

import com.rye.base.BaseActivity;
import com.rye.catcher.R;
import com.rye.catcher.activity.MainActivity;
import com.rye.catcher.utils.PermissionsUtil;
import com.rye.catcher.utils.permission.PermissionUtils;
import com.rye.catcher.utils.permission.Permissions;
import com.yanzhenjie.permission.Permission;


import java.util.concurrent.TimeUnit;


import io.reactivex.Observable;


/**
 * Created by ZZG on 2018/9/7.
 */
public class SplashActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.app_splash;
    }

    @Override
    public void initEvent() {
        //安装申请权限
        authority();
    }

    private void authority() {
        PermissionsUtil.INSTANCE.checkPermissions(this, new PermissionsUtil.IPermissionsResult() {
            @Override
            public void passPermissons() {
                MainActivity.start(SplashActivity.this);
            }

            @Override
            public void forbitPermissons() {}
        }, Permission.WRITE_EXTERNAL_STORAGE,Permission.ACCESS_COARSE_LOCATION);
    }



}
