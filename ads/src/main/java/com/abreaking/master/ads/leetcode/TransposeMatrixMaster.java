package com.abreaking.master.ads.leetcode;

/**
 * 转置矩阵
 *
 * 给你一个二维整数数组 matrix， 返回 matrix 的 转置矩阵 。
 *
 * 矩阵的 转置 是指将矩阵的主对角线翻转，交换矩阵的行索引与列索引。
 *
 * from : https://leetcode-cn.com/problems/transpose-matrix/
 * @author liwei
 * @date 2021/2/25
 */
public class TransposeMatrixMaster {

    class Solution {
        public int[][] transpose(int[][] matrix) {
            int[][] reverse = new int[matrix[0].length][matrix.length];
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    reverse[j][i] = matrix[i][j];
                }
            }

            return reverse;
        }
    }
}
