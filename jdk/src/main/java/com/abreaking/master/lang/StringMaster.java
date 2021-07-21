package com.abreaking.master.lang;

import org.junit.jupiter.api.Test;

/**
 * 字符串的几中比较
 * @author liwei
 * @date 2021/2/24
 */
public class StringMaster {

    public static void main(String[] args) {
        String s0 = "string";
        String s1 = "str";
        String s2 = "ing";
        String s3 = "str"+"ing";
        String s4 = s1+s2;
        String s5 = new String("string"); //如果常量池没有string，会创建两个对象
        String s6 = s5.intern();

        System.out.println(s0==s3); //true
        System.out.println(s0==s4); //false
        System.out.println(s0==s5); //false
        System.out.println(s0==s6); //true
        System.out.println(s0.equals(s4)); //true
        System.out.println(s0.equals(s5)); //true

    }


    @Test
    public void testPoolInt(){
        Integer  a = 127;
        Integer  b = 127;
        System.out.println(a==b); //true

        Integer c = 128;
        Integer d = 128;
        System.out.println(c==d); //false

    }

}
