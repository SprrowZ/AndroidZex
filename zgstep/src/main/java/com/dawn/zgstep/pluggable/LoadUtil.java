package com.dawn.zgstep.pluggable;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import dalvik.system.DexClassLoader;

/**
 * Create by rye
 * at 2021/1/14
 *
 * @description: 加载插件的dexElements到宿主的dexElements中去
 */
public class LoadUtil {
    //插件的apk文件位置
    private static final String apkPath = "/sdcard/pluginz-debug.apk";

    public static void loadClass(Context context) {

        try {
            Class<?> dexPathListClass = Class.forName("dalvik.system.DexPathList");
            Field dexElementsField = dexPathListClass.getDeclaredField("dexElements");
            dexElementsField.setAccessible(true);

            Class<?> classLoaderClass = Class.forName("dalvik.system.BaseDexClassLoader");
            Field pathListField = classLoaderClass.getDeclaredField("pathList");
            pathListField.setAccessible(true);

            //1.获取宿主的类加载器
            ClassLoader pathClassLoader = context.getClassLoader();
            //--获取宿主的pathList
            Object hostPathList = pathListField.get(pathClassLoader);
            //--目的达到：获取宿主的dexElements`
            Object[] hostDexElements = (Object[]) dexElementsField.get(hostPathList);

            //2.获取插件的类加载器
            //7.0之后最后一个参数可为null,对比一下6.0和7.0的loadClass！！！
            DexClassLoader pluginClassLoader = new DexClassLoader(apkPath, context.getCacheDir().getAbsolutePath(),
                    null, pathClassLoader);
            Object pluginPathList = pathListField.get(pluginClassLoader);
            Object[] pluginDexElements = (Object[]) dexElementsField.get(pluginPathList);

            //合并 new Elements[];
            Object[] newElements = (Object[]) Array.newInstance(hostDexElements.getClass().getComponentType(),
                    hostDexElements.length + pluginDexElements.length);
            System.arraycopy(hostDexElements,0,newElements,0,hostDexElements.length);
            System.arraycopy(pluginDexElements,0,newElements,hostDexElements.length,pluginDexElements.length);
            //赋值到宿主的dexElements中去
            dexElementsField.set(hostPathList,newElements);
            Log.e("Rye","合并dex完成！");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
