package com.dawn.zgstep.design_patterns.structural.decorator.orderv2;

public class Test {
    public static void main(String[] args) {
        ABOrder order = new Order();
        System.out.println(order.getDesc());
        System.out.println(order.getCost());
        //加上运费
        ABOrder orderWithCarriage = new OrderWithCarriage(order);
        System.out.println(orderWithCarriage.getDesc());
        System.out.println(orderWithCarriage.getCost());
        //再加上保险费
        ABOrder orderWithCarriageAndPremium = new OrderWithPremium(orderWithCarriage);
        System.out.println(orderWithCarriageAndPremium.getDesc());
        System.out.println(orderWithCarriageAndPremium.getCost());
        //自由组合：只加运费(包邮)；加店铺红包(价格反向增长)；---比继承好在可以自由组合、装饰，不用建更多
        //的组合类
    }
}
