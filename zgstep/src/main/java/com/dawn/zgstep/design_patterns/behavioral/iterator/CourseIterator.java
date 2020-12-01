package com.dawn.zgstep.design_patterns.behavioral.iterator;

import java.util.List;

/**
 * 具体迭代器
 */
public class CourseIterator implements RIterator {
    private List<Course> dataList;
    private int position;
    private Course course;

    public CourseIterator(List<Course> dataList) {
        this.dataList = dataList;
    }

    @Override
    public boolean hasNext() {
        return position < dataList.size();
    }

    @Override
    public Object next() {
        course = dataList.get(position);
        position++;
        return course;
    }

    @Override
    public boolean isLast() {
        if (position < dataList.size()) {
            return false;
        }
        return true;
    }
}
