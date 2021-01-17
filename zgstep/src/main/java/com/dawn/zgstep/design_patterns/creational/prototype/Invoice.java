package com.dawn.zgstep.design_patterns.creational.prototype;

import java.util.Date;

/**
 * 浅拷贝；对象只拷贝了内存空间，没有真的新建一个对象。
 */
public class Invoice implements Cloneable {
    //发票头
    private String invoiceHeader;
    //开户账号
    private long accountNumber;
    //商品名称
    private String tradeName;
    //金额
    private int amountOfMoney;
    //详情
    private InvoiceDetail invoiceDetail;

    public Invoice(String header,long number,String name,int money,InvoiceDetail detail){
        this.invoiceHeader =header;
        this.accountNumber =number;
        this.tradeName = name;
        this.amountOfMoney = money;
        this.invoiceDetail = detail;
    }


    @Override
    protected Invoice clone() throws CloneNotSupportedException {
        return (Invoice)super.clone();
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceHeader='" + invoiceHeader + '\'' +
                ", accountNumber=" + accountNumber +
                ", tradeName='" + tradeName + '\'' +
                ", amountOfMoney=" + amountOfMoney +
                ", detail=" + invoiceDetail +
                '}';
    }

    public String getInvoiceHeader() {
        return invoiceHeader;
    }

    public void setInvoiceHeader(String invoiceHeader) {
        this.invoiceHeader = invoiceHeader;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public int getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(int amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    public InvoiceDetail getInvoiceDetail() {
        return invoiceDetail;
    }

    public void setInvoiceDetail(InvoiceDetail invoiceDetail) {
        this.invoiceDetail = invoiceDetail;
    }

    static class InvoiceDetail implements Cloneable{
        //数量
        private int count;
        //单价
        private int unitPrice;
        //开票日期
        private Date invoiceDate;

        public InvoiceDetail(int count,int unitPrice,Date date){
            this.count =count;
            this.unitPrice =unitPrice;
            this.invoiceDate = date;
        }


        @Override
        public String toString() {
            return "InvoiceDetail{" +
                    "count=" + count +
                    ", unitPrice=" + unitPrice +
                    ", invoiceDate=" + invoiceDate +
                    '}';
        }

        @Override
        protected InvoiceDetail clone() throws CloneNotSupportedException {
            return (InvoiceDetail)super.clone();
        }

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
    }
}
