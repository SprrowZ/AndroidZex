package com.dawn.zgstep.design_patterns.structural.adapter;
//Adaptee 源角色
public class HorizontalFullScreen {
    public String resolvePlayerParams(){
        //...解析参数
        return "params";
    }
    public void fullScreenPlay(){
        System.out.println("--全屏横屏播放");
    }
}
