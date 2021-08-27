package com.abreaking.master.ads.leetcode;


/**
 *
 * 240. 搜索二维矩阵 II
 * 编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target 。该矩阵具有以下特性：
 *
 * 每行的元素从左到右升序排列。
 * 每列的元素从上到下升序排列。
 *
 *
 * 示例 1：
 *
 *
 * 输入：matrix = {{1,4,7,11,15},{2,5,8,12,19},{3,6,9,16,22},{10,13,14,17,24},{18,21,23,26,30}}, target = 5
 * 输出：true
 * 示例 2：
 *
 *
 * 输入：matrix = {{1,4,7,11,15},{2,5,8,12,19},{3,6,9,16,22},{10,13,14,17,24},{18,21,23,26,30}}, target = 20
 * 输出：false
 *
 * @author liwei
 * @date 2021/8/27
 */
public class searchA2dMatrixii {

    public static void main(String[] args) {
        int[][] matrix = {{1,4,7,11,15},{2,5,8,12,19},{3,6,9,16,22},{10,13,14,17,24},{18,21,23,26,30}};
        System.out.println(new Solution().searchMatrix(matrix,20));
    }

    /**
     * 思路如下：
     * 从右上（15）开始：比15大的，就从下面一个数找，比15小的，就从左边一个数找。 然后再次重复15的这个操作。直到找到。
     *  没找到的情况就是超过matrix的边界了。
     */
    static class Solution {
        public boolean searchMatrix(int[][] matrix, int target) {
            int m = 0;
            int n = matrix[0].length-1;
            while (m>-1 && n>-1 && m<matrix.length && n < matrix[0].length){
                if (matrix[m][n] == target){
                    return true;
                }
                if (matrix[m][n] > target){
                    n--;
                }else{
                    m++;
                }
                
            }
            return false;

        }
    }

}
