package com.rye.catcher.demos.annotations;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by 18041at 2019/6/23
 * Function:
 */
public class TestGeneric<T> {
    private Class<T> mClass;

//    public  void getTClass(){
//        mClass=getEntry();
//       String className= mClass.getClass().getName();
//        System.out.println(className);
//    }

//    private Class<T> getEntry() {
//        Type type = processResponseListener.getClass().getGenericSuperclass();
//        Type[] params = ((ParameterizedType) type).getActualTypeArguments();
//        Class<T> reponseClass = (Class) params[0];
//
//
//        return mClass;
//    }


}
