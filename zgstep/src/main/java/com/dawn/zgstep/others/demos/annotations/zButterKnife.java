package com.dawn.zgstep.others.demos.annotations;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created By RyeCatcher
 * at 2019/10/28
 * 功能仿照ButterKnife
 */
public class zButterKnife {

    public  static   final String TAG="zButterKnife";
    /**
     * findViewById
     * @param activity
     */

    public static void bind(final Activity activity){
        Class<? extends Activity> instance=activity.getClass();
        //--------操作属性
        Field[] fields=  instance.getDeclaredFields();
        for (Field field:fields){
            Log.i(TAG,field.getName());
           BindViewEx bindView= field.getAnnotation(BindViewEx.class);
           if (bindView!=null){//说明有注解
               int id=bindView.value();
               Log.i(TAG,field.getName()+"has Annotation..");
               try{
                   //获取到方法
                   Method method= instance.getMethod("findViewById",int.class);
                   //调用方法，得到返回值
                   Object object=  method.invoke(activity,id);
                  //可见性置为true，就可以访问private 变量了
                   field.setAccessible(true);
                  //给field设定值
                   field.set(activity,object);
                  //处理点击事件---------------还是不能跟属性混合了
               }catch (Exception e){
                   Log.e(TAG,e.toString());
               }

           }


        }
        // TODO: 2019/10/29 需要优化，两层for循环，有重复操作
        //--------操作方法
        Method[] methods=instance.getDeclaredMethods();
        for (final Method method:methods){
          OnClick onClick= method.getAnnotation(OnClick.class);
          if (onClick!=null){
              int[] ids=onClick.value();
              for (int id:ids){
                 activity.findViewById(id).setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         method.setAccessible(true);
                         try {
                             method.invoke(activity);
                         } catch (IllegalAccessException e) {
                             e.printStackTrace();
                         } catch (InvocationTargetException e) {
                             e.printStackTrace();
                         }
                     }
                 });
              }
          }
        }

    }
}
