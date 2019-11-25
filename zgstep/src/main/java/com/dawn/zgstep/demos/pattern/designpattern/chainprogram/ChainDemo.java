package com.dawn.zgstep.demos.pattern.designpattern.chainprogram;

/**
 * Created at 2018/9/26.
 *
 * @author Zzg
 */
public class ChainDemo {
    private String name;
    private boolean sex;
    private String id;
    private int age;
    private ChainDemo(Builder builder){
        this.name=builder.name;
        this.sex=builder.sex;
        this.id=builder.id;
        this.age=builder.age;
    }
    @Override
    public String toString() {
        return "ChainDemo{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", id='" + id + '\'' +
                ", age=" + age +
                '}';
    }


    static class Builder{
        private String name;
        private boolean sex;
        private String id;
        private int age;
        public Builder name(String name){
            this.name=name;
            return this;
        }
        public Builder sex(boolean sex){
            this.sex=sex;
            return this;
        }
        public Builder id(String id){
            this.id=id;
            return this;
        }
        public Builder age(int age){
            this.age=age;
            return this;
        }
        public ChainDemo create(){
            return new ChainDemo(this);
        }
    }



















































}
