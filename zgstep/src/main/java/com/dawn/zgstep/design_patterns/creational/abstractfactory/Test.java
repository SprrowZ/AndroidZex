package com.dawn.zgstep.design_patterns.creational.abstractfactory;

public class Test {
    public static void main(String[] args) {
        //创建小米TV
        IProductFactory factory = new MiProduceFactory();
        Tv miTv = factory.getTv();
        miTv.printlTvInfo();
        //创建苹果Computer
        IProductFactory factory1 = new IPhoneFactory();
        Computer iphoneComputer = factory1.getComputer();
        iphoneComputer.printComputerInfo();
        //新增产品的话，只需写一个XXProduct基础抽象类，子产品继承
        //然后新增工厂类，在IProductFactory新增产品方法,然后新增的工厂类继承此
        //问题是，比如小米新增一个智能家居的产品，如果抽象工厂里新增了一个产品方法，
        // 其他两家没有的话
        //如果用抽象方法来搞，就会导致其他两家的工厂里有些空实现.....


    }
}
