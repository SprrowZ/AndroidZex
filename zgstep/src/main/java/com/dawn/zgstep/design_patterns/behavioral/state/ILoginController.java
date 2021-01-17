package com.dawn.zgstep.design_patterns.behavioral.state;

/**
 * Create by rye
 * at 2021/1/2
 *
 * @description:
 */
public interface ILoginController extends ILoginState {
    void login();

    void logout();
}
