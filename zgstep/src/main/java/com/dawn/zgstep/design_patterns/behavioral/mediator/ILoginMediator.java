package com.dawn.zgstep.design_patterns.behavioral.mediator;

/**
 * Create by rye
 * at 2021/1/16
 *
 * @description: 登录中介者
 */
public interface ILoginMediator {
    //供各Colleague通知Mediator，从而让Mediator去协调处理
    void login(LoginType type);

    void createColleagues();

    void switchLoginType(LoginType loginType);

}
