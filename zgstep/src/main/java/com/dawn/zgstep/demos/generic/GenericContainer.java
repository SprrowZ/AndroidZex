package com.dawn.zgstep.demos.generic;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created By RyeCatcher
 * at 2019/8/20
 * 泛型类测试
 */
public class GenericContainer<T> {
    private T key;

    private Map userMap;

    private Map<String,Fragment> fragmentMap;

    public GenericContainer() {
           userMap=new HashMap();
           fragmentMap=new HashMap();
    }

    public GenericContainer(T param){
        this.key=param;
    }
   public T getKey(){
        return key;
   }
   public void setKey(T param){
        this.key=param;
   }

    /**
     * 泛型方法！Rye的第一个泛型方法
     * @param param
     * @param <E>
     * @return
     */
   public <E > E findUser(Class<E> param){
         E user;
         user= (E) userMap.get(param.getName());
        return user!=null?user:null;
   }

    public <E> void setUser(Class<E> param) {
       if (!userMap.containsKey(param.getName())){
           userMap.put(param.getName(),param);
       }
    }

    /**
     * 泛型方法测试----
     * @param clz
     * @param info
     * @param <V>
     * @return
     */
    public <V extends Fragment> V changeFragment(Class<V> clz, Bundle info){
       V mFragment=null;
       String clzName=clz.getName();
       if (fragmentMap.containsKey(clzName)){
           mFragment= (V) fragmentMap.get(clzName);
           mFragment.setArguments(info);
       }
       return mFragment;
    }
}
class GenericBean{
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private String name;
    private int age;
}