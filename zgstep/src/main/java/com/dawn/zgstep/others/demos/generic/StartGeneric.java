package com.dawn.zgstep.others.demos.generic;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By RyeCatcher
 * at 2019/8/20
 * 测试泛型的使用
 */

public class StartGeneric {


    public static void main(String[] args){
    //    testMethod();
        testExtend();
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

        System.out.println( container.findUser(GenericBean.class));


    }


    public static void testExtend(){
               Platte platte=new Platte<ExtendSuper.Apple>();

            //   platte.testGeneric();

               List<? extends ExtendSuper.Fruit> genList=new ArrayList<ExtendSuper.Fruit>();
              // genList.set(new ExtendSuper.Fruit());

               List<? super ExtendSuper.Fruit>   getTList=new ArrayList<ExtendSuper.Fruit>();
               getTList.add( new ExtendSuper.Apple());
//               ExtendSuper.Fruit  apple=getTList.get(0);
               Object object =getTList.get(0);
               System.out.println("下界通配符:"+getTList.get(0));

    }
}
