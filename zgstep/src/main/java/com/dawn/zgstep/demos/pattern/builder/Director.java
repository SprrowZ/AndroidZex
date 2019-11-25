package com.dawn.zgstep.demos.pattern.builder;

/**
 * Created at 2019/3/1.
 *
 * @author Zzg
 * @function: 导演,将创建复杂对象的过程分离；控制复杂对象的创建过程。
 */
public class Director {

    private Builder builder;
    public Director(Builder builder){
        this.builder=builder;
    }
    public void setBuilder(Builder builder){
        this.builder=builder;
    }
    //产品构建与组装方法
    public Product construct(){
        builder.buildPartA();
        builder.buildPartB();
        builder.buildPartC();
        return builder.getResult();
    }
}
