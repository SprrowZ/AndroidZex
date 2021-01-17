package com.dawn.zgstep.design_patterns.behavioral.strategy;

/**
 * Create by rye
 * at 2021/1/2
 *
 * @description:
 */
public interface IStrategyChoiceController extends ICardConfigStrategy {
    void setStrategy(ICardConfigStrategy strategy);
}
