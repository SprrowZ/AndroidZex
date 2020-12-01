package com.dawn.zgstep.design_patterns.behavioral.chainofresponsibility;

import com.dawn.zgstep.design_patterns.behavioral.chainofresponsibility.other.CostInformation;

/**
 * Create by rye
 * at 2020-08-01
 *
 * @description: 抽象处理器
 */
public interface Filter {
    boolean process(CostInformation costInformation);
}
