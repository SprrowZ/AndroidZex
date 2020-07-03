package com.dawn.zgstep.design_patterns.principle.singleresponsibility;

/**
 * Create by rye
 * at 2020-06-30
 *
 * @description: 职责：1.获取课程信息、2.操作课程
 */
public interface ICourseX {
   String getCourseName();
   byte[] getCourseVideo();

/**课程操作 职责**/
   void studyCourse();
   //退课程
   void refundCourse();
}
