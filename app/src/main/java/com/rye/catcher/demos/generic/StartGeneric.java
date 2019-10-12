package com.rye.catcher.demos.generic;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.rye.catcher.base.dbs.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By RyeCatcher
 * at 2019/8/20
 * 测试泛型的使用
 */

public class StartGeneric {

    @RequiresApi(api = Build.VERSION_CODES.P)
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

    @RequiresApi(api = Build.VERSION_CODES.P)
    public static void testExtend(){
               Platte platte=new Platte<ExtendSuper.Apple>();

               platte.testGeneric();

               List<? extends ExtendSuper.Fruit> genList=new ArrayList<ExtendSuper.Fruit>();
              // genList.set(new ExtendSuper.Apple());

               List<? super ExtendSuper.Fruit>   getTList=new ArrayList<ExtendSuper.Fruit>();
               getTList.add( new ExtendSuper.Apple());
//               ExtendSuper.Apple  apple=getTList.get(0);
               Object object =getTList.get(0);
               System.out.println("下界通配符:"+getTList.get(0));

    }
}
