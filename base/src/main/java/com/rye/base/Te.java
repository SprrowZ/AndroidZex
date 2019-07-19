package com.rye.base;

public class Te {
    public static void main(String[] args) throws Exception {
      System.out.println(true&&false||true);
      System.out.println(false||true&&false);
      System.out.println(true||false&&true);
      if (true){
          throw new Exception("...");
      }
      System.out.println("zz");
    }
}
