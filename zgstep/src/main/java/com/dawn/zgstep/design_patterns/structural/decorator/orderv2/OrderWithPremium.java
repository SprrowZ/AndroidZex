package com.dawn.zgstep.design_patterns.structural.decorator.orderv2;

public class OrderWithPremium extends  OrderDecorator {
    public OrderWithPremium(ABOrder order) {
        super(order);
    }

    @Override
    String getDesc() {
        return super.getDesc()+"加运险费";
    }

    @Override
    int getCost() {
        return super.getCost()+10;
    }
}
