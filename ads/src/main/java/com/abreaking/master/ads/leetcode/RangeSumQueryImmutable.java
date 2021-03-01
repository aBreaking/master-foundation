package com.abreaking.master.ads.leetcode;

/**
 * 303. 区域和检索 - 数组不可变
 * 给定一个整数数组  nums，求出数组从索引 i 到 j（i ≤ j）范围内元素的总和，包含 i、j 两点。
 *
 * 实现 NumArray 类：
 *
 * NumArray(int[] nums) 使用数组 nums 初始化对象
 * int sumRange(int i, int j) 返回数组 nums 从索引 i 到 j（i ≤ j）范围内元素的总和，包含 i、j 两点（也就是 sum(nums[i], nums[i + 1], ... , nums[j])）
 *
 * from : https://leetcode-cn.com/problems/range-sum-query-immutable/
 * @author liwei
 * @date 2021/3/1
 */
public class RangeSumQueryImmutable {

    public static void main(String[] args) {
        NumArrayFast numArrayFast = new NumArrayFast(new int[]{-2,0,3,-5,2,-1});
        int i = numArrayFast.sumRange(0, 2);
        System.out.println(i);
    }

    /**
     * 第二种解法:考虑在初始化时就求出，当前位置i到它前面的元素总和，然后每调用一次sumRange 就只需计算 j 与 i位置的差值即可
     * 比如存在一个数组：下标：0,1,2,3,4,5
     * 那么我们计算出前i位和：0,0+1,0+1+2,0+1+2+3,0+1+2+3+4,0+1+2+3+4+5
     * 此时再计算i,j的和，只需求j位到i-1位的差值即可。 比如求2,5位的和，就等于(0+1+2+3+4+5)-(0+1)
     *
     * @author liwei
     * @date 2021/3/1
     */
    static class NumArrayFast {

        int[] nums;
        int[] fastNums;

        public NumArrayFast(int[] nums) {
            this.nums = nums;
            this.fastNums = new int[nums.length+1];
            if (nums.length>0){
                fastNums[0] = nums[0];
                for (int i = 1; i < nums.length; i++) {
                    fastNums[i] = fastNums[i-1]+nums[i];
                }
            }
        }

        public int sumRange(int i, int j) {
            return i==0?fastNums[j]:fastNums[j]-fastNums[i-1];
        }
    }

    /**
     * 最简单的一种解法，但是有个问题：如果数组很长，每执行一次sumRange操作，都会比较耗时 -> o(n)时间复杂度
     * @author liwei
     * @date 2021/3/1
     */
    class NumArray {
        int[] nums ;

        public NumArray(int[] nums) {
            this.nums = nums;
        }

        public int sumRange(int i, int j) {
            int s = 0;
            for (int k = i; k <= j; k++) {
                s+=nums[k];
            }
            return s;
        }
    }
}
