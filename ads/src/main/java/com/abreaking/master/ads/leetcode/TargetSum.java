package com.abreaking.master.ads.leetcode;



/**
 * 目标和的问题
 *
 * 494. 目标和
 * 给你一个整数数组 nums 和一个整数 target 。
 *
 * 向数组中的每个整数前添加 '+' 或 '-' ，然后串联起所有整数，可以构造一个 表达式 ：
 *
 * 例如，nums = [2, 1] ，可以在 2 之前添加 '+' ，在 1 之前添加 '-' ，然后串联起来得到表达式 "+2-1" 。
 * 返回可以通过上述方法构造的、运算结果等于 target 的不同 表达式 的数目。
 *
 *
 *
 * 示例 1：
 *
 * 输入：nums = [1,1,1,1,1], target = 3
 * 输出：5
 * 解释：一共有 5 种方法让最终目标和为 3 。
 * -1 + 1 + 1 + 1 + 1 = 3
 * +1 - 1 + 1 + 1 + 1 = 3
 * +1 + 1 - 1 + 1 + 1 = 3
 * +1 + 1 + 1 - 1 + 1 = 3
 * +1 + 1 + 1 + 1 - 1 = 3
 * 示例 2：
 *
 * 输入：nums = [1], target = 1
 * 输出：1
 *
 *
 * 提示：
 *
 * 1 <= nums.length <= 20
 * 0 <= nums[i] <= 1000
 * 0 <= sum(nums[i]) <= 1000
 * -1000 <= target <= 100
 *
 * from : https://leetcode-cn.com/problems/target-sum/
 *
 * @author liwei
 * @date 2021/6/7
 */
public class TargetSum {

    public static void main(String[] args) {
        int[] nums = {1,2,1};
        int target = 0;
        System.out.println(new Solution2().findTargetSumWays(nums,target));
    }

    /**
     * 对Solution进行优化
     * 从dp的计算结果可发现，dp[i][] 的结果都是从dp[i-1][] 转移而来的，所以，不需要再记录i的值了，
     */
    static class Solution2{
        public int findTargetSumWays(int[] nums, int target) {
            int sum = 0;
            for (int i = 0; i < nums.length; i++) {
                sum += nums[i];
            }
            if (sum<target || (sum-target)%2!=0){ //neg肯定是正整数
                return 0;
            }

            int neg = (sum-target)/2;
            int[] dp = new int[neg+1];
            dp[0] = 1;
            for (int i = 1; i <= nums.length; i++) {
                int n = nums[i-1];
                for (int j = neg; j>=0; j--) { //这里的j应该从后往前，避免重复计算
                    if (j>=n){
                        dp[j] = dp[j] + dp[j-n];
                    }
                }
            }
            return dp[neg];
        }
    }

    /**
     * 背包问题 ： 每次循环的结果都可以从上一个状态优化而来
     * 因为该问题是必须要选"+"或"-"，所以这样来思考：令 sum 为nums的和，假设从nums中选了n个数是负数，n个负数的和为neg，那么就有 sum-2*neg=target => neg = (sum-target) /2
     * 所以，只需要算出nums里可以有多少个元素和等于neg即可。
     *
     * 计 : dp[i][j]=x 表示前i个数，其目标和为j 的 结果共x个 i<nums.length,j<=target
     * 当 j<nums[i]时，即第i个数肯定不满足了，所以dp[i][j] = dp[i-1][j]
     * 当 j>=nums[i]时，第i个数可能满足(第i个数不选的结果 + 选上结果)，那么dp[i][j] = dp[i-1][j] + dp[i-1][j-nums[i]]
     *
     */
    static class Solution {
        public int findTargetSumWays(int[] nums, int target) {
            int sum = 0;
            for (int i = 0; i < nums.length; i++) {
                sum += nums[i];
            }
            if (sum<target || (sum-target)%2!=0){ //neg肯定是正整数
                return 0;
            }

            int neg = (sum-target)/2;
            int[][] dp = new int[nums.length+1][neg+1];
            dp[0][0] = 1;
            for (int i = 1; i <= nums.length; i++) {
                int n = nums[i-1];
                for (int j = 0; j <= neg; j++) {
                    if (j<n){
                        dp[i][j] = dp[i-1][j];
                    }else{
                        dp[i][j] = dp[i-1][j] + dp[i-1][j-n];
                    }
                }
            }
            return dp[nums.length][neg];
        }
    }
}
