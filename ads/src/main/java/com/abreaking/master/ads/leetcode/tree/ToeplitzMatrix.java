package com.abreaking.master.ads.leetcode.tree;

/**
 * 766. 托普利茨矩阵
 * 给你一个 m x n 的矩阵 matrix 。如果这个矩阵是托普利茨矩阵，返回 true ；否则，返回 false 。
 *
 * 如果矩阵上每一条由左上到右下的对角线上的元素都相同，那么这个矩阵是 托普利茨矩阵 。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/toeplitz-matrix
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author liwei_paas 
 * @date 2021/2/22
 */
public class ToeplitzMatrix{

    public boolean isToeplitzMatrixByGuanfang(int[][] matrix) {
        for (int i = 0; i < matrix.length - 1; i++) {
            for (int j = 0; j < matrix[0].length-1; j++) {
                if (matrix[i][j]!=matrix[i+1][j+1]){
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 一个解法是对对矩阵每行进行遍历，从第一行开始，找到其每列对角的值，如果都相等，出现一次不相等即为false，反之为true.
     *      这个算法速度挺快的，就是内存占用挺多的。
     *
     */
    public boolean isToeplitzMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            if (!isToeplitzMatrix(matrix,i)){
                return false;
            }
        }
        return true;
    }

    private  boolean isToeplitzMatrix(int[][] matrix,int startLine) {
        int colLength = matrix[startLine].length;
        if(colLength<=1 || matrix.length<=1 || (matrix.length == startLine+1)){
            return true;
        }
        for (int col = 0; col < colLength; col++) {
            for (int line = startLine; line < matrix.length; line++) {
                int x = col+line-startLine;
                if (x<colLength && matrix[startLine][col] != matrix[line][x]){
                    return false;
                }
            }
        }

        return true;
    }


    public static void main(String[] args) {
        //int[][] matrix = {{36,59,71,15,26,82,87},{56,36,59,71,15,26,82},{15,0,36,59,71,15,26}};
        //int[][] matrix = {{1,2,3,4},{5,1,2,3},{9,5,1,2}};
        //int[][] matrix = {{18},{66}};
        int[][] matrix = {{11,74,0,93},{40,11,74,7}};
        System.out.println(new ToeplitzMatrix().isToeplitzMatrix(matrix));
    }
}
