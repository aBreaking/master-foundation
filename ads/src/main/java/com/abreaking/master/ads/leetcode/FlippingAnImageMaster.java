package com.abreaking.master.ads.leetcode;

/**
 * 翻转图片
 * 定一个二进制矩阵 A，我们想先水平翻转图像，然后反转图像并返回结果。
 *
 * 水平翻转图片就是将图片的每一行都进行翻转，即逆序。例如，水平翻转 [1, 1, 0] 的结果是 [0, 1, 1]。
 *
 * 反转图片的意思是图片中的 0 全部被 1 替换， 1 全部被 0 替换。例如，反转 [0, 1, 1] 的结果是 [1, 0, 0]。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/flipping-an-image
 * @author liwei
 * @date 2021/2/24
 */
public class FlippingAnImageMaster {

    public static void main(String[] args) {
    }

    static class Solution {
        public int[][] flipAndInvertImage(int[][] A) {
            for (int i = 0; i < A.length; i++) {
                //对每一行进行翻转
                int left = 0 ;
                int right = A[i].length-1;
                while (left<=right){
                    /**
                     * 其实只有左边==右边时才需要操作，具体的操作就是0->1 1->0
                     * 原因：https://leetcode-cn.com/problems/flipping-an-image/solution/fan-zhuan-tu-xiang-by-leetcode-solution-yljd/
                     */
                    if (A[i][left] == A[i][right]){
                        int x = A[i][left];
                        A[i][left] = x==0?1:0;
                        A[i][right] = A[i][left];
                    }
                    left++;
                    right--;
                }
            }

            return A;
        }

    }
}
