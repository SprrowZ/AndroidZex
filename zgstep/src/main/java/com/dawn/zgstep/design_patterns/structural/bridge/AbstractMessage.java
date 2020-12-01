package com.dawn.zgstep.design_patterns.structural.bridge;

/**
 * Create by rye
 * at 2020-09-10
 *
 * @description:  维度二 ： 消息类型 ，可用接口
 */
public abstract class AbstractMessage {
    private SendAction mSendAction;

    public AbstractMessage(SendAction action) {
        this.mSendAction = action;
    }
    public void sendMessage(String message,String toUser){
      this.mSendAction.send(message,toUser);
    }
}
