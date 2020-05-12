package com.dawn.zgstep.mains;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.dawn.zgstep.javas.java8.DoubleColon;
import com.dawn.zgstep.javas.java8.StreamInterface;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class MainEntry {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void main(String[] args){
        StreamInterface.testStreamOne();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void testDoubleColon(){
        List al = Arrays.asList("a", "b", "c", "d");
        al.forEach(DoubleColon::printValur);
        //下面的方法和上面等价的
        Consumer methodParam = DoubleColon::printValur; //方法参数
        al.forEach(x -> methodParam.accept(x));//方法执行accept
    }

    public static void testDoubleColon2(){
        new Thread(MainEntry::printValur).start();
    }

    private static void printValur() {
    }

    public static void printValur(String str){
        System.out.println("print value : "+str);
    }


}
