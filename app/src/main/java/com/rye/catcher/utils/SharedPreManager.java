package com.rye.catcher.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.rye.catcher.RyeCatcherApp;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created at 2019/2/27.
 *
 * @author Zzg
 * @function: 使用SP存储配置信息，90K空间
 */
public class SharedPreManager {
   private static final String SP_NAME="Z-RYE-CATCHER";
    //内存缓存
   private static ConcurrentHashMap concurrentHashMap=new ConcurrentHashMap();

    public static String getValue(String key){
        if (concurrentHashMap.contains(key)){
            return (String) concurrentHashMap.get(key);
        }
        SharedPreferences sp= RyeCatcherApp.getInstance()
                .getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(key,"");
    }
    public static void saveValue(String key,Object value){
        if (key==null){
            throw new IllegalStateException("key can not be null!");
        }
        SharedPreferences sp= RyeCatcherApp.getInstance()
                .getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(key,String.valueOf(value));
        editor.commit();
        saveMap(key,value);
    }
    private static  void saveMap(String key ,Object value){
        //保存到内存中
        if (concurrentHashMap.size()>200){//内存过大清空，可能导致问题，待后续完善
            concurrentHashMap.clear();
        }
        concurrentHashMap.put(key,String.valueOf(value));

    }
}
