package com.rye.catcher.activity.splash;

import com.rye.base.BaseActivity;
import com.rye.catcher.R;
import com.rye.catcher.activity.MainActivity;
import com.rye.catcher.utils.permission.PermissionUtils;
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
        PermissionUtils.requestPermission(this, getString(R.string.need_authority),
                false, data -> {
                    openMain();
                }, 0,
                Permission.WRITE_EXTERNAL_STORAGE, Permission.ACCESS_COARSE_LOCATION);//CALL_PHONE先去掉
    }

    private void openMain() {
        Observable.timer(1000, TimeUnit.MILLISECONDS).subscribe(aLong ->
                MainActivity.start(SplashActivity.this));
    }

}
