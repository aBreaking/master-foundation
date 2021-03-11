package com.abreaking.master.ads.leetcode;

import java.util.Stack;

/**
 *227. 基本计算器 II
 * 给你一个字符串表达式 s ，请你实现一个基本计算器来计算并返回它的值。
 *
 * 整数除法仅保留整数部分。
 *
 *
 *
 * 示例 1：
 *
 * 输入：s = "3+2*2"
 * 输出：7
 * 示例 2：
 *
 * 输入：s = " 3/2 "
 * 输出：1
 * 示例 3：
 *
 * 输入：s = " 3+5 / 2 "
 * 输出：5
 *
 *
 * 提示：
 *
 * 1 <= s.length <= 3 * 105
 * s 由整数和算符 ('+', '-', '*', '/') 组成，中间由一些空格隔开
 * s 表示一个 有效表达式
 * 表达式中的所有整数都是非负整数，且在范围 [0, 231 - 1] 内
 * 题目数据保证答案是一个 32-bit 整数
 * from : https://leetcode-cn.com/problems/basic-calculator-ii/
 * @author liwei
 * @date 2021/3/11
 */
public class BasicCalculator2Master {

    public static void main(String[] args) {
        String s = "3+2*2";
        System.out.println(new Solution().calculate(s));
    }


    /**
     * 思路为：记录操作数前面的符号，如果符号是+，数字直接入栈，-则数字的负数入栈。如果是 * / 就直接与栈顶元素计算
     */
    static class Solution {
        public int calculate(String s) {
            int preSign = 1; //操作数前面的符号
            Stack<Integer> stack = new Stack<>(); //记录操作数。如果符号是+，数字直接入栈，-则数字的负数入栈

            for (int i = 0; i < s.length(); i++) {
                switch (s.charAt(i)){
                    case '-'  : preSign = -1;break;
                    case '+' : preSign = 1;break;
                    case '*' : preSign = 2;break;
                    case '/' : preSign = 3;break;
                    default:{
                        int t = 0,j = i;
                        while (j<s.length() && Character.isDigit(s.charAt(j))){
                            t = t*10 + s.charAt(j) - '0';
                            j++;
                        }
                        if (j>i){ //防止空格的问题
                            if (preSign<=1){
                                stack.push(preSign*t);
                            }else{
                                if (!stack.empty()){//乘除直接进行计算
                                    Integer pop = stack.pop();
                                    if (preSign == 2){
                                        stack.push(pop * t);
                                    }else{
                                        stack.push(pop / t);
                                    }
                                }
                            }
                            i = j-1;
                        }



                    }
                }
            }
            int result = 0;
            while (!stack.empty()){
                result += stack.pop();
            }
            return result;
        }
    }
}
