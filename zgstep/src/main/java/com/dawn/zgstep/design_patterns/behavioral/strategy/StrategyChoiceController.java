package com.dawn.zgstep.design_patterns.behavioral.strategy;

/**
 * Create by rye
 * at 2021/1/2
 *
 * @description:
 */
public class StrategyChoiceController implements IStrategyChoiceController {
    private ICardConfigStrategy mCurrentStrategy;

    @Override
    public void setStrategy(ICardConfigStrategy strategy) {
        this.mCurrentStrategy = strategy;
    }

    @Override
    public void goPlay() {
        mCurrentStrategy.goPlay();
    }

    @Override
    public void jumpDetailPage() {
        mCurrentStrategy.jumpDetailPage();
    }
}
