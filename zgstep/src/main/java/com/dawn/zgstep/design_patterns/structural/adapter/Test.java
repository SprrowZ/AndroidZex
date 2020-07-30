package com.dawn.zgstep.design_patterns.structural.adapter;

public class Test {
    public static void main(String[] args) {
        //类适配模式
        HorizontalThumbPlay thumbPlay = new HorizontalThumbScreen();
        thumbPlay.thumbPlay();
       //对象适配模式
        HorizontalThumbPlay thumbPlayEx = new HorizontalThumbScreenEx(
                new HorizontalFullScreen());
        thumbPlayEx.thumbPlay();
    }
}
