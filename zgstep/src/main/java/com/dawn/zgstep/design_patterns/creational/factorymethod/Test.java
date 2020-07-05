package com.dawn.zgstep.design_patterns.creational.factorymethod;

/**
 * Create by rye
 * at 2020-07-02
 *
 * @description:
 */
public class Test {
    public static void main(String[] args) {
        Video video = new JavaVideo();
        video.produce();
        //简单工厂
        Video av = VideoFactory.createVideo("PythonVideo");
        av.produce();
        //改善简单工厂
        Video av2 = VideoFactory.getVideo(PythonVideo.class);
        if (av2 ==null) return;
        av2.produce();
    }
}
