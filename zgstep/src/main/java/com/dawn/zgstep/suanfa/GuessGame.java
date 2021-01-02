package com.dawn.zgstep.suanfa;

public class GuessGame {
    public static void main(String[] args) {

        int[] input = {1, 2, 3, 4};
        int[] guess = {1, 2, 3, 4};
        guess(input, guess);
    }

    private static void guess(int[] input, int[] guess) {
        int aNumber = 0, bNumber = 0;
        if (input.length != 4 || guess.length != 4) {
            System.out.println("输出有误，请重新输入！");
            return;
        }
        for (int i = 0; i < input.length; i++) {
            if (input[i] == guess[i]) {
                aNumber++;
            } else {
                for (int j = i; j < input.length; j++) {
                    if (input[i] == guess[j]) {
                        bNumber++;
                    }
                }
            }
        }
        System.out.println(aNumber + "A" + bNumber + "B");
    }
}
