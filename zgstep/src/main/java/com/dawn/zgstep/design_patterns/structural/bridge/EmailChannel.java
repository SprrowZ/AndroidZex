package com.dawn.zgstep.design_patterns.structural.bridge;

/**
 * Create by rye
 * at 2020-09-10
 *
 * @description:
 */
public class EmailChannel implements SendAction {
    @Override
    public void send(String message, String toUser) {
        System.out.println("邮件发送消息:"+message+" 给"+toUser);
    }
}
