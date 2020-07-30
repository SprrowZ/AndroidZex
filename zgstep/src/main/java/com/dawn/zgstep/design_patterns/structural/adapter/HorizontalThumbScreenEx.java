package com.dawn.zgstep.design_patterns.structural.adapter;

/**
 * 对象适配模式
 */
public class HorizontalThumbScreenEx implements  HorizontalThumbPlay {
    private HorizontalFullScreen fullScreen;
    public HorizontalThumbScreenEx(HorizontalFullScreen fullScreen){
        this.fullScreen = fullScreen;
    }

    @Override
    public void thumbPlay() {
        String param = fullScreen.resolvePlayerParams();
        System.out.println("组合方式： 解析参数："+param+",半屏播放");
    }
}
