package com.dawn.zgstep.reflect.classes;

import android.annotation.SuppressLint;

import com.dawn.zgstep.reflect.MethodUtils;
import com.dawn.zgstep.reflect.pojo.Animal;
import com.dawn.zgstep.reflect.pojo.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import java.lang.reflect.Method;


/**
 * Create by rye
 * at 2020-07-25
 *
 * @description:
 */
public class ClassTest {
    public static void main(String[] args) throws Exception {
        //  testClass1();
        //     testField();
        // testConstructors();
        // testMethods();
        //  testFieldEx();
        //testMethodEx();
        testMethodUtil();
    }


    private static void testClass1() throws ClassNotFoundException {
        Class<User> userClass = User.class;
        printInfo("包名:" + userClass.getPackage(), false);
        Class<?> userClass2 = Class.forName("com.dawn.zgstep.reflect.pojo.User");
        //所有public类和public 接口
        printInfo("getClasses:", true);
        for (Class<?> clazz : userClass2.getClasses()) {
            printInfo(clazz.getSimpleName(), false);
        }
        //所有类和接口
        printInfo("getDeclaredClasses:", true);
        for (Class<?> clazz : userClass2.getDeclaredClasses()) {
            printInfo(clazz.getSimpleName(), false);
        }
        printInfo("getSuperClass:" + userClass2.getSuperclass(), true);
        printInfo("可能会有多个接口,getInterfaces:" + userClass2.getInterfaces(), true);

        try {
            Animal sub = Class.forName("com.dawn.zgstep.reflect.pojo.User")
                    .asSubclass(Animal.class).newInstance();
            printInfo("asSubclass 约束被反射类：" + sub, true);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }


    private static void testField() throws ClassNotFoundException {
        Class<?> userClass = Class.forName("com.dawn.zgstep.reflect.pojo.User");
        printInfo("getFields:", false);
        for (Field field : userClass.getFields()) {
            printInfo(field.getName(), false);
        }
        printInfo("getDeclaredFields:", true);
        for (Field field : userClass.getDeclaredFields()) {
            printInfo(field.getName(), false);
        }
    }

    @SuppressLint("NewApi")
    private static void testConstructors() throws Exception {
        Class<User> userClass = User.class;
        printInfo("getConstructors:" + userClass.getConstructors().length, true);
        for (Constructor constructor : userClass.getConstructors()) {
            int count = constructor.getParameterCount();
            printInfo("构造函数参数个数：" + count, false);
        }
        printInfo("getDeclaredConstructors:" + userClass.getDeclaredConstructors().length, true);

        //获取构造方法，构造一个对象
        Constructor userConstructor = userClass.getDeclaredConstructor();
        User user = (User) userConstructor.newInstance();
        user.setName("二狗");
        printInfo("newInstance:" + user.getName(), true);
        //获取构造方法
        User user1 = userClass
                .getDeclaredConstructor(String.class, Integer.class, String.class)
                .newInstance("二狗", 15, "9527");
        printInfo("newInstance,三个构造参数：" + user1.toString(), true);
    }

    private static void testMethods() throws NoSuchMethodException {
        Class<User> userClass = User.class;
        printInfo("获取所有声明的方法", true);
        for (Method method : userClass.getDeclaredMethods()) {
            printInfo("方法：" + method.getName(), false);
        }
        Method getName = userClass.getDeclaredMethod("setName", String.class);
        printInfo("获取指定方法：" + getName.getName(), true);
    }

    private static void testFieldEx() throws NoSuchFieldException, IllegalAccessException {
        User user = new User("二狗", 18, "2233");
        Class<?> clazz = user.getClass();
        Field field = clazz.getDeclaredField("name");
        //暴力
        field.setAccessible(true);
        String name = (String) field.get(user);
        printInfo(user.toString(), true);
        printInfo("field.get(): name:" + name, true);
        //
        field.set(user, "大黄");
        printInfo("field.set 之后：" + user.toString(), true);
    }

    private static void testMethodEx() throws Exception {
        User user = new User("二狗", 18, "2223");
        Class<User> userClass = User.class;
        //无参方法调用
        Method method = userClass.getDeclaredMethod("run");
        method.invoke(user);
        //有参方法调用
        Method method1 = userClass.getDeclaredMethod("setName", String.class);
        method1.invoke(user, "大黄");
        printInfo(user.toString(), true);
    }

    private static void testMethodUtil() {
        User user = new User("二狗", 18, "2223");
        //无参构造
        String name = (String) MethodUtils.invokeMethod(user, "getName", "");
        printInfo("getName:" + name,true);
        //有参
        MethodUtils.invokeMethod(user,"setName","大黄");
        printInfo("setName之后的对象："+user.toString(),true);
    }


    private static void printInfo(String content, boolean split) {
        if (split) {
            System.out.println("--------------  =.= ----------------");
        }
        System.out.println(content);
    }
}
