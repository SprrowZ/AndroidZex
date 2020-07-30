package com.dawn.zgstep.design_patterns.structural.decorator.orderv2;

public class OrderDecorator extends ABOrder {
    private ABOrder mABOrder;
    public  OrderDecorator(ABOrder order){
        this.mABOrder = order;
    }
    @Override
    String getDesc() {
        return mABOrder.getDesc();
    }

    @Override
    int getCost() {
        return mABOrder.getCost();
    }
}
