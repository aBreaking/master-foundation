package com.abreaking.master.ads.leetcode;


/**
 * 713. 乘积小于 K 的子数组
 * 给你一个整数数组 nums 和一个整数 k ，请你返回子数组内所有元素的乘积严格小于 k 的连续子数组的数目。
 *
 *
 * 示例 1：
 *
 * 输入：nums = [10,5,2,6], k = 100
 * 输出：8
 * 解释：8 个乘积小于 100 的子数组分别为：[10]、[5]、[2],、[6]、[10,5]、[5,2]、[2,6]、[5,2,6]。
 * 需要注意的是 [10,5,2] 并不是乘积小于 100 的子数组。
 * 示例 2：
 *
 * 输入：nums = [1,2,3], k = 0
 * 输出：0
 *
 *
 * 提示:
 *
 * 1 <= nums.length <= 3 * 104
 * 1 <= nums[i] <= 1000
 * 0 <= k <= 106
 * @author liwei@abreaking
 * @date 2022/5/5
 */
public class subarrayProductLessThanK {

    public static void main(String[] args) {
        int[] nums = {3,6,2};
        int k = 19;
        int i = new Solution_faliled().numSubarrayProductLessThanK(nums, k);
        System.out.println(i);
    }

    /**
     * 滑动窗口算法：
     */
    static class Solution {
        public int numSubarrayProductLessThanK(int[] nums, int k) {

            if (k <= 1){
                return 0;
            }

            int i =0 ;
            int j =0 ;
            int m =1 ;
            int ret = 0;
            while (j < nums.length){

                m *= nums[j];
                while (m>=k){
                    m /= nums[i];
                    i++;
                }

                ret += j-i+1;
                j++;

            }

            return ret;

        }
    }



    /**
     * 递归这种方式是不得行的，如果遇到Nums带1的情况，成了无限递归了，时间复杂度太差
     * ————liwei
     */
    static class Solution_faliled {

        int ret = 0;

        public int numSubarrayProductLessThanK(int[] nums, int k) {

            for (int i = 0; i < nums.length; i++) {
                nsplt(nums,k,i);
            }
            return ret;
        }

        private void nsplt(int[] nums,float k,int x){
            if (x<nums.length && nums[x] < k ) {
                ret++;
                nsplt(nums, k / nums[x], x + 1);
            }
        }
    }

}
