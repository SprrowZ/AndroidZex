package com.dawn.zgstep.design_patterns.behavioral.chainofresponsibility.other;

import com.dawn.zgstep.design_patterns.behavioral.chainofresponsibility.CEOFilter;
import com.dawn.zgstep.design_patterns.behavioral.chainofresponsibility.DirectorFilter;
import com.dawn.zgstep.design_patterns.behavioral.chainofresponsibility.ManagerFilter;
import com.dawn.zgstep.design_patterns.behavioral.chainofresponsibility.ReimbursementProcessChain;

/**
 * Create by rye
 * at 2020-08-01
 *
 * @description:
 */
public class Test {
    public static void main(String[] args) {
        ManagerFilter managerHandler = new ManagerFilter();
        DirectorFilter directorHandler = new DirectorFilter();
        CEOFilter ceoHandler = new CEOFilter();

        ReimbursementProcessChain chain = new ReimbursementProcessChain();
        chain.addHandler(managerHandler);
        chain.addHandler(directorHandler);
        chain.addHandler(ceoHandler);

        CostInformation costInformation = new CostInformation("老李",1200);
    //    CostInformation costInformation1 = new CostInformation("老张",19999);
        chain.process(costInformation);
      //  chain.process(costInformation1);
    }
}
