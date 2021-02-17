package com.dawn.zgstep.mapping;

import java.util.HashMap;
import java.util.Map;

public class RouterMapping_123 {
    public static Map<String,String> get(){
        Map<String,String> mapping = new HashMap<>();
        mapping.put("route://xxx","com.xxx.xxx.AActivity");
        mapping.put("route://xxx","com.xxx.xxx.BActivity");
        mapping.put("route://xxx","com.xxx.xxx.CActivity");
        mapping.put("route://xxx","com.xxx.xxx.DActivity");
        return mapping;
    }
}
