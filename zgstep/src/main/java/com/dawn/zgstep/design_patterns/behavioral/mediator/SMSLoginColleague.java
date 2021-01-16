package com.dawn.zgstep.design_patterns.behavioral.mediator;

/**
 * Create by rye
 * at 2021/1/16
 *
 * @description:
 */
public class SMSLoginColleague implements ILoginColleague {
    private ILoginMediator mMediator;

    public SMSLoginColleague(ILoginMediator mediator) {
        if (mediator == null) {
            throw new IllegalArgumentException("mediator can not be null!");
        }
        this.mMediator = mediator;
    }




    @Override
    public void showLoginPanel() {
        System.out.println("----切换成短信登录面板");
    }

    @Override
    public void login() {
        System.out.println("====短信登录");
        mMediator.login(LoginType.SMS_LOGIN);
    }
}
