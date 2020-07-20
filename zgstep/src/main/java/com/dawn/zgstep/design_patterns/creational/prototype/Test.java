package com.dawn.zgstep.design_patterns.creational.prototype;

import java.util.Date;

public class Test {
    public static void main(String[] args) throws CloneNotSupportedException {
        Invoice.InvoiceDetail detail = new Invoice.InvoiceDetail(1,250,new Date());
        Invoice proto = new Invoice("ICBC",666666,"餐饮费",250,detail);
        System.out.println(proto);
        //克隆对象

        Invoice clone1 = proto.clone();
        clone1.setInvoiceHeader("CB");
        clone1.setAccountNumber(555555);
        System.out.println(clone1);

        System.out.println(proto);



    }
}
