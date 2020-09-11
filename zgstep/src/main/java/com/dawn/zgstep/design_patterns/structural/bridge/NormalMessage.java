package com.dawn.zgstep.design_patterns.structural.bridge;

/**
 * Create by rye
 * at 2020-09-10
 *
 * @description: 消息类型具体实现
 */
public class NormalMessage extends AbstractMessage {
    public NormalMessage(SendAction action) {
        super(action);
    }
}
