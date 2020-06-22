package com.dawn.zgstep.others.demos.pattern.builder;

/**
 * Created at 2019/3/1.
 *
 * @author Zzg
 * @function: 抽象构造者
 */
public abstract class Builder {
    //创建产品对象
    private Product product=new Product();
    abstract void buildPartA();
    abstract  void buildPartB();
    abstract  void buildPartC();
    //返回产品对象
    public Product getResult() {
        return product;
    }
}
