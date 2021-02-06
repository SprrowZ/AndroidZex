package com.rye.router_api.api;


import android.app.Activity;

import java.util.Map;

/**
 * Create by rye
 * at 2021/1/23
 *
 * @description: 路由接口，所有模块的路由类都需要实现此接口
 */
public interface IRouterLoad {
    void loadInfo(Map<String, Class<? extends Activity>> routers);
}
