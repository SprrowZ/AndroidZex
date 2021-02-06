package com.dawn.zgstep.design_patterns.other.principle.singleresponsibility;

/**
 * Create by rye
 * at 2020-06-30
 *
 * @description: 两个职责可以分别实现
 */
public class CourseImpl implements ICourseContent, ICourseManager {
    @Override
    public String getCourseName() {
        return null;
    }

    @Override
    public byte[] getCourseVideo() {
        return new byte[0];
    }

    @Override
    public void studyCourse() {

    }

    @Override
    public void refundCourse() {

    }
}
