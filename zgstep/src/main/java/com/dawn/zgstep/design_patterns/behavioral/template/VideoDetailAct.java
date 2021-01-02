package com.dawn.zgstep.design_patterns.behavioral.template;

/**
 * Create by rye
 * at 2021/1/2
 *
 * @description:
 */
public abstract class VideoDetailAct {
    abstract void buildParams();

    public void play() {
        buildParams();
        System.out.println("参数构造完毕...goPlay");
    }
}
