package com.dawn.zgstep.javas.java8;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

import java.util.List;

public class StreamInterface {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void testStreamOne(){
        List<String>  dataList=new ArrayList<>();
        dataList.add("111");
        dataList.add("222");
        dataList.add("333");
        dataList.add("kugou");

       String result= dataList.stream().max(StreamInterface::test).get();
       System.out.println("result:"+result);

    }

    private static int test(String s, String s1) {
        if (s.matches("[0-9]")){

        }
        return 0;
    }




}
