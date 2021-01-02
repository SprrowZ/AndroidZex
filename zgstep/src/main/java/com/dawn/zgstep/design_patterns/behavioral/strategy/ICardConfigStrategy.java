package com.dawn.zgstep.design_patterns.behavioral.strategy;

/**
 * Create by rye
 * at 2021/1/2
 *
 * @description:
 */
public interface ICardConfigStrategy {
    void goPlay();

    void jumpDetailPage();

    default void println(Class<? extends ICardConfigStrategy> clazz, String msg) {
        System.out.println(clazz.getSimpleName() + ":" + msg);
    }
}
