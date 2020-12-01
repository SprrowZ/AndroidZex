package com.dawn.zgstep.design_patterns.behavioral.chainofresponsibility;

import com.dawn.zgstep.design_patterns.behavioral.chainofresponsibility.other.CostInformation;

/**
 * Create by rye
 * at 2020-08-01
 *
 * @description: 二级处理人 ：主管
 */
public class DirectorFilter implements Filter {
    @Override
    public boolean process(CostInformation costInformation) {
        if (costInformation.getAmount() >= 1000 && costInformation.getAmount() < 10000) {
            return true;
        }
        //---
        return false;
    }
}