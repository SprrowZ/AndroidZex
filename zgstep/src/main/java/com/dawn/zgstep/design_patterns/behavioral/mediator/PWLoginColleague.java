package com.dawn.zgstep.design_patterns.behavioral.mediator;

/**
 * Create by rye
 * at 2021/1/16
 *
 * @description: 负责管理Panel里的UI
 */
public class PWLoginColleague implements ILoginColleague {
    private ILoginMediator mMediator;

    public PWLoginColleague(ILoginMediator mediator) {
        if (mediator == null) {
            throw new IllegalArgumentException("mediator can not be null!");
        }
        this.mMediator = mediator;
    }

    @Override
    public void showLoginPanel() {
        System.out.println("----切换成密码登录面板");
    }

    @Override
    public void login() {
        System.out.println("====密码登录");
        mMediator.login(LoginType.PW_LOGIN);
    }

    @Override
    public void register() {

    }
}
