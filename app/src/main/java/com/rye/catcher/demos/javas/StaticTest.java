package com.rye.catcher.demos.javas;

/**
 * Created By RyeCatcher
 * at 2019/10/10
 * 测试父类的静态方法是否可被子类重写
 */
public class StaticTest {

    public static void doMethod1(){
        System.out.println("父类静态方法");
    }
}
class SonStatic extends  StaticTest{


    public static void doMethod1(){

    }

}
