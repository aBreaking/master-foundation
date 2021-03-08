package com.abreaking.master.ads.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 剑指 Offer 03. 数组中重复的数字
 * 找出数组中重复的数字。
 *
 *
 * 在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次。请找出数组中任意一个重复的数字。
 *
 * 示例 1：
 *
 * 输入：
 * [2, 3, 1, 0, 2, 5, 3]
 * 输出：2 或 3
 *
 *
 * 限制：
 *
 * 2 <= n <= 100000
 *
 * @author liwei
 * @date 2021/3/8
 */
public class ShuZuZhongZhongFuDeShuZiLcof {


    /**
     * 原地置换思路，from : https://leetcode-cn.com/problems/shu-zu-zhong-zhong-fu-de-shu-zi-lcof/solution/yuan-di-zhi-huan-shi-jian-kong-jian-100-by-derrick/
     * 题干的前提条件： nums 里的所有数字都在 0～n-1 的范围内
     *
     * 思路：找到每个nums[i] 应该在数据中的位置:  即nums[i]应该放在数组 nums[nums[i]]位置处，然后每次比较与该位置元素是否重复，即为题解。
     *
     */
    class Solution_Fast {
        public int findRepeatNumber(int[] nums) {
            for (int i = 0; i < nums.length; i++) {
                while (nums[i] != i){
                    if (nums[i] == nums[nums[i]]){
                        return nums[i];
                    }
                    int temp = nums[nums[i]];
                    nums[nums[i]] = nums[i];
                    nums[i] = temp;
                }
            }
            return -1;
        }
    }

    /**
     * 最简单的一种思路，用个map把遍历后的每个nums存放起来
     */
    class Solution {
        public int findRepeatNumber(int[] nums) {
            Map<Integer,Integer> map = new HashMap();
            for(int i=0;i<nums.length;i++){
                if(map.containsKey(nums[i])){
                    return nums[i];
                }
                map.put(nums[i],i);
            }
            return -1;
        }
    }
}
