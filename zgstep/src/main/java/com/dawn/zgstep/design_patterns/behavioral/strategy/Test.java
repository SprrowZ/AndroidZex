package com.dawn.zgstep.design_patterns.behavioral.strategy;

/**
 * Create by rye
 * at 2021/1/2
 *
 * @description:
 */
public class Test {
    public ICardConfigStrategy mCurrentStrategy;

    public static void main(String[] args) {
        IStrategyChoiceController strategy = new StrategyChoiceController();
        strategy.setStrategy(new UgcCard());
        strategy.goPlay();
    }


    public void normalStrategy(String type) {
        if (type == "UGC") {
            //UGC 初始化，执行各种操作
        } else if (type == "PGC") {
            // 初始化，执行各种操作
        } else if (type == "LIVE") {
            // 初始化，执行各种操作
        }
    }

}
