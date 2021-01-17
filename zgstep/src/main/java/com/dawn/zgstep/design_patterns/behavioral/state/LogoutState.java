package com.dawn.zgstep.design_patterns.behavioral.state;

/**
 * Create by rye
 * at 2021/1/2
 *
 * @description:
 */
public class LogoutState implements ILoginState {
    @Override
    public void dealPersonalInfo() {
        println("hidePersonInfo");
    }

    @Override
    public void dealVipStatus() {
        println("dealVipStatus---onlyOneStatus");
    }

    @Override
    public void dealLoginBtn() {
        println("showLoginBtn");
    }
}
