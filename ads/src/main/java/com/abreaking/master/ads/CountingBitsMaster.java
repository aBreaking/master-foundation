package com.abreaking.master.ads;

/**
 *比特位计数
 * 给定一个非负整数 num。对于 0 ≤ i ≤ num 范围中的每个数字 i ，计算其二进制数中的 1 的数目并将它们作为数组返回。
 *
 * 要求算法的空间复杂度为O(n)
 *
 * 示例 1:
 * 输入: 2
 * 输出: [0,1,1]
 *
 * 示例 2:
 * 输入: 5
 * 输出: [0,1,1,2,1,2]
 *
 * from: https://leetcode-cn.com/problems/counting-bits/
 * @author liwei
 * @date 2021/3/3
 */
public class CountingBitsMaster {

    public static void main(String[] args) {
        int i = Integer.bitCount(10);
        System.out.println(i); // 比特位中两个1
        System.out.println(10&1); //判断奇偶数
    }

    class Solution {
        public int[] countBits(int num) {
            int[] ret = new int[num+1];
            for (int i = 1; i < num+1; i++) {
                if ((i&1)==0){//偶数
                    ret[i] =ret[i/2];
                }else{
                    ret[i] = ret[i-1]+1;
                }
            }
            return ret;
        }
    }
}
