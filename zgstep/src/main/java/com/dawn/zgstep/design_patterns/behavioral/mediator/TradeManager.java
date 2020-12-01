package com.dawn.zgstep.design_patterns.behavioral.mediator;

public class TradeManager {
    public static void buyOil(Country producer,Country consumer){
        //do much thing
        System.out.println(consumer.countryName +"购买了"+producer.countryName+"的石油");
    }
}
