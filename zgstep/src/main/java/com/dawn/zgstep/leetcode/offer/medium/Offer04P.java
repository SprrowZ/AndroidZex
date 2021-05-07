package com.dawn.zgstep.leetcode.offer.medium;

/**
 * Create by rye
 * at 2021/3/6
 *
 * @description: 在一个 n * m 的二维数组中，每一行都按照从左到右递增的顺序排序，
 * 每一列都按照从上到下递增的顺序排序。请完成一个高效的函数，输入这样的一个二维数组和一个整数，
 * 判断数组中是否含有该整数。
 * 现有矩阵 matrix 如下：
 * <p>
 * [
 * [1,   4,  7, 11, 15],
 * [2,   5,  8, 12, 19],
 * [3,   6,  9, 16, 22],
 * [10, 13, 14, 17, 24],
 * [18, 21, 23, 26, 30]
 * ]
 * 给定 target = 5，返回 true。
 * <p>
 * 给定 target = 20，返回 false。
 * -----------------------------------------------------------
 * 应该用二分查找！
 */
public class Offer04P {
    public static boolean findNumberIn2DArray(int[][] matrix, int target) {

        int m = matrix[0].length; //一行的长度,有多少列.ORZ
        int n = matrix.length; //行数
        if (matrix[0][0] > target || m == 0 || n == 0 || matrix[n-1][m-1] < target) {
            return false;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix[i][j] == target) {
                    return true;
                }
            }
        }
        return false;
    }


    public static void output() {
        int[][] matrix = {
                {1, 4, 7, 11, 15},
                {2, 5, 8, 12, 19},
                {3, 6, 9, 16, 22},
                {10, 13, 14, 17, 24},
                {18, 21, 23, 26, 30}
        };
        System.out.println(findNumberIn2DArray(matrix, 5));

    }
}
