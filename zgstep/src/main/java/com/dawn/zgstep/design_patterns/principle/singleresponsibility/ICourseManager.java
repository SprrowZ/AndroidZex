package com.dawn.zgstep.design_patterns.principle.singleresponsibility;

/**
 * Create by rye
 * at 2020-06-30
 *
 * @description: 单一职责：操作课程
 */
public interface ICourseManager {
    /**课程操作 职责**/
    void studyCourse();
    //退课程
    void refundCourse();
}
