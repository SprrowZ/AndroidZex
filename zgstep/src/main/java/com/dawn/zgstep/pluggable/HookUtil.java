package com.dawn.zgstep.pluggable;

import android.app.Activity;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Create by rye
 * at 2021/1/16
 *
 * @description:
 */
public class HookUtil {
    public static void testHookAms(Activity activity) {
        try {
            Class<?> activityClass = Class.forName("android.app.Activity");
            Field tagField = activityClass.getDeclaredField("TAG");
            tagField.setAccessible(true);
            Object activityTag = tagField.get(activity);
            Log.e("Rye", "activityTag:" + activityTag);
        } catch (Exception e) {

        }
    }

    public static void testDynamicActivity(Activity activity) {
        Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                activity.getClass().getInterfaces(), new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.getName() == "onResume") {
                            Log.e("Rye", "startOnResume......");
                        }
                        Object result = method.invoke(activity, args);
                        return result;
                    }
                });
    }

}
