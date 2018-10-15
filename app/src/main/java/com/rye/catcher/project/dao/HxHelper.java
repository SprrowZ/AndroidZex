package com.rye.catcher.project.dao;

/**
 * Created by chenguozhi on 2017/7/8.
 */

public class HxHelper {
    private HXSDKModel model;

    public HXSDKModel getModel() {
        return model;
    }

    public void setModel(HXSDKModel model) {
        this.model = model;
    }

    private static HxHelper hxHelper = new HxHelper();

    public static HxHelper getInstance() {
        return hxHelper;
    }
}
