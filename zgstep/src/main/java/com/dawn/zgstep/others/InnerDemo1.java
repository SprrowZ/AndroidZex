package com.dawn.zgstep.others;

/**
 * Java模拟闭包
 */
public class InnerDemo1 {
    class Bar{
        public void show(){
            //do ..
        }
    }
    public static void main(String[] args) {
        InnerDemo1 innerDemo01=new InnerDemo1();
        Bar bar=innerDemo01.method();
        bar.show();//你觉得num会输出吗？？？？
    }
    public Bar  method(){
        //String str="wuranghao";
        final int num=30;
        //局部内部类将输出这个局部变量
        class innerClass extends Bar{

            public void show(){
                System.out.println(num);
            }
        }
        return new innerClass();
    }

}
