package com.dawn.zgstep.design_patterns.creational.prototype;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Create by rye
 * at 2020-07-21
 *
 * @description:
 */
public class InvoiceEx implements Cloneable {
    private String invoiceHeader;
    private int amountOfMoney;
    //行业分类
    private String[] industryTypes;
    //纸质分类
    private List<String> paperTypes;
    //详情
    private InvoiceExDetail detail;

    public String getInvoiceHeader() {
        return invoiceHeader;
    }

    public void setInvoiceHeader(String invoiceHeader) {
        this.invoiceHeader = invoiceHeader;
    }

    public int getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(int amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    public String[] getIndustryTypes() {
        return industryTypes;
    }

    public void setIndustryTypes(String[] industryTypes) {
        this.industryTypes = industryTypes;
    }

    public List<String> getPaperTypes() {
        return paperTypes;
    }

    public void setPaperTypes(List<String> paperTypes) {
        this.paperTypes = paperTypes;
    }

    public InvoiceExDetail getDetail() {
        return detail;
    }

    public void setDetail(InvoiceExDetail detail) {
        this.detail = detail;
    }

    public InvoiceEx(String header, int money, String[] industry, List<String> paperTypes, InvoiceExDetail detail) {
        this.invoiceHeader = header;
        this.amountOfMoney = money;
        this.industryTypes = industry;
        this.paperTypes = paperTypes;
        this.detail = detail;
    }

    @Override
    protected InvoiceEx clone() throws CloneNotSupportedException {
        InvoiceEx ex = (InvoiceEx)super.clone();
        ex.setPaperTypes(new ArrayList<>(getPaperTypes()));
        ex.setDetail(getDetail().clone());
        return (InvoiceEx) super.clone();
    }

    @NonNull
    @Override
    public String toString() {
        return "invoiceHeader:" + invoiceHeader + "\n amountOfMoney:" + amountOfMoney +
                "\n industryTypes" + Arrays.toString(industryTypes) + "\n paperTypes:" + paperTypes +
                "\n detail:" + detail;
    }

    static class InvoiceExDetail implements Cloneable   {
        //数量
        private int count;
        //单价
        private int unitPrice;
        //开票日期
        private Date invoiceDate;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(int unitPrice) {
            this.unitPrice = unitPrice;
        }

        public Date getInvoiceDate() {
            return invoiceDate;
        }

        public void setInvoiceDate(Date invoiceDate) {
            this.invoiceDate = invoiceDate;
        }

        public InvoiceExDetail(int count, int unitPrice, Date invoiceDate) {
            this.count = count;
            this.unitPrice = unitPrice;
            this.invoiceDate = invoiceDate;
        }

        @Override
        protected InvoiceExDetail clone() throws CloneNotSupportedException {
            return (InvoiceExDetail) super.clone();
        }

        @NonNull
        @Override
        public String toString() {
            return "count:" + count + "~~~unitPrice:" + unitPrice + "~~~invoiceDate:" + invoiceDate;
        }
    }
}
