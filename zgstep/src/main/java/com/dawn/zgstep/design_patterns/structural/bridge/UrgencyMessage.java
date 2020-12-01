package com.dawn.zgstep.design_patterns.structural.bridge;

/**
 * Create by rye
 * at 2020-09-10
 *
 * @description: 紧急消息
 */
public class UrgencyMessage extends AbstractMessage {
    public UrgencyMessage(SendAction action) {
        super(action);
    }

    @Override
    public void sendMessage(String message, String toUser) {
        super.sendMessage(message, toUser);
    }
    //扩展功能
    public void notifyUser(){

    }

}
