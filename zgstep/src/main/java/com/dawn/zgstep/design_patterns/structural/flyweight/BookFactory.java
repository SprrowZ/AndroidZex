package com.dawn.zgstep.design_patterns.structural.flyweight;

import java.util.HashMap;

/**
 * Create by rye
 * at 2020-09-09
 *
 * @description:  享元模式 ->工厂类
 */
public class BookFactory {

    private static final HashMap<String, Book> books = new HashMap<>();

    public static Book getBook(String name) {
        if (books.containsKey(name)) {
            System.out.println("取出书籍:"+name);
            return books.get(name);
        } else {
            Book book = new Book(name);
            //日志
            System.out.println("创建书籍:"+name);
            books.put(name,book);
            return book;
        }
    }

}
