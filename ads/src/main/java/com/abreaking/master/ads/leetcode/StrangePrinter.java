package com.abreaking.master.ads.leetcode;


/**
 *664. 奇怪的打印机
 * 有台奇怪的打印机有以下两个特殊要求：
 *
 * 打印机每次只能打印由 同一个字符 组成的序列。
 * 每次可以在任意起始和结束位置打印新字符，并且会覆盖掉原来已有的字符。
 * 给你一个字符串 s ，你的任务是计算这个打印机打印它需要的最少打印次数。
 *
 *
 * 示例 1：
 *
 * 输入：s = "aaabbb"
 * 输出：2
 * 解释：首先打印 "aaa" 然后打印 "bbb"。
 * 示例 2：
 *
 * 输入：s = "aba"
 * 输出：2
 * 解释：首先打印 "aaa" 然后在第二个位置打印 "b" 覆盖掉原来的字符 'a'。
 *
 *
 * 提示：
 *
 * 1 <= s.length <= 100
 * s 由小写英文字母组成
 *
 * from : https://leetcode-cn.com/problems/strange-printer/
 * @author liwei
 * @date 2021/5/24
 */
public class StrangePrinter {

    public static void main(String[] args) {
        String s = "bacaba";
        System.out.println(new Solution().strangePrinter(s));
    }

    /**
     * 	设 f[i][j] 为i到j区间的最小打印次数的解。s[i]是字符串的值。
     * 	s[i][i] = 1;
     * 	如果s[i] = s[j] ，那么f[i][j] = f[i][j-1]。因为同一字符可以直接一起打印了。
     * 	如果s[i] != s[j]，至少进行两次打印，设 i<k<j，f[i][j]=min(f[i][k] +f[k][j])
     */
    static class Solution {
        public int strangePrinter(String s) {
            char[] c = s.toCharArray();
            int n = s.length();
            int[][] f = new int[n][n];
            for (int i = n-1; i >=0; i--) {
                f[i][i] = 1;
                for (int j = i+1; j < n; j++) {
                    if (c[i] == c[j]){
                        f[i][j] = f[i][j-1];
                    }else{
                        int min = Integer.MAX_VALUE;
                        for (int k = i; k < j; k++) {
                            int ck = f[i][k]+f[k+1][j];
                            min = Math.min(min,ck);
                        }
                        f[i][j] = min;
                    }
                }
            }
            return f[0][n-1];
        }
    }
}
