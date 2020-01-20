package com.dawn.zgstep.javas.java8.beans;

import java.util.Comparator;

public class Student implements Comparator {
    private int id;
    private String name;
    private int age;
    private int sex;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    /**
     * 按照年龄排序
     * @param o1
     * @param o2
     * @return
     */
    @Override
    public int compare(Object o1, Object o2) {
       return  ((Student)o1).age - ((Student)o2).age;
    }
}
