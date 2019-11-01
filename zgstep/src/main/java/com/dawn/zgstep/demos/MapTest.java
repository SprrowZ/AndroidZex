package com.dawn.zgstep.demos;

import java.util.HashMap;

/**
 * Created By RyeCatcher
 * at 2019/10/31
 */
public class MapTest {

    public static void main(String[] args){
        mapSize();
    }

    /**
     *测试hashMap的初始大小
     */
    public static void mapSize(){
        HashMap hasMap=new HashMap();
        System.out.println(hasMap.size());
    }
}
