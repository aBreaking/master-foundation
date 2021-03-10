package com.abreaking.master.ads.leetcode;

import org.junit.Test;

import java.util.Stack;

/**
 * 224. 基本计算器
 * 实现一个基本的计算器来计算一个简单的字符串表达式 s 的值。
 *
 *
 *
 * 示例 1：
 *
 * 输入：s = "1 + 1"
 * 输出：2
 * 示例 2：
 *
 * 输入：s = " 2-1 + 2 "
 * 输出：3
 * 示例 3：
 *
 * 输入：s = "(1+(4+5+2)-3)+(6+8)"
 * 输出：23
 *
 *
 * 提示：
 *
 * 1 <= s.length <= 3 * 105
 * s 由数字、'+'、'-'、'('、')'、和 ' ' 组成
 * s 表示一个有效的表达式
 *
 * from : https://leetcode-cn.com/problems/basic-calculator/
 * @author liwei
 * @date 2021/3/10
 */
public class BasicCalculatorMaster {

    public static void main(String[] args) {
        //String s = "1-(3+5-2+(3+19-(3-1-4+(9-4-(4-(1+(3)-2)-5)+8-(3-5)-1)-4)-5)-4+3-9)-4-(3+2-5)-10";
        String s = " 1-( 5- 2)";
        System.out.println(new Solution().calculate(s));
        System.out.println(new Solution_by_guanfang().calculate(s));
    }


    /**
     *  因为只有加减两种情况，所以直接从左往后计算每个值即可。
     *  没有括号，直接计算即可
     *  遇到括号，括号前面的符号是加号，还是直接计算。 比如3+(2-5) => 3+2-5
     *  遇到括号，括号前面的符号是减号，那么括号里的每个元素都要被反符号，比如 3-(2-5+1) => 3-2+5-1
     *
     * from 官方题解 : https://leetcode-cn.com/problems/basic-calculator/solution/ji-ben-ji-suan-qi-by-leetcode-solution-jvir/
     */
    static class Solution_by_guanfang {
        public int calculate(String s) {
            Stack<Integer> ops = new Stack<>(); //用它来保存符号的正负，-1负数 1正数
            ops.push(1); //默认从正数开始
            int result = 0; //计算结果
            int sign = 1;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                switch (c){
                    case '+': sign = ops.peek();break;
                    case '-': sign = -ops.peek();break;
                    case '(': ops.push(sign);break;
                    case ')' : ops.pop();break;
                    default:{
                        int t= 0,j=i;
                        while (j < s.length() && Character.isDigit(c = s.charAt(j))){
                            t = t*10 + c - '0';
                            j++;
                        }
                        result += sign * t;
                        if (j>i){
                            i = j-1;
                        }
                    }

                }
            }
            return result;
        }
    }


    /**
     * 思路是：先计算括号的里的数据，将计算后的结果也视为字符串中的一部分
     *
     * 虽然思路很好，但是太麻烦了
     *
     * @author liwei
     * @date 2021/3/10
     */
    static class Solution {
        public int calculate(String s) {
            if (s.indexOf("(") == -1 ||s.indexOf(")") == -1){
                return calNoK(s);
            }
            //解决括号的问题
            Stack<Integer> stack = new Stack();
            StringBuilder builder = new StringBuilder(s);
            for (int i = 0; i < builder.length(); i++) {
                if (builder.charAt(i) == '('){
                    stack.push(i);
                }
                if (builder.charAt(i) == ')'){
                    if (stack.empty()){ //右括号必定匹配左括号，否则出错
                        return -1;
                    }
                    int left = stack.pop();
                    int nok = calNoK(builder.substring(left + 1, i));
                    builder.replace(left,i+1,String.valueOf(nok)); //将计算出来的值替换原括号位置
                    i = left; //i从原left重新开始
                }
            }
            //builder里的括号处理完了，直接builder处理
            return calNoK(builder.toString());
        }

        /**
         * 没有括号的计算，主要对+ -算术 的计算
         * 考虑负数的情况
         * @param ns
         * @return
         */
        public int calNoK(String ns){
            int i = -1;
            if ((i=ns.indexOf("+"))!=-1){
                String left = ns.substring(0,i);
                String right = ns.substring(i+1);
                return calNoK(left)+calNoK(right);
            }
            if ((i=ns.indexOf("--"))!=-1){ //这里的计算结果可能会出现负数，并且前面符号是负号的话。还会出现--的情况
                if (i==0){
                    return calNoK(ns.substring(2));
                }
                String left = ns.substring(0,i);
                String right = ns.substring(i+2);
                return calNoK(left)+calNoK(right);
            }
            if ((i=ns.lastIndexOf("-"))>0){
                String left = ns.substring(0,i);
                String right = ns.substring(i+1);
                return calNoK(left)-calNoK(right);
            }
            //既不是+也不是- ，那就直接返回了
            //吐槽leetcode ，还得需要考虑各种空格之类得
            String trim = ns.trim();
            trim = trim.replaceAll(" ", "");
            return Integer.parseInt(trim);
        }
    }


///////////////////////////other test
    @Test
    public void test01(){
        //字符变数字的一个方式
        String a = "123";
        int ret = 0;
        for (int i = 0; i < a.length(); i++) {
            /**
             * int a = '3';
             * a - '0' == 3; //a必须是数字，起始就是ansci编码里，计算数字与0的位置差值，就是结果
             * ret*10 + ( a.charAt(i) - '0' )= ret*10 + a.charAt(i) - '0'
             */
            ret = ret*10 + a.charAt(i) - '0'; // == ret*10 + ( a.charAt(i) - '0' )
        }
        System.out.println(ret);
    }
}
