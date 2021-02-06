package com.dawn.zgstep.design_patterns.behavioral.mediator;

/**
 * Create by rye
 * at 2021/1/16
 *
 * @description:
 */
public class Test {
    public static void main(String[] args) {
        FakeLoginMediator loginMediator = FakeLoginMediator.create();
        loginMediator.switchLoginType(LoginType.PW_LOGIN);
    }
}
