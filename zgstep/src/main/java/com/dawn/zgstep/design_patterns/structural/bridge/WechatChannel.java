package com.dawn.zgstep.design_patterns.structural.bridge;

/**
 * Create by rye
 * at 2020-09-10
 *
 * @description: 微信渠道
 */
public class WechatChannel implements SendAction {

    @Override
    public void send(String message, String toUser) {
        System.out.println("微信发送消息:"+message+" 给"+toUser);
    }
}
