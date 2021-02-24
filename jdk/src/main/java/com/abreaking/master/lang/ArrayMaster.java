package com.abreaking.master.lang;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * 数组的一些操作
 * @author liwei
 * @date 2021/2/24
 */
public class ArrayMaster {

    /**
     * Q: 那么 Arrays.copyOfRange() 与 System.arraycopy() 作用是否等同呢？
     *
     * A: 不等同。
     *
     * Arrays.copyOf() 和 Arrays.copyOfRange() 都会内部创建目标数组。前者是直接创建一个和源数组等长的数组，而后者则是根据传参 to 和 from 计算出目标数组长度进行创建。
     *
     * 它们得到的数组都是完整包含了要拷贝内容的，都无法实现目标数组的局部拷贝功能。
     *
     * 例如我要拿到一个长度为 10 的数组，前面 5 个位置的内容来源于「源数组」的拷贝，后面 5 个位置我希望预留给我后面自己做操作，它们都无法满足，只有 System.arraycopy() 可以。
     *
     * 感谢 @pnq 同学提供了高质量的提问，丰富了题解内容 ~
     *
     * 作者：AC_OIer
     * 链接：https://leetcode-cn.com/problems/flipping-an-image/solution/shuang-zhi-zhen-yi-bian-chu-li-huan-you-ik0v1/
     * 来源：力扣（LeetCode）
     */
    @Test
    public void testArraysCopyOfAndSystemCopy(){
        Object[] src = {new User(0),new User(1),new User(2),new User(3),new User(4)};

        Object[] copyByArrays = Arrays.copyOf(src, 2); //它拷贝了之后总是一个新数组了

        Object[] copyBySystem = new Object[src.length];
        System.arraycopy(src,0,copyBySystem,0,2); //它可以局部拷贝

        System.out.println(Arrays.toString(copyByArrays)+"->"+copyByArrays.length); //[1, 2]->2
        System.out.println(Arrays.toString(copyBySystem)+"->"+copyBySystem.length); // [1, 2, 0, 0, 0]->5


        /**
         * Q : 是拷贝了还是引用原来的对象
         * 是浅拷贝，即数组里面的对象还是引用原来的对象
         */

        ((User)src[0]).no = 10086;
        System.out.println(src[0] == copyByArrays[0]);
        System.out.println(src[0] == copyBySystem[0]);

    }

    @Test
    public void testCloneObject() throws CloneNotSupportedException {
        User src = new User(0);
        Object clone = src.clone();
        System.out.println(src);
        System.out.println(clone);
    }

    static class  User implements Cloneable{
        int no;

        public User(int no) {
            this.no = no;
        }



        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();

        }
    }

}
