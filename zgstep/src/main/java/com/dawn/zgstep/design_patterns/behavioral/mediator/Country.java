package com.dawn.zgstep.design_patterns.behavioral.mediator;

public class Country {
    public String countryName;
    public Country(String countryName){
        this.countryName = countryName;
    }
    public void buyOil(Country producer){
        System.out.println(this.countryName +"买了" +producer.countryName +"的石油");
    }
}
