package com.abreaking.master.ads.leetcode;

import java.util.Arrays;

/**
 *59. 螺旋矩阵 II
 * 给你一个正整数 n ，生成一个包含 1 到 n2 所有元素，且元素按顺时针顺序螺旋排列的 n x n 正方形矩阵 matrix 。
 *
 *
 *
 * 示例 1：
 *
 *
 * 输入：n = 3
 * 输出：[[1,2,3],[8,9,4],[7,6,5]]
 * 示例 2：
 *
 * 输入：n = 1
 * 输出：[[1]]
 *
 * from : https://leetcode-cn.com/problems/spiral-matrix-ii/
 * @author liwei
 * @date 2021/3/16
 */
public class SpiralMatrix2 {

    public static void main(String[] args) {
        int n = 5;
        int[][] ints = new Solution().generateMatrix(n);
        for (int i = 0; i < ints.length; i++) {
            System.out.println(Arrays.toString(ints[i]));
        }
    }

    static class Solution {
        public int[][] generateMatrix(int n) {
            int[][] ret = new int[n][n];
            set(ret,1,0,n-1);
            return ret;
        }

        /**
         *
         * @param ret 要set的数组
         * @param startNum 从哪个数开始
         * @param i 那行开始
         * @param j 多少列
         */
        private void set(int[][] ret,int startNum,int i,int j){
            if (i==j){
                ret[i][i] = startNum;
                return;
            }
            if (j<=0 || i>=ret.length){
                return;
            }

            for (int k = i; k < j; k++) {
                ret[i][k] = startNum++;
            }
            for (int k = i; k < j; k++) {
                ret[k][j] = startNum++;
            }
            for (int k = j; k > i ; k--) {
                ret[j][k] = startNum++;
            }
            for (int k = j; k > i; k--) {
                ret[k][i] = startNum++;
            }
            j--;
            i++;
            set(ret,startNum,i,j);
        }
    }
}
