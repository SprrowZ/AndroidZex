package com.dawn.zgstep.design_patterns.behavioral.state;

/**
 * Create by rye
 * at 2021/1/2
 *
 * @description:
 */
public interface ILoginState {
    void dealPersonalInfo();

    void dealVipStatus();

    void dealLoginBtn();

    default void println(String msg) {
        System.out.println(msg);
    }
}
