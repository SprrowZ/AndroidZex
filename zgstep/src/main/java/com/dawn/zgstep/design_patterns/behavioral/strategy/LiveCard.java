package com.dawn.zgstep.design_patterns.behavioral.strategy;

/**
 * Create by rye
 * at 2021/1/2
 *
 * @description:
 */
public class LiveCard  implements  ICardConfigStrategy{
    @Override
    public void goPlay() {
        println(this.getClass(),"goPlay..");
    }

    @Override
    public void jumpDetailPage() {
        println(this.getClass(),"jumpDetailPage..");
    }
}
