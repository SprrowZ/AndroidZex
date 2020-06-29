package com.dawn.zgstep.others.javas.java8;

/**
 * 测试java8的双冒号用法
 */
public class DoubleColon {

    private String token;
    private String account;



    public static void getData(){
        System.out.println("....");
    }
    public static void printValur(Object str){
        System.out.println("print value : "+str);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    class User {
        private String name;
        private int sex;
        private String address;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
