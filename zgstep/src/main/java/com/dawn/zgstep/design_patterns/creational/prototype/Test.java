package com.dawn.zgstep.design_patterns.creational.prototype;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test {
    public static void main(String[] args) throws CloneNotSupportedException {
        testProto2();
    }
    public static void testProto1() throws CloneNotSupportedException {
        Invoice.InvoiceDetail detail = new Invoice.InvoiceDetail(1, 250, new Date());

        Invoice proto = new Invoice("ICBC", 666666, "餐饮费", 250, detail);
        System.out.println(proto);
        //克隆对象


        Invoice clone1 = proto.clone();
        clone1.setInvoiceHeader("CB");
        clone1.setAccountNumber(555555);
        clone1.getInvoiceDetail().setCount(22);

        System.out.println(clone1);

        System.out.println(proto);
    }
    public static void testProto2() throws CloneNotSupportedException {
        InvoiceEx.InvoiceExDetail invoiceExDetail = new InvoiceEx.InvoiceExDetail(1, 250, new Date());

        List<String> paperTypes = new ArrayList<>();
        paperTypes.add("普通纸");
        paperTypes.add("压感纸");

        String[] industries = new String[]{"工业", "商业"};

        InvoiceEx proto = new InvoiceEx("ICBC", 250, industries,
                paperTypes, invoiceExDetail);
        System.out.println("原型：\n"+proto);
        System.out.println("-------------------------------------");
        //开始克隆和修改
        InvoiceEx clones = proto.clone();

        //修改克隆，看是否会影响之前
        clones.setAmountOfMoney(25);
        //新增
        List<String> papers = clones.getPaperTypes();
        papers.add("拷贝纸");
        papers.add("油墨纸");
        clones.setPaperTypes(papers);
        InvoiceEx.InvoiceExDetail detail2 = clones.getDetail();
        detail2.setCount(222);
        clones.setDetail(detail2);
        //开始打印信息
        System.out.println("克隆:\n"+clones);
        System.out.println("-------------------------------------");
        System.out.println("修改克隆之后的原型:\n"+proto);
    }
}
