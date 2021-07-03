package com.abreaking.master.ads.leetcode;


import java.util.HashMap;
import java.util.Map;

/**
 * 3. 无重复字符的最长子串
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 *
 * 示例 1:
 * 输入: s = "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 *
 * 示例 2:
 * 输入: s = "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 *
 * 示例 3:
 * 输入: s = "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 *
 * 示例 4:
 * 输入: s = ""
 * 输出: 0
 *
 * from : https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
 * @author liwei
 * @date 2021/7/2
 */
public class LongestSubstringWithoutRepeatingCharacters {

    public static void main(String[] args) {
        String s = "pwhwkepa";
        System.out.println(new Solution().lengthOfLongestSubstring(s));
    }

    static class Solution {
        public int lengthOfLongestSubstring(String s) {
            if (s.isEmpty()){
                return 0;
            }
            int result = 1;
            char[] chars = s.toCharArray();
            Map<Character,Integer> map = new HashMap<>();
            int i = 0;
            for (int j = 0; j < chars.length; j++) {
                char c = chars[j];
                if (map.containsKey(c)){
                    //判断此时map里c是在i 左边还是右边，左边就不需要管了
                    i = Math.max(i,map.get(c)+1);
                }
                map.put(c,j);
                result = Math.max(result,j - i+1);
            }

            return result;
        }
    }
}
