package com.dawn.zgstep.design_patterns.structural.facade;

/**
 * Create by rye
 * at 2020-07-25
 *
 * @description:
 */
public class Test {
    public static void main(String[] args) {
//        ConsumeService consumeService = new ConsumeService();
//        consumeService.startShopping();
        newCousumeProcedure();
    }

    private static void newCousumeProcedure(){
        BaseConsumeService consumeService = new NewConsumeService();
        consumeService.consume();
    }
}
