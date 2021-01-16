package com.dawn.zgstep.pluggable;

import android.content.Context;
import android.util.Log;

import dalvik.system.DexClassLoader;

/**
 * Create by rye
 * at 2021/1/12
 *
 * @description:
 */
public class PlugTest {
    private static final String PLUGIN_DEMO_CLASS = "com.dream.pluginz.demos.PluginZDemo";
    private static final String COMPONENT_DEMO_CLASS = "com.rye.catcher.ThirdSdk";

    public static void main(String[] args) {
        // demo2();
         demo3();
    }

    /**
     * 自己加载一个Class文件
     */
    private static void demo1(String dexPath, Context context) throws ClassNotFoundException {
        DexClassLoader dexClassLoader = new DexClassLoader(dexPath, context.getCacheDir().getAbsolutePath(),
                null, context.getClassLoader()); /* parent可以传自己想传的，可以PathClassLoader，
                也可以BootClassLoader*/
        Class<?> clazz = dexClassLoader.loadClass("com.hello.kugou");
    }

    /**
     * 通过反射加载插件中的某个类: 反射是拿不到的，因为不在同一个dex文件中
     */
    private static void demo2() {
        try {
            Class<?> pluginClazz = Class.forName(PLUGIN_DEMO_CLASS);
            Log.i("Rye", "获取到插件中的类:" + pluginClazz.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过反射获取组件中的某个类：因为在同一个dex文件中，所以是可以拿到的
     */
    private static void demo3() {
        try {
            Class<?> componentClass = Class.forName(COMPONENT_DEMO_CLASS);
            Log.i("Rye", "获取到组件中的类:" + componentClass.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
