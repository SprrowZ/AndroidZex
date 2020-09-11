package com.dawn.zgstep.design_patterns.structural.bridge.tests;

import com.dawn.zgstep.design_patterns.structural.bridge.SendAction;

/**
 * Create by rye
 * at 2020-09-10
 *
 * @description:
 */
public class XXMessage implements Message {
    private String message;
    private String user;
    public XXMessage(String message,String user){
        this.message = message;
        this.user = user;
    }
    @Override
    public void sendMessage(SendAction sendAction) {
        sendAction.send(message,user);
    }
}
