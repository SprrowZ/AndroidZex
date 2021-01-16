package com.dawn.zgstep.design_patterns.behavioral.mediator;

/**
 * Create by rye
 * at 2021/1/16
 *
 * @description:
 */
public interface ILoginColleague {


    void showLoginPanel();
    void login();

    default void register(){}
}
