package com.dawn.zgstep.design_patterns.creational.builder;

public class Test {
    public static void main(String[] args) {
        Course course = new Course.Builder()
                .setCourseName("Java")
                .setCourseArticle("JavaArticle...emm")
                .setCourseTeacher("TeacherZ")
                .setCourseChapters(10)
                .build();
        System.out.println(course.toString());
    }
}
