package com.dawn.zgstep.design_patterns.structural.flyweight;

/**
 * Create by rye
 * at 2020-09-09
 *
 * @description: 具体享元实现
 */
public class Book implements IBookAction {

    private String bookName;
    private String author;

    public Book(String bookName) {
        this.bookName = bookName;
    }

    @Override
    public void sell() {
        System.out.println(bookName + "is sold one");
    }

    @Override
    public void sealOff() {
        System.out.println(bookName + "is sealOff one");
    }
}
