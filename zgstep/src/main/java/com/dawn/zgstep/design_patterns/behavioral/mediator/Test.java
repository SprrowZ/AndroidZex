package com.dawn.zgstep.design_patterns.behavioral.mediator;

public class Test {
    private static int p;
    public static void main(String[] args) {
     //  method1();
        method2();
    }

    private static void method1(){
        Country america = new Country("美国");
        Country china = new Country("中国");
        Country japan = new Country("小日本");
        Country england = new Country("英国");
        //---do much thing
        america.buyOil(china);
        //---do much thing
        china.buyOil(england);
        //---do much thing
        japan.buyOil(america);
        //---do much thing
        england.buyOil(japan);
    }
    private static void mediator(){
        Country america = new Country("美国");
        Country china = new Country("中国");
        Country japan = new Country("小日本");
        Country england = new Country("英国");
        TradeManager.buyOil(america,china);
        TradeManager.buyOil(china,england);
        TradeManager.buyOil(japan,america);
    }

    private static void method2(){
        System.out.println(p);
    }

}
