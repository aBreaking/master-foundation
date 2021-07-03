package com.abreaking.master.ads.leetcode;


import java.util.*;

/**
 * 451. 根据字符出现频率排序
 * 给定一个字符串，请将字符串里的字符按照出现的频率降序排列。
 *
 * 示例 1:
 *
 * 输入:
 * "tree"
 *
 * 输出:
 * "eert"
 *
 * 解释:
 * 'e'出现两次，'r'和't'都只出现一次。
 * 因此'e'必须出现在'r'和't'之前。此外，"eetr"也是一个有效的答案。
 * 示例 2:
 *
 * 输入:
 * "cccaaa"
 *
 * 输出:
 * "cccaaa"
 *
 * 解释:
 * 'c'和'a'都出现三次。此外，"aaaccc"也是有效的答案。
 * 注意"cacaca"是不正确的，因为相同的字母必须放在一起。
 * 示例 3:
 *
 * 输入:
 * "Aabb"
 *
 * 输出:
 * "bbAa"
 *
 * 解释:
 * 此外，"bbaA"也是一个有效的答案，但"Aabb"是不正确的。
 * 注意'A'和'a'被认为是两种不同的字符。
 * @author liwei
 * @date 2021/7/3
 */
public class SortCharactersByFrequency {

    public static void main(String[] args) {
        String s = "aabbcccddeaa";
        System.out.println(new Solution().frequencySort(s));
    }


    static class Solution {
        public String frequencySort(String s) {
            Map<Character,Integer> map = new HashMap<>();

            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                int n = map.containsKey(c)?map.get(c):0;
                map.put(c,n+1);
            }
            StringBuilder builder = new StringBuilder();
            for (Character c : map.keySet()){
                int i = 0;
                for (; i < builder.length();) {
                    char c1 = builder.charAt(i);
                    if (map.get(c) >= map.get(c1)){
                        break;
                    }else{
                        i += map.get(c1);
                    }
                }
                Integer n = map.get(c);
                for (int j = 0; j < n; j++) {
                    builder.insert(i,c);
                }
            }
            return builder.toString();

        }
    }

}
