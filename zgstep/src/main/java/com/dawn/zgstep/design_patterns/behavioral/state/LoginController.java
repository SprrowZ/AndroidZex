package com.dawn.zgstep.design_patterns.behavioral.state;

/**
 * Create by rye
 * at 2021/1/2
 *
 * @description: 状态模式
 */
public class LoginController implements ILoginController {
    private ILoginState mState;

    public void setState(ILoginState mState) {
        this.mState = mState;
    }

    @Override
    public void login() {
        setState(new LoginState());
    }

    @Override
    public void logout() {
        setState(new LogoutState());
    }

    @Override
    public void dealPersonalInfo() {
        mState.dealPersonalInfo();
    }

    @Override
    public void dealVipStatus() {
        mState.dealVipStatus();
    }

    @Override
    public void dealLoginBtn() {
        mState.dealLoginBtn();
    }
}
