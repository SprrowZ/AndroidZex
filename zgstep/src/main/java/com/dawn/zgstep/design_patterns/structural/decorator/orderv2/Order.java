package com.dawn.zgstep.design_patterns.structural.decorator.orderv2;

public class Order extends ABOrder {
    @Override
    String getDesc() {
        return "订单价格：";
    }

    @Override
    int getCost() {
        return 110;
    }
}
