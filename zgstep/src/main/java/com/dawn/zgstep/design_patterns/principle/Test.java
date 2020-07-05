package com.dawn.zgstep.design_patterns.principle;

import com.dawn.zgstep.design_patterns.principle.openclose.JavaDiscountPrice;
import com.dawn.zgstep.design_patterns.principle.singleresponsibility.Bird;
import com.dawn.zgstep.design_patterns.principle.dependencyinversion.FECourseEx;
import com.dawn.zgstep.design_patterns.principle.dependencyinversion.Geely;
import com.dawn.zgstep.design_patterns.principle.dependencyinversion.JavaCourseEx;
import com.dawn.zgstep.design_patterns.principle.openclose.ICourse;

/**
 * Create by rye
 * at 2020-06-29
 *
 * @description:
 */
public class Test {
    public static void main(String[] args) {
        dependencyInversion();
    }

    /**
     * 开闭原则测试
     */
    private static void openCloseTest() {
        ICourse source = new JavaDiscountPrice(96, "Java设计模式精讲", 299d);
        JavaDiscountPrice javaSource = (JavaDiscountPrice) source;
        System.out.println("课程ID:" + javaSource.getId() + "\n课程名:" + javaSource.getName() +
                "\n折后价：" + javaSource.getDiscountPrice());
    }

    /**
     * 依赖倒置原则
      */
    private static void dependencyInversion() {
        //  v1
//        Geely geely = new Geely();
//        geely.studyFECourse();
//        geely.studyJavaCourse();
        //  v2
        Geely geely = new Geely();
        geely.studyCourse(new JavaCourseEx());
        geely.studyCourse(new FECourseEx());

    }

    /**
     * 单一职责原则
     */
    private static void singleResponsibility(){
        Bird bird = new Bird();

    }


}
