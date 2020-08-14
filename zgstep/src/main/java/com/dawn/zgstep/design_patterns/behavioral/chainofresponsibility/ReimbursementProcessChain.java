package com.dawn.zgstep.design_patterns.behavioral.chainofresponsibility;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by rye
 * at 2020-08-01
 *
 * @description: 报销流程 --责任链
 */
public class ReimbursementProcessChain {
    private List<Handler> handlers = new ArrayList<>();

    public void addHandler(Handler handler) {
        this.handlers.add(handler);
    }

    public void removeHandler(Handler handler) {
        if (handlers.size() > 0 && handlers.contains(handler)) {
            handlers.remove(handler);
        }
    }

    public void process(CostInformation costInformation) {
        if (handlers.isEmpty()) return;
        for (int i = 0; i < handlers.size(); i++) {
            Handler handler = handlers.get(i);
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
