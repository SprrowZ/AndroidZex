package com.dawn.zgstep.design_patterns.creational.abstractfactory;

public interface IProductFactory {
    Computer getComputer();
    Tv getTv();
    MobilePhone getMobilePhone();
}
