package com.dawn.zgstep.principle;

import com.dawn.zgstep.principle.openclose.ICourse;
import com.dawn.zgstep.principle.openclose.JavaCourse;

/**
 * Create by rye
 * at 2020-06-29
 *
 * @description:
 */
public class Test {
    public static void main(String[] args){
        ICourse javaSource = new JavaCourse(96,"Java设计模式精讲",299d);
        System.out.println("课程ID:"+javaSource.getId()+"\n课程名:"+javaSource.getName());
    }
}
