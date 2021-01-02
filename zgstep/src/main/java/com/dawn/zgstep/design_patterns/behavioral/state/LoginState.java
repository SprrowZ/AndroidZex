package com.dawn.zgstep.design_patterns.behavioral.state;

/**
 * Create by rye
 * at 2021/1/2
 *
 * @description:
 */
public class LoginState implements ILoginState {
    @Override
    public void dealPersonalInfo() {
        println("showPersonalInfo");
    }

    @Override
    public void dealVipStatus() {
        println("dealVipStatus---hasManyStatus");
    }

    @Override
    public void dealLoginBtn() {
        println("hideLoginBtn");
    }
}
