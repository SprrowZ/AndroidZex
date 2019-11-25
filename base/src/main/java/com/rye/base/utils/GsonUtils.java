package com.rye.base.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created By RyeCatcher
 * at 2019/9/4
 */
public class GsonUtils {
    // TODO: 2019/9/10  jsonBuilder 可以使用一个
    private GsonUtils() {
    }

    /**
     * 转成json
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(object);
    }

    /**
     * Json转字符串，直接序列化Object
     *
     * @param object
     * @param type
     * @return
     */
    public static String toJson(Object object, Type type) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(object, type);
    }

    /**
     * 转成bean
     *
     * @param gsonString
     * @param cls
     * @return 实体类
     */
    public static <T> T toBean(String gsonString, Class<T> cls) {
        T t = null;
        Gson gson = new GsonBuilder().serializeNulls().create();
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }

    /**
     * Reader 对象读取JSON 转成List
     * @param
     * @param cls
     * @param <T>
     * @return List<T>
     */
    public static <T> List<T> toList(String jsonStr, Class<T[]> cls) {
        Gson gson = new Gson();
        T[] array = gson.fromJson(jsonStr, cls);
        return Arrays.asList(array);
    }

    public static <T> List<T> toList(Reader reader, Class<T[]> cls) {
        Gson gson = new Gson();
        T[] array = gson.fromJson(reader, cls);
        return Arrays.asList(array);
    }

    /**
     * 带*头结点*的json List
     * @param message
     * @param jsonHead
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> toList(String message, String jsonHead,Class<T> cls){ //这里是Class<T>
        JsonObject jsonObject = new JsonParser().parse(message).getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray(jsonHead);

        Gson gson = new GsonBuilder().create();
        List<T> list = new ArrayList<>();
        for (JsonElement jsonElement : jsonArray) {
            list.add(gson.fromJson(jsonElement,cls)); //cls
        }
        return list;
    }



    /**
     * 转成list中有map的
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> toMapList(String gsonString) {
        List<Map<String, T>> list = null;
        Gson gson = new GsonBuilder().serializeNulls().create();
        list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        return list;
    }

    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> toMaps(String gsonString) {
        Map<String, T> map = null;
        Gson gson = new GsonBuilder().serializeNulls().create();
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }

}
