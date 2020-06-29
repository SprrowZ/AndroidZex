package com.dawn.zgstep.others.javas.annotations;


import android.util.Log;



import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created By RyeCatcher
 * at 2019/10/24
 */
 
public class TestAnnotations {

  public  static final  String TAG="TestAnnotations";
//  @BindView(1)
//  private String name;

  private String sex;

  public static void main(String[] args){
      getAnnotations("TestAnnotations");
  }

  /**
   * 通过反射获取到注解属性
   * @param className
   */
  public static void getAnnotations(String className){
   try{
     Class instance=Class.forName(className);

    Field[] fields= instance.getDeclaredFields();
     for (Field filed:fields){
         Annotation[] annotations=filed.getAnnotations();
         System.out.println("fieldName:"+filed.getName());
         if (annotations!=null&&annotations.length>0){
             System.out.println(filed.getName()+"..annotation is:"+annotations[0].annotationType());
         }

     }

   }catch (ClassNotFoundException exception){
     Log.d(TAG, "getAnnotations: ");
   }

  }

}
