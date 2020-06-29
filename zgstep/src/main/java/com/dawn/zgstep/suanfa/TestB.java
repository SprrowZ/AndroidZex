package com.dawn.zgstep.suanfa;

public class TestB {
    public static void main(String[] args){
        String target="ABCCACBA";
          cal(target);
    }
    public static void cal(String args){

         char[] array  =  args.toCharArray();
         char[] newArray= new char[array.length];//存储消除相同但未插入的字符
          int index=0;//
           for (int i=0;i<array.length-2;){
               if (array[i]==array[i+1]){//如果有相同的，开始进行消除

                   for (int j=i+1;j<array.length;j++){
                        if (array[j]==array[i]){//相同元素全消除

                            i=j;//避免无谓循环,跳过相同元素
                        }else{
                            break;
                        }
                   }
               }else{
                   newArray[index++]=array[i];
               }

           }
           for (int i=0;i<newArray.length;i++){
               System.out.println(newArray[i]);
           }
    }
}
