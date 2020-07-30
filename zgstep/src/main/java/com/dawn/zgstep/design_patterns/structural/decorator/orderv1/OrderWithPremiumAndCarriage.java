package com.dawn.zgstep.design_patterns.structural.decorator.orderv1;

public class OrderWithPremiumAndCarriage extends OrderWithPremium {
    @Override
    public String getDesc() {
        return super.getDesc()+"再加运费";
    }

    @Override
    public int getCost() {
        return super.getCost()+30;
    }
}
