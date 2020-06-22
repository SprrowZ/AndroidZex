package com.dawn.zgstep.others.demos.generic;

/**
 * Created By RyeCatcher
 * at 2019/10/9
 * 测试泛型中的extend和
 */
public class ExtendSuper {

   static class Food{
        public String Exname=this.getClass().getSimpleName();
        protected  void  output(){
            System.out.println("do something...");
        }

    }

   static class Fruit extends Food{
       public String Exname =this.getClass().getSimpleName();
    }

      static class Apple extends Fruit{
        public String Exname =this.getClass().getSimpleName();
    }

    class Orange extends  Fruit{
        public String Exname =this.getClass().getSimpleName();
    }

}
