package com.dawn.zgstep.design_patterns.behavioral.mediator;

/**
 * Create by rye
 * at 2021/1/16
 *
 * @description: 负责管理登录页面的UI
 */
public class FakeLoginMediator implements ILoginMediator {
    private ILoginColleague mSmsLogin;
    private ILoginColleague mPwLogin;
    //记录当前登录模式
    private LoginType mCurrentLoginType = LoginType.SMS_LOGIN;

    private FakeLoginMediator() {
        createColleagues();
    }

    public static FakeLoginMediator create() {
        return new FakeLoginMediator();
    }


    @Override
    public void login(LoginType type) {
        switch (type) {
            case PW_LOGIN:
                pwLogin();
                break;
            case SMS_LOGIN:
                smsLogin();
                break;
        }
    }


    @Override
    public void createColleagues() {
        mSmsLogin = new SMSLoginColleague(this);
        mPwLogin = new PWLoginColleague(this);
    }

    /**
     * 右侧按钮切换登录方式
     *
     * @param loginType
     */
    @Override
    public void switchLoginType(LoginType loginType) {
        mCurrentLoginType = loginType;
        switch (loginType) {
            case PW_LOGIN:
                mPwLogin.showLoginPanel();
                break;
            case SMS_LOGIN:
                mSmsLogin.showLoginPanel();
                break;
        }
        toggleText();
    }


    private void smsLogin() {
        //完善逻辑
        System.out.println("短信登录");
    }

    private void pwLogin() {
        //完善逻辑
        System.out.println("密码登录");
    }

    private void toggleText() {
        //根据mCurrentLoginType 显示标题和右侧文字
        System.out.println("切换登录页文字");
    }

}
