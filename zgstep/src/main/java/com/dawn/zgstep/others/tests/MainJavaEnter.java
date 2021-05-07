package com.dawn.zgstep.others.tests;

import java.util.List;

public class MainJavaEnter {


    public static void main(String[] args) {
        //  testAnnotation();
        testClass();
    }


    private static void testAnnotation() {
        ListApis.INSTANCE.dealSth();

        Enlist.Companion.conspire();

        Enlist.Companion.getContention();

        Enlist.harbor();

        Enlist.getGenre();

        System.out.println(Enlist.woollen);

        Mango mango = new Mango("小李");

        Mango.Companion.dealSth("American");

    }

    private static void testClass() {

    }

    static class Test1 {
        List<? extends Object> dataList;


    }

    interface ITest {
//        <? extends Object> creteProduct();
    }

}
