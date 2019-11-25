package com.catcher.zzsdk.leetcode;

public class DemoThree {
   public static void main(String[] args){

   }
   public static int getMaxLength(String variable){
       if ("".equals(variable)){
           return 0;
       }
       int length=1;
       int maxLength=1;
       for(int i=0;i<variable.length();i++)
           for (int j=0;j<variable.length();j++){
               if (variable.charAt(i)!=variable.charAt(j)){
                   length++;
               }else{
                   if (length>maxLength){
                       maxLength=length;
                   }
                   length=0;
                   break;
               }
           }

       return maxLength;
   }
}
