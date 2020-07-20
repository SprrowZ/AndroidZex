package com.dawn.zgstep.design_patterns.creational.abstractfactory;

public class IPhoneFactory implements IProductFactory {
    @Override
    public Computer getComputer() {
        return new IPhoneComputer();
    }

    @Override
    public Tv getTv() {
        return new IPhoneTv();
    }

    @Override
    public MobilePhone getMobilePhone() {
        return new IPhoneMobilePhone();
    }
}
