package com.dawn.zgstep.design_patterns.creational.factorymethod;

/**
 * Create by rye
 * at 2020-07-02
 *
 * @description:
 */
public class Test {
    public static void main(String[] args) {
//       VideoFactory videoFactory = new PythonFactory();
//       Video video = videoFactory.getVideo();
//       video.produce();
         //---应用层需要更换实现的时候，只需要替换工厂即可，不过
        //同一产品等级的产品，都需要各自的工厂类，但这也是工厂方法实现的必经之路
        VideoFactory videoFactory = new JavaFactory();
        Video video = videoFactory.getVideo();
        video.produce();
    }
}
