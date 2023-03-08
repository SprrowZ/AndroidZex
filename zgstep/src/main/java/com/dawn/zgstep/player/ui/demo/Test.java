package com.dawn.zgstep.player.ui.demo;

/**
 * Create by  [Rye]
 * <p>
 * at 2022/9/14 2:21 下午
 */
public class Test {

    public static void main(String[] args) {
        for(int i = 0;i<10;i++){
            if (i%2==0){
                if(i==4){
                    System.out.println("偶数i:"+i);
                    continue;
                }

            }
            System.out.println("i:"+i);
        }
    }
}
