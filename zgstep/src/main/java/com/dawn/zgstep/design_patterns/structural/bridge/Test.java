package com.dawn.zgstep.design_patterns.structural.bridge;

import android.os.Build;

import com.dawn.zgstep.design_patterns.structural.bridge.tests.XXMessage;

/**
 * Create by rye
 * at 2020-09-10
 *
 * @description: 测试维度实现方式不同情况下的现象：抽象类+接口 && 接口+接口
 */
public class Test {
    public static void main(String[] args) {
       testMessage();
    }

    private static void testAbstractWay(){
        NormalMessage message = new NormalMessage(new EmailChannel());
        message.sendMessage("哈喽，WDNMD","李二狗");

        UrgencyMessage urgencyMessage = new UrgencyMessage(new WechatChannel());
        urgencyMessage.sendMessage("唯独你不懂","李二狗");
    }


    private static void testMessage(){
        XXMessage xxMessage = new XXMessage("你在哪啊","李二狗");
        xxMessage.sendMessage(new WechatChannel());
        System.getProperty("os.name");
        System.getProperty("os.version");

    }
}
