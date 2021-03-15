package com.abreaking.master.ads.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 54. 螺旋矩阵
 * 给你一个 m 行 n 列的矩阵 matrix ，请按照 顺时针螺旋顺序 ，返回矩阵中的所有元素。
 *
 * 示例 1：
 *
 * 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
 * 输出：[1,2,3,6,9,8,7,4,5]
 *
 * 示例 2：
 * 输入：matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
 * 输出：[1,2,3,4,8,12,11,10,9,5,6,7]
 *
 * from : https://leetcode-cn.com/problems/spiral-matrix/
 * @author liwei
 * @date 2021/3/15
 */
public class SpiralMatrixMaster {

    public static void main(String[] args) {
        int[][] matrix = {{1,2,3,4},{5,6,7,8},{9,10,11,12}};
        System.out.println(new Solution().spiralOrder(matrix));
    }

    /**
     * 解法：分治，将矩阵看作洋葱，先拨外层，然后里层又是外层，如此循环。
     * @author liwei
     * @date 2021/3/15
     */
    static class Solution {
        public List<Integer> spiralOrder(int[][] matrix) {
            List<Integer> list = new ArrayList<>();
            int left = 0,top = 0;
            int bottom = matrix.length-1;
            int right = matrix[0].length-1;
            while (left<=right && top<=bottom){
                list.addAll(spiralOrder(matrix,top,bottom,left,right));
                left++;right--;
                top++;bottom--;
            }

            return list;

        }

        private List<Integer> spiralOrder(int[][] matrix,int top,int bottom,int left,int right){
            List<Integer> list = new ArrayList<>();
            for (int i = left; i <= right; i++) {
                list.add(matrix[top][i]);
            }
            if (top==bottom){
                return list; //只有一行，直接返回
            }
            for (int i = top+1; i <= bottom; i++) {
                list.add(matrix[i][right]);
            }
            if (left==right){
                return list; //只有一列，也直接返回
            }

            for (int i = right-1; i >= left; i--) {
                list.add(matrix[bottom][i]);
            }
            for (int i = bottom-1; i >= top+1; i--) {
                list.add(matrix[i][left]);
            }
            return list;
        }
    }
}
