package com.dawn.zgstep.design_patterns.structural.decorator.orderv1;

public class OrderWithPremium extends Order {
    @Override
    public String getDesc() {
        return super.getDesc()+" 加运险费";
    }

    @Override
    public int getCost() {
        return super.getCost()+10;
    }
}
