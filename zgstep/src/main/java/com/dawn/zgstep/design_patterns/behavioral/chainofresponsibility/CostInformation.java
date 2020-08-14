package com.dawn.zgstep.design_patterns.behavioral.chainofresponsibility;

/**
 * Create by rye
 * at 2020-08-01
 *
 * @description: 参数
 */
public class CostInformation {
    private String applicant;
    private int amount;

    public CostInformation(String applicant,int amount){
        this.applicant =applicant;
        this.amount =amount;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
