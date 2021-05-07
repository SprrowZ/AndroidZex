package com.rye.catcher.agocode.sample;

import java.util.HashMap;
import java.util.Map;

public class RouterMapping_1 {
  public static Map<String, String> get() {
    Map<String, String> mapping = new HashMap();
    mapping.put("router://page-project","com.rye.catcher.activity.ProjectMainActivityRx");
    return mapping;
  }
}
