package com.dawn.zgstep.reflect;

import java.lang.reflect.Method;

/**
 * Create by rye
 * at 2020-07-25
 *
 * @description:
 */
public class MethodUtils {
    private MethodUtils() {
    }

    /**
     * @param obj        被调用对象
     * @param methodName 方法名
     * @param args       方法参数
     * @return
     */
    public static Object invokeMethod(Object obj, String methodName, Object... args) {
        Object result = null;
        Class<?> clazz = obj.getClass();

        try {
            if (args == null || args.length == 0) {
                Method method = clazz.getDeclaredMethod(methodName);
                result = method.invoke(obj);
            } else {
                Class<?>[] argsClass = new Class[args.length];
                for (int i = 0; i < args.length; i++) {
                    argsClass[i] = args[i].getClass();
                }
                Method method = clazz.getDeclaredMethod(methodName, argsClass);
                result = method.invoke(obj, args);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
