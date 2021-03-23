package com.abreaking.master.ads.leetcode;

import java.util.*;

/**
 *
 * 341. 扁平化嵌套列表迭代器
 * 给你一个嵌套的整型列表。请你设计一个迭代器，使其能够遍历这个整型列表中的所有整数。
 *
 * 列表中的每一项或者为一个整数，或者是另一个列表。其中列表的元素也可能是整数或是其他列表。
 *
 *
 *
 * 示例 1:
 *
 * 输入: [[1,1],2,[1,1]]
 * 输出: [1,1,2,1,1]
 * 解释: 通过重复调用 next 直到 hasNext 返回 false，next 返回的元素的顺序应该是: [1,1,2,1,1]。
 * 示例 2:
 *
 * 输入: [1,[4,[6]]]
 * 输出: [1,4,6]
 * 解释: 通过重复调用 next 直到 hasNext 返回 false，next 返回的元素的顺序应该是: [1,4,6]。
 * @author liwei
 * @date 2021/3/23
 */
public class FlattenNestedListIterator {

    public static void main(String[] args) {
    }

    /**
     *
     * 算法原理： 每次记录当前正在遍历哪个List<NestedInteger>
     *      用一个下表i指向List<NestedInteger>当前的元素，如果当前元素又是List，那么再构造一个NestedIterator继续遍历 并记录
     *
     * from : Mr.Ronin => https://leetcode-cn.com/problems/flatten-nested-list-iterator/solution/bian-ping-hua-qian-tao-lie-biao-die-dai-ipjzq/
     */
    public class NestedIterator implements Iterator<Integer> {

        List<NestedInteger> nestedList;
        int i = 0;
        NestedIterator iterator; //这里用它来保存当前正在用哪个List<NestedInteger>

        public NestedIterator(List<NestedInteger> nestedList) {
            this.nestedList = nestedList;
        }

        @Override
        public Integer next() {
            NestedInteger nestedInteger = nestedList.get(i);
            if (nestedInteger.isInteger()){
                i++;
                return nestedInteger.getInteger();
            }else{
                return iterator.next();
            }
        }

        @Override
        public boolean hasNext() {
            while (i<nestedList.size()){
                if (nestedList.get(i).isInteger()){
                    return true;
                }
                List<NestedInteger> list = nestedList.get(i).getList();
                if (iterator==null){
                    iterator = new NestedIterator(list);
                }
                if (iterator.hasNext()){
                    return true;
                }
                i++;
                iterator = null;
            }
            return false;
        }
    }


    /**
     * 使用栈的数据结构
     * 注意题干：每次调用Next之前都会先调用hasNext
     */
    public class NestedIterator_stack implements Iterator<Integer> {

        Stack<Iterator<NestedInteger>> stack = new Stack();


        public NestedIterator_stack(List<NestedInteger> nestedList) {
            stack.push(nestedList.iterator());
        }


        @Override
        public Integer next() {
            //调next之前保证了每次都会调用hasNext，所以栈顶的元素就一定是integer
            return stack.peek().next().getInteger();
        }

        /**
         * 算法原理是： Iterator可看做指针，指向的当前List的元素
         *  第一次先是将初始的Iterator放了进来，如果Iterator的下一个是数字，那么将其放在栈顶，如果不是数字，继续将List的Iterator放在栈顶，继续一次循环。
         *  —— 其实这个算法就是利用了集合本身的iterator就保存了到了哪一个元素。
         *
         * 我们可以用一个栈来代替方法一中的递归过程。
         *
         * 具体来说，用一个栈来维护深度优先搜索时，从根节点到当前节点路径上的所有节点。由于非叶节点对应的是一个列表，我们在栈中存储的是指向列表当前遍历的元素的指针（下标）。每次向下搜索时，取出列表的当前指针指向的元素并将其入栈，同时将该指针向后移动一位。如此反复直到找到一个整数。循环时若栈顶指针指向了列表末尾，则将其从栈顶弹出。
         *
         * 下面的代码中，\texttt{C++}C++ 和 \texttt{Java}Java 的栈存储的是迭代器，迭代器可以起到指向元素的指针的效果，\texttt{Go}Go 的栈存储的是切片（视作队列），通过将元素弹出队首的操作代替移动指针的操作。
         *
         * 作者：LeetCode-Solution
         * 链接：https://leetcode-cn.com/problems/flatten-nested-list-iterator/solution/bian-ping-hua-qian-tao-lie-biao-die-dai-ipjzq/
         *
         * @return
         */
        @Override
        public boolean hasNext() {
            while (!stack.isEmpty()){
                Iterator<NestedInteger> peek = stack.peek();
                if (!peek.hasNext()){
                    stack.pop();
                    continue;
                }
                NestedInteger next = peek.next();
                if (next.isInteger()){
                    stack.push(Collections.singletonList(next).iterator());
                    return true;
                }else{
                    stack.push(next.getList().iterator());
                }
            }
            return false;
        }
    }

    /**
     * 使用list先直接把所有integer装起来
     * 但这样其实并不好：因为它会提前把所有的元素都解析出来
     */
    public class NestedIterator_List implements Iterator<Integer> {
        List<Integer> list = new ArrayList<>();
        Iterator<Integer> iterator;

        public NestedIterator_List(List<NestedInteger> nestedList) {
            toList(nestedList,list);
            iterator = list.iterator();
        }

        public void toList(List<NestedInteger> nestedList,List<Integer> list){
            for (NestedInteger nestedInteger : nestedList){
                if (nestedInteger.isInteger()){
                    list.add(nestedInteger.getInteger());
                }else{
                    toList(nestedInteger.getList(),list);
                }
            }
        }

        @Override
        public Integer next() {
            return iterator.next();
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }
    }

    // This is the interface that allows for creating nested lists.
    // You should not implement it, or speculate about its implementation
    public interface NestedInteger {

        // @return true if this NestedInteger holds a single integer, rather than a nested list.
        boolean isInteger();

        // @return the single integer that this NestedInteger holds, if it holds a single integer
        // Return null if this NestedInteger holds a nested list
        Integer getInteger();

        // @return the nested list that this NestedInteger holds, if it holds a nested list
        // Return null if this NestedInteger holds a single integer
        List<NestedInteger> getList();
    }

/**
 * Your NestedIterator object will be instantiated and called as such:
 * NestedIterator i = new NestedIterator(nestedList);
 * while (i.hasNext()) v[f()] = i.next();
 */
}
