package com.dawn.zgstep.design_patterns.behavioral.iterator;

/**
 * 抽象迭代器，可全局使用
 */
public interface RIterator {
    boolean hasNext();
    Object next();
    boolean isLast();
}
