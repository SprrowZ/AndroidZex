package com.dawn.zgstep.design_patterns.behavioral.iterator;

/**
 * 获取具体的迭代器，数据仓库需要实现
 */
public interface RContainer {
    RIterator getIterator();
    //除了获取迭代器外，此接口也可以实现其他操作数据类的方法
    void addCourse(Course course);
    void deleteCourse(Course course);
}
