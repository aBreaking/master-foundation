package com.abreaking.master.ads.leetcode;

/**
 * 面试题 17.21. 直方图的水量
 * 给定一个直方图(也称柱状图)，假设有人从上面源源不断地倒水，最后直方图能存多少水量?直方图的宽度为 1。
 *
 *
 *
 * 上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的直方图，在这种情况下，可以接 6 个单位的水（蓝色部分表示水）。 感谢 Marcos 贡献此图。
 *
 * 示例:
 *
 * 输入: [0,1,0,2,1,0,1,3,2,1,2,1]
 * 输出: 6
 *
 * from : https://leetcode-cn.com/problems/volume-of-histogram-lcci/
 * @author liwei
 * @date 2021/4/2
 */
public class VolumeOfHistogramLcci {

    public static void main(String[] args) {
        int[] height = {0,1,0,2,1,0,1,3,2,1,2,1};
        System.out.println(new Solution().trap(height));
    }

    static class Solution {

        public int trap(int[] height) {
            if (height.length<=1){
                return 0;
            }
            int[] leftMax = new int[height.length];
            int[] rightMax = new int[height.length];
            leftMax[0] = height[0];
            rightMax[height.length-1] = height[height.length-1];
            for (int i = 1; i < height.length; i++) {
                leftMax[i] = Math.max(leftMax[i-1],height[i]);
            }
            for (int i = height.length-2; i >= 0; i--) {
                rightMax[i] = Math.max(rightMax[i+1],height[i]);
            }

            int result = 0;
            for (int i = 0; i < height.length; i++) {
                result += Math.min(leftMax[i],rightMax[i])-height[i];
            }

            return result;
        }

    }
}
