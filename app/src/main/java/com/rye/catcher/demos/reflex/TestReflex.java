package com.rye.catcher.demos.reflex;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.rye.catcher.beans.AppBean;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by 18041at 2019/6/22
 * Function:
 */
public class TestReflex {
@RequiresApi(api = Build.VERSION_CODES.O)
public static void main(String[] args) throws Exception {
// printFields();
// printDeclaredFields();
// printMethods();
// printDeclaredMethods();
// //操作私有方法
// getPrivateMethod();
// //
// modifyPrivateFields();

    Foo<String> foo=new Foo<String>() {};
    Type mySuperClass = foo.getClass().getGenericSuperclass();
    Type type = ((ParameterizedType)mySuperClass).getActualTypeArguments()[0];
    String className=type.getClass().getName();
    System.out.println(type);
    System.out.println(className);
}

    /**
     * 获取当前类及其父类的public变量
     */
    private static void printFields(){
    Class mClass=SonClass.class;
    System.out.println("类名："+mClass.getName());
    //getFileds获取父类及子类所有的public变量
    Field[] fields=mClass.getFields();
    for (Field field:fields){
        //获取变量修饰符，public，private ，protected--
        int modifiers=field.getModifiers();
        System.out.println(Modifier.toString(modifiers)+"");
        System.out.println(field.getType().getName()+"--"+field.getName());
    }
}
    public static void end(){
        System.out.println("===================.======================");
    }
    /**
     * 获取当前类的所有变量
     */
    public static void printDeclaredFields(){
        Class mClass =SonClass.class;
        System.out.println("类的名称："+mClass.getName());
        //获取当前类的变量，无论是protected，private还是public
        Field[] fields=mClass.getDeclaredFields();
        for (Field field:fields){
            int modifers=field.getModifiers();
            System.out.println(Modifier.toString(modifers)+"");
            System.out.println(field.getType().getName()+""+field.getName());
        }
        end();
}

    /**
     * 获取子类与父类的所有的public方法
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void printMethods(){
        Class sonClass=SonClass.class;
        //2.1 获取所有 public 访问权限的方法
        //包括自己声明和从父类继承的
        Method[] methods=sonClass.getMethods();
        for (Method method:methods){
            //获取返回值类型
            System.out.println("方法名："+method.getName()+"--方法作用域："+Modifier.toString(method.getModifiers()));
            System.out.println("返回值类型："+method.getReturnType().getName());
            //获取方法的所有参数
            Parameter[] parameters=method.getParameters();
            for (Parameter parameter:parameters){

                System.out.println("参数类型："+parameter.getType().getName());
                System.out.print("参数名："+parameter.getName()+"(");
            }
            Class[] exceptionTypes=method.getExceptionTypes();
            if (exceptionTypes.length==0){
                System.out.println("看来是不会抛异常了....）");
            }else{
                for (Class c:exceptionTypes){
                    System.out.println(") throws"+c.getName());
                }
            }
        }
        end();
    }

    /**
     *获取当前类的所有方法
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void printDeclaredMethods(){
        Class sonClass=SonClass.class;
        //2.2 获取当前类所有的方法，不包括父类的
        Method[] methods=sonClass.getDeclaredMethods();
        for (Method method:methods){
            //获取返回值类型
            System.out.println("方法名："+method.getName()+"--方法作用域："+Modifier.toString(method.getModifiers()));
            System.out.println("返回值类型："+method.getReturnType().getName());
            //获取方法的所有参数
            Parameter[] parameters=method.getParameters();
            for (Parameter parameter:parameters){

                System.out.println("参数类型："+parameter.getType().getName());
                System.out.print("参数名："+parameter.getName()+"(");
            }
            Class[] exceptionTypes=method.getExceptionTypes();
            if (exceptionTypes.length==0){
                System.out.println("看来是不会抛异常了....）");
            }else{
                for (Class c:exceptionTypes){
                    System.out.println(") throws"+c.getName());
                }
            }
        }
        end();
    }

    /**
     * 通过反射，使得实例能访问私有方法
     * @throws Exception
     */
    public static  void getPrivateMethod() throws Exception{
        //获取实例
        TestPrivateClass privateClass=new TestPrivateClass();
        Class mClass=privateClass.getClass();
        //2. 获取私有方法
        //第一个参数为要获取的私有方法的名称
        //第二个为要获取方法的参数的类型，参数为 Class...，没有参数就是null
        //方法参数也可这么写 ：new Class[]{String.class , int.class}
        Method privateMethod=mClass.getDeclaredMethod("privateMethod", String.class, int.class);

        if (privateMethod!=null){
            //获取invoke反射调用私有方法
            //privateMethod是获取到的私有方法，如果不设置为true，会抛异常
            privateMethod.setAccessible(true);
            //使用invoke反射调用私有方法..................................
            //privateMethod是获取到的私有方法
            //privateClass是要操作的对象（所以这个方法里new了一个class）
            //后面两个传实参
            privateMethod.invoke(privateClass,"Java Reflect",666);
        }
         end();
    }

    /**
     * 通过反射，修改类里私有变量的值
     * @throws Exception
     */
    public static void modifyPrivateFields() throws Exception{
        TestPrivateClass privateClass=new TestPrivateClass();
        Class mClass=privateClass.getClass();
        //获取私有变量
        Field privateField=mClass.getDeclaredField("MSG");
        if (privateField!=null){
            privateField.setAccessible(true);
            System.out.println("MSG before Modify:"+privateClass.getMsg());
            privateField.set(privateClass,"666666666666");
            System.out.println("MSG after Modify:"+privateClass.getMsg());
        }
        end();
    }

    /**
     * 私有常量就是指final修饰符修饰的成员属性，java虚拟机的一个优化是：
     * JVM在编译阶段会把引用常量的代码替换成具体的常量值；
     * 虽然我们可以在运行时修改私有常量，但在编译阶段jvm还是将其优化为具体的值
     * 运行阶段还是原来的值，所以即使修改了也无意义。但，还是有方法的
     * @throws Exception
     */
    public static void modifyFinalFields() throws Exception{
        //1. 获取 Class 类实例
        TestPrivateClass testClass = new TestPrivateClass();
        Class mClass = testClass.getClass();

        //2. 获取私有常量
        Field finalField = mClass.getDeclaredField("FINAL_VALUE");

        //3. 修改常量的值
        if (finalField != null) {

            //获取私有常量的访问权
            finalField.setAccessible(true);

            //调用 finalField 的 getter 方法
            //输出 FINAL_VALUE 修改前的值
            System.out.println("Before Modify：FINAL_VALUE = "
                    + finalField.get(testClass));

            //修改私有常量
            finalField.set(testClass, "Modified");

            //调用 finalField 的 getter 方法
            //输出 FINAL_VALUE 修改后的值
            System.out.println("After Modify：FINAL_VALUE = "
                    + finalField.get(testClass));

            //使用对象调用类的 getter 方法
            //获取值并输出
            System.out.println("Actually ：FINAL_VALUE = "
                    + testClass.getFinalValue());
        }

    }

}

