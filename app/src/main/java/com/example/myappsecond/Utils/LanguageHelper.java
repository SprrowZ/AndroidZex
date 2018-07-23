package com.example.myappsecond.Utils;

import com.example.myappsecond.EChatApp;

/**
 * Created by changshuchao on 2017/6/13.
 */
public class LanguageHelper {

    private static LanguageHelper instance = null;

    public static LanguageHelper getInstance() {
        if (instance == null)
            instance = new LanguageHelper();
        return instance;
    }

    public boolean isChinese(){
        if (EChatApp.getInstance().getResources().getConfiguration().locale.getCountry().equals("CN")
                ||EChatApp.getInstance().getResources().getConfiguration().locale.getCountry().equals("TW")
                ||EChatApp.getInstance().getResources().getConfiguration().locale.getCountry().equals("HK")
                ||EChatApp.getInstance().getResources().getConfiguration().locale.getCountry().equals("MO")) {
            return true;
        }else {
            return false;
        }
    }

    public boolean isEnglish(){
        if(isChinese()){
            return false;
        }else{
            return true;
        }
    }
}
