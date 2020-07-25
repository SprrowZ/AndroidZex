package com.dawn.zgstep.reflect.pojo;

/**
 * Create by rye
 * at 2020-07-25
 *
 * @description: 反射使用
 */
public class User extends Animal implements Action {
    private String name;
    protected Integer age;
    public String idNumber;


    public User() {
    }

    protected User(String name) {
        this.name = name;
    }

    public User(String name, Integer age, String idNumber) {
        this.name = name;
        this.age = age;
        this.idNumber = idNumber;
    }


    @Override
    String getType() {
        return "human";
    }

    @Override
    public void run() {
        System.out.println("human run...");
    }

    @Override
    public void eat() {
        System.out.println("human eat...");
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected Integer getAge() {
        return age;
    }

    protected void setAge(Integer age) {
        this.age = age;
    }

    private String getIdNumber() {
        return idNumber;
    }

    private void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", idNumber='" + idNumber + '\'' +
                '}';
    }


    private class UserInfo {

    }

    public class UserDetail {

    }
}
