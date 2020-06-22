package com.dawn.zgstep.others.demos.reflex;

/**
 * Created by 18041at 2019/6/23
 * Function:
 */

public abstract class Foo<T> {

    Class<T> type;

    public Foo() {
        this.type = (Class<T>) getClass();
    }
}

