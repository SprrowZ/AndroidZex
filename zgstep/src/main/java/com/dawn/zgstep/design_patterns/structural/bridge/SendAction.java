package com.dawn.zgstep.design_patterns.structural.bridge;

/**
 * Create by rye
 * at 2020-09-10
 *
 * @description: 维度一 ： 发送方式
 */
public interface SendAction {
    void send(String message,String toUser);
}
