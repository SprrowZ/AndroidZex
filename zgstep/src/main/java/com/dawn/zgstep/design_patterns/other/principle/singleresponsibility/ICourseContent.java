package com.dawn.zgstep.design_patterns.other.principle.singleresponsibility;

/**
 * Create by rye
 * at 2020-06-30
 *
 * @description: 单一职责：获取课程信息
 */
public interface ICourseContent {

    String getCourseName();
    byte[] getCourseVideo();
}
