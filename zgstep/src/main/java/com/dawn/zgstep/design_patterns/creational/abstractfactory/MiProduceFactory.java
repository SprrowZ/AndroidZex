package com.dawn.zgstep.design_patterns.creational.abstractfactory;

public class MiProduceFactory implements IProductFactory {
    @Override
    public Computer getComputer() {
        return new MiComputer();
    }

    @Override
    public Tv getTv() {
        return new MiTv();
    }

    @Override
    public MobilePhone getMobilePhone() {
        return new MiMobilePhone();
    }
}
