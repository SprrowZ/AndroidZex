package com.rye.catcher.agocode.sample;

import java.util.HashMap;
import java.util.Map;

public class RouterMapping_2 {
  public static Map<String, String> get() {
    Map<String, String> mapping = new HashMap();
    mapping.put("router://page-step","com.dawn.zgstep.ui.activity.ZStepMainActivity");
    mapping.put("router://page-demo","com.dawn.zgstep.ui.activity.DemoActivity");
    return mapping;
  }
}
