package com.abreaking.master.jvm;

import org.junit.Test;

/**
 * 模拟输出StackOverFlowError 情况的出现
 */
public class StackOverflowErrorKwky {

    private int stackDepth = 0;

    /**
     * 通过递归的方式，使出现SOVFE
     */
    private void stackLeak(){
        stackDepth++;
        stackLeak();
    }

    /**
     * jvm args: -Xss128k
     */
    @Test
    public void stackLeakTest() throws Throwable {
        try{
            stackLeak();
        }catch (Throwable t){
            System.out.println(stackDepth);
            throw t;
        }
    }
}
