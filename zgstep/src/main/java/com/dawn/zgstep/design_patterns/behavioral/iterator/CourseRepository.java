package com.dawn.zgstep.design_patterns.behavioral.iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * 保存数据的仓库
 */
public class CourseRepository implements RContainer {
    private List<Course> dataList;

    public CourseRepository() {
        dataList = new ArrayList<>();
    }

    @Override
    public RIterator getIterator() {
        return new CourseIterator(dataList);
    }

    @Override
    public void addCourse(Course course) {
        dataList.add(course);
    }

    @Override
    public void deleteCourse(Course course) {
        if (dataList.contains(course)) {
            dataList.remove(course);
        }
    }
}
