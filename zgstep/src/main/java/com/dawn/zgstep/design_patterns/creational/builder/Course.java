package com.dawn.zgstep.design_patterns.creational.builder;

public class Course {
    public String courseName;
    public String courseTeacher;
    public int courseChapters;
    public String courseArticle;

    public Course(Builder builder){
        this.courseName = builder.courseName;
        this.courseTeacher = builder.courseTeacher;
        this.courseChapters = builder.courseChapters;
        this.courseArticle = builder.courseArticle;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseName='" + courseName + '\'' +
                ", courseTeacher='" + courseTeacher + '\'' +
                ", courseChapters=" + courseChapters +
                ", courseArticle='" + courseArticle + '\'' +
                '}';
    }

    public static class Builder {
        private String courseName;
        private String courseTeacher;
        private int courseChapters;
        private  String courseArticle;

        public  Builder setCourseName(String name){
            this.courseName = name;
            return this;
        }
        public Builder setCourseTeacher(String teacher){
            this.courseTeacher = teacher;
            return this;
        }
        public Builder setCourseChapters(int chapters){
            this.courseChapters = chapters;
            return this;
        }
        public Builder setCourseArticle(String article){
            this.courseArticle = article;
            return this;
        }
        public Course build(){
            return new Course(this);
        }
    }
}
