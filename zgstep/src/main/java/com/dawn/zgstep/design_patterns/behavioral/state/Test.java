package com.dawn.zgstep.design_patterns.behavioral.state;

/**
 * Create by rye
 * at 2021/1/2
 *
 * @description:
 */
public  class Test {
    public static void main(String[] args) {
      ILoginController loginController = new LoginController();
      loginController.login();
      loginController.dealLoginBtn();
      loginController.dealVipStatus();
    }
}
