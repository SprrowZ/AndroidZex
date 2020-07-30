package com.dawn.zgstep.design_patterns.structural.decorator.orderv1;

public class Test {
    public static void main(String[] args) {
        Order order = new OrderWithPremium();
        System.out.println(order.getDesc());
        System.out.println(order.getCost());

        Order order1 = new OrderWithPremiumAndCarriage();
        System.out.println(order1.getDesc());
        System.out.println(order1.getCost());
    }
}
