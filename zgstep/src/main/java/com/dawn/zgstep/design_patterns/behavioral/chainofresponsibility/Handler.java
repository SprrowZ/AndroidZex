package com.dawn.zgstep.design_patterns.behavioral.chainofresponsibility;

/**
 * Create by rye
 * at 2020-08-01
 *
 * @description: 抽象处理器
 */
public interface Handler {
    boolean process(CostInformation costInformation);
}
