package com.rye.router_api.api;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Create by rye
 * at 2021/1/22
 *
 * @description:
 */
public class Router {

    private static volatile Router mInstance;

    public static Router getInstance() {
        if (mInstance == null) {
            synchronized (Router.class) {
                if (mInstance == null) {
                    mInstance = new Router();
                }
            }
        }
        return mInstance;
    }

    private static Map<String, Class<? extends Activity>> routers = new HashMap<>();

    public void register(String path, Class<? extends Activity> cls) {
        routers.put(path, cls);
    }

    public void startActivity(Activity activity, String path) {
        Class<? extends Activity> cls = routers.get(path);
        if (cls != null) {
            Intent intent = new Intent(activity, cls);
            activity.startActivity(intent);
        }
    }


    /**
     * 此策略是在每一个需要router的模块中，新建一个包，包里存放所有需要注册的类，统一注册
     * 然后通过此方案来看，还是有问题：
     * 每个模块都至少需要创建一个路由表，而且必须在路由表中将这个模块下需要注册的Activity
     * 一个一个手动加进去！很麻烦哎有没有！
     * 但是我们可以发现，这些类除了塞进去的数据不同(要注册不同的activity),结构基本都是一样的！
     * 都是实现IRouterLoad接口，包名也一样。
     */
    public static void init(Application application) {
        try {
            Set<String> classNames = ClassUtils.getFileNameByPackageName(application, "com.enjoy.routers");
            for (String className : classNames) {
                Class<?> cls = Class.forName(className);
                if (IRouterLoad.class.isAssignableFrom(cls)) {//新姿势，class级别的instanceOf
                    IRouterLoad routerLoad = (IRouterLoad) cls.newInstance();
                    routerLoad.loadInfo(routers);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
