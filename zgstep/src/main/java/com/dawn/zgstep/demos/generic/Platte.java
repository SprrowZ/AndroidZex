package com.dawn.zgstep.demos.generic;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created By RyeCatcher
 * at 2019/10/10
 */
public class Platte<T extends ExtendSuper.Fruit> {
    public  T item;
    public Platte(){

    }
  public static void main(String args[]){

  }
    @RequiresApi(api = Build.VERSION_CODES.P)
    public void testGeneric(){
        Class c=this.getClass();
        Type type= c.getGenericSuperclass();
        String typeName=type.getTypeName();
        System.out.println(typeName);
    }


    Map hashMap=new HashMap();
    public void setKey(T item){
        hashMap.put(item.getClass().getSimpleName(),item);
    }
    public T getKey(T item){
        return (T) hashMap.get(item.getClass().getSimpleName());
    }

    public void sys(){
//        System.out.println(getKey());
    }
}
