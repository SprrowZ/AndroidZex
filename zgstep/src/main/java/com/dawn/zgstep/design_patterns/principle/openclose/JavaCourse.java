package com.dawn.zgstep.design_patterns.principle.openclose;

/**
 * Create by rye
 * at 2020-06-29
 *
 * @description: 具体实现
 */
public class JavaCourse implements  ICourse {
    private Integer id;
    private String name;
    private Double price;

    public JavaCourse(Integer id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Double getPrice() {
        return this.price;
    }
}
