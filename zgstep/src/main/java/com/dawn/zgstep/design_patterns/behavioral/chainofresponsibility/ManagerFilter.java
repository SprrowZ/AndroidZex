package com.dawn.zgstep.design_patterns.behavioral.chainofresponsibility;

import com.dawn.zgstep.design_patterns.behavioral.chainofresponsibility.other.CostInformation;

/**
 * Create by rye
 * at 2020-08-01
 *
 * @description: 三级处理人 ：经理
 */
public class ManagerFilter implements Filter {
    @Override
    public boolean process(CostInformation costInformation) {
        if (costInformation.getAmount()<1000){
            return true;
        }
        return false;
    }
}
