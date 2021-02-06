package com.dawn.zgstep.design_patterns.other.principle.dependencyinversion;

/**
 * Create by rye
 * at 2020-06-30
 *
 * @description:
 */
public class JavaCourseEx implements ICourseEx {
    @Override
    public void studyCourse() {
        System.out.println("学习Java课程");
    }
}
