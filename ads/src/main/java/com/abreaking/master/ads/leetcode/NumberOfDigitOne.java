package com.abreaking.master.ads.leetcode;


/**
 * 233. 数字 1 的个数
 * 给定一个整数 n，计算所有小于等于 n 的非负整数中数字 1 出现的个数。
 *
 *
 *
 * 示例 1：
 *
 * 输入：n = 13
 * 输出：6
 * 示例 2：
 *
 * 输入：n = 0
 * 输出：0
 *
 *
 * 提示：
 *
 * 0 <= n <= 2 * 109
 * @author liwei
 * @date 2021/8/13
 */
public class NumberOfDigitOne {

    public static void main(String[] args) {

        int n = 13;
        System.out.println(new Solution_other().countDigitOne(n));
        System.out.println(new Solution().countDigitOne(n));
    }

    /**
     * 参考：https://leetcode-cn.com/problems/1nzheng-shu-zhong-1chu-xian-de-ci-shu-lcof/solution/mian-shi-ti-43-1n-zheng-shu-zhong-1-chu-xian-de-2/
     * @author liwei
     * @date 2021/8/13
     */
    static class Solution {
        public int countDigitOne(int n) {
            int result = 0;
            int i = 1;
            int h = 1;
            int r = 1;
            int c = 1;
            while (h!=0 ){
                int l = n/i;
                r = n%i;
                c = l%10;
                h = l/10;
                result += h*i;
                if (c == 1){
                    result += r+1;
                }else if (c!=0){
                    result += i;
                }
                i *= 10;
            }

            return result;

        }
    }

    static class Solution_other {
        public int countDigitOne(int n) {
            int digit = 1, res = 0;
            int high = n / 10, cur = n % 10, low = 0;
            while(high != 0 || cur != 0) {
                if(cur == 0) res += high * digit;
                else if(cur == 1) res += high * digit + low + 1;
                else res += (high + 1) * digit;
                low += cur * digit;
                cur = high % 10;
                high /= 10;
                digit *= 10;
            }
            return res;
        }
    }

}
