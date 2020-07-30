package com.dawn.zgstep.design_patterns.structural.adapter;

/**
 * 类适配模式
 */
public class HorizontalThumbScreen extends  HorizontalFullScreen implements  HorizontalThumbPlay{
    @Override
    public void thumbPlay() {
        String params = resolvePlayerParams();
        System.out.println("继承方式： 解析参数："+params+",半屏播放");
    }
}
