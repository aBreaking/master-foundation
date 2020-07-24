package com.abreaking.master.ads.leetcode;

/**
 * 算法：通配符匹配
 *
 * 给定一个字符串 (s) 和一个字符模式 (p) ，实现一个支持 '?' 和 '*' 的通配符匹配。
 * '?' 可以匹配任何单个字符。
 * '*' 可以匹配任意字符串（包括空字符串）。
 * 两个字符串完全匹配才算匹配成功。
 *
 * 说明:
 * s 可能为空，且只包含从 a-z 的小写字母。
 * p 可能为空，且只包含从 a-z 的小写字母，以及字符 ? 和 *。
 *
 * 示例 1:
 * 输入:
 * s = "aa"
 * p = "a"
 * 输出: false
 * 解释: "a" 无法匹配 "aa" 整个字符串。
 *
 * 示例 2:
 * 输入:
 * s = "aa"
 * p = "*"
 * 输出: true
 * 解释: '*' 可以匹配任意字符串。
 *
 * 示例 3:
 * 输入:
 * s = "cb"
 * p = "?a"
 * 输出: false
 * 解释: '?' 可以匹配 'c', 但第二个 'a' 无法匹配 'b'。
 *
 * 示例 4:
 * 输入:
 * s = "adceb"
 * p = "*a*b"
 * 输出: true
 * 解释: 第一个 '*' 可以匹配空字符串, 第二个 '*' 可以匹配字符串 "dce".
 *
 * 示例 5:
 * 输入:
 * s = "acdcb"
 * p = "a*c?b"
 * 输出: false
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/wildcard-matching
 *
 * @author liwei_paas
 * @date 2020/7/16
 */
public class WildcardMatchMaster {

    public static void main(String args[]){
        //assertMatch("1a","?*",true);
        //assertMatch("1a","1*",true);
        assertMatch("efg","e*g",true);
        assertMatch("acdcb","a*c?b",false);
        assertMatch("adceb","a*b",true);
        assertMatch("","*",true);
        assertMatch("aa","*?",true);
        assertMatch("","a",false);
        assertMatch("a","?*",true);
        assertMatch("a","*?*?",false);
        assertMatch("a","*?*",true);
        assertMatch("zacabz","*a?b*",false);
        assertMatch("b","*?*?*",false);
        assertMatch("","?",false);
        assertMatch("cd","c*?",true);
        assertMatch("d","*d*d",false);
        assertMatch("ab","*ab",true);
        assertMatch("mississippi","m*si*",true);
    }

     public boolean isMatch3(String s,String p){
         boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
         dp[0][0] = true;
         for (int j = 1; j < p.length() + 1; j++) {
             if (p.charAt(j - 1) == '*') {
                 dp[0][j] = dp[0][j - 1];
             }
         }
         for (int i = 1; i < s.length() + 1; i++) {
             for (int j = 1; j < p.length() + 1; j++) {
                 if (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '?') {
                     dp[i][j] = dp[i - 1][j - 1];
                 } else if (p.charAt(j - 1) == '*') {
                     dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                 }
             }
         }
         return dp[s.length()][p.length()];
     }




    /**
     * 从左至右裁剪字符串法。
     * 失败！
     * 原因：*的匹配考虑不全面，全量匹配还是部分匹配考虑不周到
     * String s = "a";
     * String p = "?*";
     * @param s
     * @param p
     * @return
     */
    private boolean isMatch(String s, String p){
        char[] pc = p.toCharArray();
        // 从左到右匹配s，匹配成功就将s成功 的部分删除掉
        int ls = 0; //记录p匹配到哪里了
        for (int pi = 0; pi < pc.length; pi++) {
            //先比较p 剩余的部分与s是不是一样的，一样的就说明也是匹配成功
            if (s.equals(p.substring(ls))){
                return true;
            }
            //p当前的字符
            char pCur = pc[pi];
            if(s.length()==0 && pCur == '*'){
                ls = pi+1;
                continue;
            }
            if (pCur != '?' && pCur != '*'){
                if (s.indexOf(pCur)==-1){
                    //如果s都不包括了pcur，那么肯定也是匹配不成功的
                    return false;
                }
                continue;
            }

            //有匹配符 * 或者 ？ ,那么截取p中匹配符前面的字符串，看下s还有没有
            String psub = p.substring(ls, pi);//通配符前面的字符串
            //此时psub 必定是 s的开头
            int indexOfs = s.indexOf(psub);
            if (indexOfs!=0){
                return false;
            }
            //此时
            s = s.substring(psub.length());

            int offset = 0 ; //s需要截取的字符串长度1
            //如果是? ，psub基础上再往后1位
            if (pCur == '?'){
                //*的下一个如果是? ，直接往后一位就是了
                offset = 1;
            }else{
                //* 的处理，需要找到*的下一个字符，然后定位s该字符的位置
                char _pc = '*';// * 的下一个字符
                while(pi<pc.length){
                    if (pc[pi] != '*'){
                        _pc = pc[pi];
                        break;
                    }
                    pi++;
                }
                if (_pc == '*'){
                    //p 只剩 *了，也就匹配成功了
                    return true;
                }
                if (_pc == '?'){
                    offset = 1;
                    //找到? 下一个不为
                }else{
                    //pi剩余部分是否还有 * ,如果还有，那么就是需要从s的左边开找，如果没有， 那么就是需要从右边开找
                    String _pr = p.substring(pi);
                    offset = _pr.indexOf("*")==-1?s.lastIndexOf(_pc):s.indexOf(_pc);
                    if (offset==-1){
                        //s已经没有该字符串了，那么也就匹配不成功
                        return false;
                    }
                }
                pi--;
            }
            if (offset>s.length()){
                //需要cut的部分比s还长，那么匹配失败,再看p剩下的是不是全是*，如果是也就成功，否则失败
                String pl = p.substring(pi+1);
                char[] _plc = pl.toCharArray();
                if(_plc.length==0){
                    return false;
                }
                for (int i = 0; i < _plc.length; i++) {
                    if (_plc[i] != '*')  {
                        return false;
                    }
                }
                return true;
            }
            //现在将s给剪切,p只是记录到哪里了
            s = s.substring(offset); //s的剩余部分
            ls = pi+1; //记录p剪切到哪里了
        }
        return s.length()==0 ;
    }

    private static void assertMatch(String s,String p,boolean flag){
        WildcardMatchMaster master = new WildcardMatchMaster();
        boolean match = master.isMatch3(s, p);
        if (match ^ flag){
            System.out.println(s+","+p+"\t->should be "+flag+",but "+match);
        }
    }
}
