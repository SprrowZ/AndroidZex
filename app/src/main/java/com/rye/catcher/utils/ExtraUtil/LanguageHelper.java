package com.rye.catcher.utils.ExtraUtil;

import com.rye.catcher.zApplication;

/**
 * Created by zzg on 2017/6/13.
 */
public class LanguageHelper {

    private static LanguageHelper instance = null;

    public static LanguageHelper getInstance() {
        if (instance == null)
            instance = new LanguageHelper();
        return instance;
    }

    public boolean isChinese(){
        if (zApplication.getInstance().getResources().getConfiguration().locale.getCountry().equals("CN")
                || zApplication.getInstance().getResources().getConfiguration().locale.getCountry().equals("TW")
                || zApplication.getInstance().getResources().getConfiguration().locale.getCountry().equals("HK")
                || zApplication.getInstance().getResources().getConfiguration().locale.getCountry().equals("MO")) {
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
