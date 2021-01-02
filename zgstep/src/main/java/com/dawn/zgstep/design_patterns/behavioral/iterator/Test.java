package com.dawn.zgstep.design_patterns.behavioral.iterator;

public class Test {



    public static void main(String[] args) {
        Course course = new Course("Java");
        Course course2 = new Course("Python");
        Course course3 = new Course("C++");
        Course course4 = new Course("Flutter");
        RContainer repository = new CourseRepository();
        repository.addCourse(course);
        repository.addCourse(course2);
        repository.addCourse(course3);
        printCourses(repository);
        repository.addCourse(course4);
        printCourses(repository);
    }
    private static void printCourses(RContainer repository){
        RIterator iterator = repository.getIterator();
        while (!iterator.isLast()){
            Course course = (Course) iterator.next();
            System.out.println(course.getName());
        }
    }
}
