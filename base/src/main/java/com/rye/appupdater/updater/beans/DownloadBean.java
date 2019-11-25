package com.rye.appupdater.updater.beans;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created By RyeCatcher
 * at 2019/9/19
 */
public class DownloadBean implements Serializable {//P better
    public String title;
    public String content;
    public String url;
    public String md5;
    public String versionCode;

    public static DownloadBean parse(String result) {
        try {
            JSONObject jsonObject=new JSONObject(result);
            String title=jsonObject.optString("title");
            String content=jsonObject.optString("content");
            String url=jsonObject.optString("url");
            String md5=jsonObject.optString("md5");
            String versionCode=jsonObject.optString("versionCode");
            DownloadBean bean=new DownloadBean();
            bean.title=title;
            bean.content=content;
            bean.url=url;
            bean.md5=md5;
            bean.versionCode=versionCode;
            return bean;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }
}
