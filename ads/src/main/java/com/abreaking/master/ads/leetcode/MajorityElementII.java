package com.abreaking.master.ads.leetcode;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 229. 求众数 II
 * 给定一个大小为 n 的整数数组，找出其中所有出现超过 ⌊ n/3 ⌋ 次的元素。
 *
 *
 *
 *
 *
 * 示例 1：
 *
 * 输入：[3,2,3]
 * 输出：[3]
 * 示例 2：
 *
 * 输入：nums = [1]
 * 输出：[1]
 * 示例 3：
 *
 * 输入：[1,1,1,3,3,2,2,2]
 * 输出：[1,2]
 *
 *
 * 提示：
 *
 * 1 <= nums.length <= 5 * 104
 * -109 <= nums[i] <= 109
 * @author liwei
 * @date 2021/10/22
 */
public class MajorityElementII {

    public static void main(String[] args) {
        int[] nums = {1,1,2};
        List<Integer> list = new Solution().majorityElement(nums);
        List<Integer> list2 = new Solution2().majorityElement(nums);
        System.out.println(list);
        System.out.println(list2);
    }

    /**
     * 算法一：利用hashmap
     */
    static class Solution {
        public List<Integer> majorityElement(int[] nums) {
            List<Integer> list = new ArrayList<>();

            int b = nums.length/3 + 1; //既然是超过，那么遍历时某个数必然出现次数必然为这么多
            Map<Integer,Integer> map = new HashMap<>();

            for (int n : nums){
                Integer m = map.getOrDefault(n, 0);
                m++;
                map.put(n,m);
                if (m == b){
                    list.add(n);
                }
            }

            return list;
        }
    }

    /**
     * 算法二：出现次数要超过n/3次，那么很明显，最后的结果里最多能有两个数。
     * from : https://leetcode-cn.com/problems/majority-element-ii/solution/qiu-zhong-shu-ii-by-leetcode-solution-y1rn/
     * 原理：用消除的方式：如果三个数都不同，消除这三个数一次。
     */
    static class Solution2 {
        public List<Integer> majorityElement(int[] nums) {
            int a1 = 0,a2 = 0;
            int v1 = 0,v2 = 0;
            List<Integer> list = new ArrayList<>();
            for (int n : nums){
                if (v1>0 && a1==n){
                    v1++;
                }else if (v2 >0 && a2 == n){
                    v2++;
                }else if (v1 == 0){
                    v1++;
                    a1 = n;
                }else if (v2 == 0){
                    v2 ++;
                    a2 = n;
                }else{
                    v1--;
                    v2--;
                }
            }

            //如果v1,v2 都>0 ,还得需要判断下a1,a2的次数是不是满足 > n/3
            if (v1==0 && v2 == 0){
                return list;
            }

            v1=0;v2=0;
            for (int n :nums){
                if (n == a1) v1++;
                if (n == a2) v2++;
            }
            if (v1>nums.length/3){
                list.add(a1);
            }
            if (a1!=a2 && v2>nums.length/3){
                list.add(a2);
            }

            return list;
        }
    }


}
