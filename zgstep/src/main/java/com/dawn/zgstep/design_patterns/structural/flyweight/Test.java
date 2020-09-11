package com.dawn.zgstep.design_patterns.structural.flyweight;

import java.util.Random;

/**
 * Create by rye
 * at 2020-09-09
 *
 * @description:
 */
public class Test {
    public static void main(String[] args) {
        String[] bookNames =new String[]{"《时间简史》","《物种起源》","《麦田守望者》"};
        for (int i =0;i<20;i++){
            int pos = new Random().nextInt(3);
            BookFactory.getBook(bookNames[pos]);
        }
    }
}
