package com.rye.catcher;
import com.dawn.zgstep.pluggable.LoadUtil;
import com.rye.base.BaseApplication;


import com.rye.catcher.base.helpers.ApplicationHelper;
import com.rye.router.runtime.Router;


/**
 * Created by ZZG on 2018/3/22.
 */

public class RyeCatcherApp extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }


    private void init() {
        ApplicationHelper.registerLifeCycle();
        ThirdSdk.getInstance().initSdk(this);
        //插件，加载plugin dex
        LoadUtil.loadClass(this);
        //加载自定义路由框架
        Router.init();
    }


}
