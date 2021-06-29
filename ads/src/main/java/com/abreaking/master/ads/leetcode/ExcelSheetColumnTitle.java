package com.abreaking.master.ads.leetcode;


/**
 * 168. Excel表列名称
 * 给定一个正整数，返回它在 Excel 表中相对应的列名称。
 *
 * 例如，
 *
 *     1 -> A
 *     2 -> B
 *     3 -> C
 *     ...
 *     26 -> Z
 *     27 -> AA
 *     28 -> AB
 *     ...
 * 示例 1:
 *
 * 输入: 1
 * 输出: "A"
 * 示例 2:
 *
 * 输入: 28
 * 输出: "AB"
 * 示例 3:
 *
 * 输入: 701
 * 输出: "ZY"
 * @author liwei
 * @date 2021/6/29
 */
public class ExcelSheetColumnTitle {

    public static void main(String[] args) {
        System.out.println(new Solution().convertToTitle(2));
    }

    static
    class Solution {
        public String convertToTitle(int columnNumber) {
            Character A = 65 ;
            int n = 26;
            StringBuilder builder = new StringBuilder();
            while ( columnNumber > 0){
                columnNumber--;
                char r = (char) (columnNumber % n);
                builder.append((char)(r + A));
                columnNumber = columnNumber/n;
            }

            return builder.reverse().toString();
        }
    }

}
