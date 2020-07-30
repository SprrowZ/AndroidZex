package com.dawn.zgstep.design_patterns.structural.decorator.orderv2;

public class OrderWithCarriage extends OrderDecorator {
    public OrderWithCarriage(ABOrder order) {
        super(order);
    }

    @Override
    String getDesc() {
        return super.getDesc() + "加运费";
    }

    @Override
    int getCost() {
        return super.getCost() + 30;
    }
}
