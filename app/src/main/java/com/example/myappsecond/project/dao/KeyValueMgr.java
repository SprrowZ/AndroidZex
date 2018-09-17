package com.example.myappsecond.project.dao;

import android.content.Context;


import com.example.myappsecond.utils.EchatAppUtil;
import com.example.myappsecond.utils.ExtraUtil.Bean;
import com.example.myappsecond.utils.StringUtils;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jinyunyang on 15/3/13.
 */
public class KeyValueMgr {


    /***
     * 版本号信息
     * */
    public static String VERSION_DETIL="VERSION_DETIL";




    //内存缓存；
    public static ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
    /** 常用联系人**/

    private static Context getContext() {
        return EchatAppUtil.getAppContext();
    }
    /**
     * 获取登录人信息
     */
    public static final String LOGIN_INFO_TIME = "LOGIN_INFO_TIME";


    /**
     *
     * @param key 参数的主键
     * @return 主键值
     */
    public static String getValue(String key) {
        if (concurrentHashMap.containsKey(key)){
            Object value =  concurrentHashMap.get(key);
            return String.valueOf(value);
        }
        KeyValueDao keyValueDao = new KeyValueDao(getContext());
        String value = keyValueDao.getValue(key);
        //更新内存；
        saveMap(key,value);
        return value;
    }
    public static void init(){
        KeyValueDao keyValueDao = new KeyValueDao(getContext());
        List<Bean> list = keyValueDao.finds(null);
        for (Bean bean:list){
            saveMap(bean.getStr("ITEM_KEY"),bean.getStr("ITEM_VALUE"));
        }
    }
    /**
     *
     * @param key 数据主键
     * @param value 数据值
     */
    public static void saveValue(String key, String value) {
        saveMap(key,value);

        KeyValueDao keyValueDao = new KeyValueDao(getContext());
        keyValueDao.save(key, value);
    }
    /**
     * @param key 数据主键
     * @param value 数据值
     *  更新内存
     * */
    public static void saveMap(Object key, Object value){
        if (concurrentHashMap.size()>200){
            concurrentHashMap.clear();
        }
        concurrentHashMap.put(key,value);
    }
    public static void clear(){
        concurrentHashMap.clear();
    }
    /**
     *
     * @param key 数据主键
     * @param value 数据值
     */
    public static void saveValue(String key, long value) {
        saveMap(key,value);

        KeyValueDao keyValueDao = new KeyValueDao(getContext());
        keyValueDao.save(key, String.valueOf(value));
    }

    /**
     *
     * @param key 数据主键
     * @param defaultVal 默认值，如果数据库中没有指定主键的数据，则返回默认值。
     */
    public static String getValue(String key, String defaultVal) {
        String val = getValue(key);
        if(StringUtils.isNotEmpty(val)) {
            return val;
        }
        return defaultVal;
    }

    /**
     *
     * @param key 数据主键
     * @param defaultVal 默认值，如果数据库中没有指定主键的数据，则返回默认值。
     * @return 指定数据
     */
    public static long getValue(String key, long defaultVal) {
        String val = getValue(key);
        if(StringUtils.isEmpty(val)) {
            return defaultVal;
        }
        try {
            return Long.valueOf(val);
        } catch (Exception e) {
            return defaultVal;
        }
    }

    /**
     * 删除表中key中包含text的数据
     * @param text
     */
    public static void delete(String text) {
        if (concurrentHashMap.containsKey(text)){
            concurrentHashMap.remove(text);
        }
        KeyValueDao keyValueDao = new KeyValueDao(getContext());
        SqlBean sqlBean = new SqlBean()
                .andLike("ITEM_KEY",text);
        keyValueDao.delete(sqlBean);
    }
}
