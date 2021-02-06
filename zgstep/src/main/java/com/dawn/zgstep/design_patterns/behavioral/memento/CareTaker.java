package com.dawn.zgstep.design_patterns.behavioral.memento;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by rye
 * at 2021/1/18
 *
 * @description: 只负责存储备忘录
 */
class CareTaker {
    private static final int MAX_MEMENTO_SIZE = 30;
    private final List<Memento> mMementoList = new ArrayList<>(MAX_MEMENTO_SIZE);
    private int mCurrentIndex = 0;

    public Memento getNextMemento() {
        return (mCurrentIndex < MAX_MEMENTO_SIZE - 1) ? mMementoList.get(++mCurrentIndex) :
                mMementoList.get(mCurrentIndex);
    }

    public Memento getPreMemento() {
        return (mCurrentIndex > 0) ? mMementoList.get(--mCurrentIndex) :
                mMementoList.get(mCurrentIndex);
    }
}
