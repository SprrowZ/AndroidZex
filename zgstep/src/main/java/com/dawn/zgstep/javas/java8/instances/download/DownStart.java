package com.dawn.zgstep.javas.java8.instances.download;


import java.util.Arrays;

public class DownStart {


    public static void main(String[] args){
       ZDPresenter.download("WWW.BAIDU.COM", pro -> {
             System.out.println("当前进度："+pro);
       });

        Arrays.asList("ss","..");
    }
}
