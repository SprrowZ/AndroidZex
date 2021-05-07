package com.rye.catcher.agocode.sample;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by rye
 * at 2021/2/26
 *
 * @description:
 */
public class RouterMapping {

    public static Map<String, String> get() {
        Map<String, String> map = new HashMap();
        map.putAll(RouterMapping_1.get());
        map.putAll(RouterMapping_2.get());
        return map;
    }

}
