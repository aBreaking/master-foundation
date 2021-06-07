package com.abreaking.master.ads.leetcode;

/**
 * 416. 分割等和子集
 * 给你一个 只包含正整数 的 非空 数组 nums 。请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
 *
 *
 *
 * 示例 1：
 *
 * 输入：nums = [1,5,11,5]
 * 输出：true
 * 解释：数组可以分割成 [1, 5, 5] 和 [11] 。
 * 示例 2：
 *
 * 输入：nums = [1,2,3,5]
 * 输出：false
 * 解释：数组不能分割成两个元素和相等的子集。
 *
 *
 * 提示：
 *
 * 1 <= nums.length <= 200
 * 1 <= nums[i] <= 100
 *
 *  from : https://leetcode-cn.com/problems/partition-equal-subset-sum/
 *
 * @author liwei
 * @date 2021/6/7
 */
public class partitionEequalSubsetSum {

    public static void main(String[] args) {
        System.out.println(new Solution().canPartition(new int[]{1,2,5}));
    }

    /**
     * 先把数组的和求出来：sum，那么和的一半就是：bsum = sum/2 。所以，只需要从nums里找出和为bsum的若干元素即可。（每个元素都是大于0的）
     *
     * 令dp[i][j] 表示前i 个元素的和为j 是true或者false
     *
     * 如果nums[i]>j了，很明显，nums[i]不能再放进去，所以 dp[i][j] = dp[i-1][j];
     * 如果nums[i]<=j了，nums[i]可放或者可不放，所以 dp[i][j] = dp[i-1][j] || dp[i-1][j-nums[i]]
     *
     * 最后返回dp[nums.length][bsum] 结果即可
     */
   static class Solution {
        public boolean canPartition(int[] nums) {
            if (nums.length<2){
                return false;
            }
            int sum = 0;
            for (int i = 0; i < nums.length; i++) {
                sum += nums[i];
            }
            if (sum % 2 != 0){ //和为奇数肯定不得行
                return false;
            }
            int bsum = sum/2;
            boolean[] dp = new boolean[bsum+1];
            dp[0] = true;
            for (int i = 1; i <= nums.length; i++) {
                int n = nums[i-1];
                for (int j = bsum; j >= 0; j--) {
                    if (j>=n) dp[j] = dp[j] || dp[j-n];
                }
            }
            return dp[bsum];
        }
    }
}
