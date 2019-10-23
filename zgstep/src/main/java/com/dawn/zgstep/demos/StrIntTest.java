package com.dawn.zgstep.demos;

/**
 * Created By RyeCatcher
 * at 2019/8/23
 */
public class StrIntTest {

    public static void main(String[] args){
       // testOne();
        testThree();
    }

    /**
     * 测试==和equals
     */
    public static void testOne(){
        String str1 = "Lance";
        String str2 = new String("Lance");
        String str3 = str2; //引用传递，str3直接指向st2的堆内存地址
        String str4 = "Lance";
        /**
         *  ==:
         * 基本数据类型：比较的是基本数据类型的值是否相同
         * 引用数据类型：比较的是引用数据类型的地址值是否相同
         * 所以在这里的话：String类对象==比较，比较的是地址，而不是内容
         */
        System.out.println(str1==str2);//false
        System.out.println(str1==str3);//false
        System.out.println(str3==str2);//true
        System.out.println(str1==str4);//true
    }
    public static void testTwo(){
         StringBean bean=new StringBean();
         bean.setName("张三");
         String name="张三";
         String nameEx=new String("张三");
         System.out.println("beans.getName == name:"+bean.getName()==name);//false
         System.out.println("beans.getName == nameEx:"+bean.getName()==nameEx);//false
         System.out.println("beans.getName.equals(name)"+bean.getName().equals(name));//true
         System.out.println("name.equals(nameEx):"+name.equals(nameEx));//true
     }

    /**
     * 测试Integer
     * 自动装箱/拆箱
     */
     public static void testThree(){
     Integer a=new Integer(3);
     Integer bxxxxxxx=3;
     int c=3;
     System.out.println(a==bxxxxxxx);//false--两个不是同一个对象
     System.out.println(a==c);//true---



    }

    static class StringBean{
        String name;
        int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
