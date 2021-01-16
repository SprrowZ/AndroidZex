package com.dawn.zgstep.design_patterns.other.principle.dependencyinversion;

/**
 * Create by rye
 * at 2020-06-30
 *
 * @description: Geely是个人，他要学习不同的课程
 */
public class Geely {
//    public void studyJavaCourse(){
//      System.out.println("Geely 学习java课程");
//    }
//    public void studyFECourse(){
//        System.out.println("Geely 学习FE课程");
//    }
//
//    public void studyPythonCourse(){
//        System.out.println("Geely 学习Python课程");
//    }

    /*具体实现交给上层选择，而不是针对这个底层实现
    * */
    public void studyCourse(ICourseEx course){
        course.studyCourse();
    }
}
