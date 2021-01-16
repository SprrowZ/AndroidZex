package com.dawn.zgstep.design_patterns.other.principle.openclose;

/**
 * Create by rye
 * at 2020-06-30
 *
 * @description:
 */
public class JavaDiscountPrice extends JavaCourse {
    public JavaDiscountPrice(Integer id, String name, Double price) {
        super(id, name, price);
    }

    public Double getDiscountPrice(){
        return getPrice()*0.8;
    }
}
