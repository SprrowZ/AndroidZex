package com.rye.catcher.demos.generic;

/**
 * Created By RyeCatcher
 * at 2019/8/20
 * 测试泛型的使用
 */

public class StartGeneric {

    public static void main(String[] args){
        testMethod();
    }

    public  static void testMethod(){
        GenericContainer<String> container=new GenericContainer<String>();
        container.setKey("hello");
        System.out.println(container.getKey());
        //泛型
        GenericBean bean=new GenericBean();
        bean.setName("Rye");
        bean.setAge(24);
        container.setUser(bean.getClass());
        //
       ;
         System.out.println( container.findUser(GenericBean.class));


    }
}
