package com.dawn.zgstep.design_patterns.behavioral.chainofresponsibility;

import com.dawn.zgstep.design_patterns.behavioral.chainofresponsibility.other.CostInformation;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by rye
 * at 2020-08-01
 *
 * @description: 报销流程 --责任链
 */
public class ReimbursementProcessChain {
    private List<Filter> filters = new ArrayList<>();

    public void addHandler(Filter handler) {
        this.filters.add(handler);
    }

    public void removeHandler(Filter handler) {
        if (filters.size() > 0 && filters.contains(handler)) {
            filters.remove(handler);
        }
    }

    public void process(CostInformation costInformation) {
        if (filters.isEmpty()) return;
        for (int i = 0; i < filters.size(); i++) {
            Filter handler = filters.get(i);
            boolean result = handler.process(costInformation);
            System.out.println(costInformation.getApplicant() + "申请报销：" + costInformation.getAmount()
                    + (result ? " dealt by:" : " transmit by:")
                    + handler.getClass().getSimpleName());
            if (result) {
                System.out.println("----------finish--------");
                break;
            }
        }
    }
}
