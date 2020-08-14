package com.dawn.zgstep.design_patterns.behavioral.chainofresponsibility;

/**
 * Create by rye
 * at 2020-08-01
 *
 * @description: 一级处理人 ：CEO
 */
public class CEOHandler  implements  Handler{
    @Override
    public boolean process(CostInformation costInformation) {
        //ceo can deal any amount of money ..
        return true;
    }
}
