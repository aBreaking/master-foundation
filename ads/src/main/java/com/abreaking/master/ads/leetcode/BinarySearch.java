package com.abreaking.master.ads.leetcode;


/**
 *704. 二分查找
 * 给定一个 n 个元素有序的（升序）整型数组 nums 和一个目标值 target  ，写一个函数搜索 nums 中的 target，如果目标值存在返回下标，否则返回 -1。
 *
 *
 * 示例 1:
 *
 * 输入: nums = [-1,0,3,5,9,12], target = 9
 * 输出: 4
 * 解释: 9 出现在 nums 中并且下标为 4
 * 示例 2:
 *
 * 输入: nums = [-1,0,3,5,9,12], target = 2
 * 输出: -1
 * 解释: 2 不存在 nums 中因此返回 -1
 *
 *
 * 提示：
 *
 * 你可以假设 nums 中的所有元素是不重复的。
 * n 将在 [1, 10000]之间。
 * nums 的每个元素都将在 [-9999, 9999]之间。
 *
 * from : https://leetcode-cn.com/problems/binary-search/
 * @author liwei
 * @date 2021/9/6
 */
public class BinarySearch {

    public static void main(String[] args) {
        int[] nums = {2,3,4,5};
        System.out.println(new Solution2().search(nums,3));
    }

    static class Solution {
        public int search(int[] nums, int target) {
            return search(nums,target,0,nums.length-1);
        }

        public int search(int[] nums, int target,int start ,int end) {
            int m = (start+end)/2;
            if (nums[m] == target){
                return m;
            }
            if (m == start){ //此时说明start+1 = end，那么此时nums[end]!=target就是循环的结束条件了
                return nums[end] == target?end:-1;
            }
            if (nums[m]>target){
                return search(nums,target,start,m);
            }else{
                return search(nums,target,m,end);
            }
        }
    }

    static class Solution2 {
        public int search(int[] nums, int target) {
            int l = 0;
            int r = nums.length-1;
            while (l<=r){
                int mid = (r+l)/2;
                int m = nums[mid];
                if (m == target){
                    return mid;
                }
                if (m > target){
                    r = mid-1;
                }else{
                    l = mid+1;
                }
            }
            return -1;
        }

    }
}
