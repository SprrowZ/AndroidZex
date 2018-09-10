package com.example.myappsecond.project.javamore;

/**
 * Created by ZZG on 2018/3/14.
 */

public class InterfaceTest  {
    private static final String TAG = "InterfaceTest";
    public static void  doAct(String url,CmdCallback cmdCallback){
        if (url.isEmpty()){
            cmdCallback.onError();
        }else{
            cmdCallback.onSuccess();
        }

    }

}
