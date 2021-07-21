package com.abreaking.master.ads.leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * #title#
 *  n 皇后问题研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
 *
 * #desc#
 *  给定一个整数 n，返回所有不同的 n 皇后问题的解决方案。
 *
 * 每一种解法包含一个明确的 n 皇后问题的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
 *
 * 示例:
 *
 * 输入: 4
 * 输出: [
 *  [".Q..",  // 解法 1
 *   "...Q",
 *   "Q...",
 *   "..Q."],
 *
 *  ["..Q.",  // 解法 2
 *   "Q...",
 *   "...Q",
 *   ".Q.."]
 * ]
 * 解释: 4 皇后问题存在两个不同的解法。
 *
 *
 * #from#
 *  https://leetcode-cn.com/problems/n-queens/
 * @author liwei_paas
 * @date 2020/8/6
 */
public class QueenMaster {

    public List<List<String>> solveNQueens(int n) {
        List<List<String>> ret = new ArrayList<List<String>>(n);
        Set<String> except = new HashSet<String>();
        int isQueen = 0; //isQueen==n即满足
        for (int i = 0; i < n; i++) {
            boolean[][] queen = new boolean[n-1][n-1];
            queen[0][i] = true;
            isQueen ++;
            



        }

        return ret;
    }


}
