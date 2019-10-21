package com.abreaking.foundation.jvm;

/**
 * -Xss128k
 * @author liwei_paas
 * @date 2019/9/30
 */
public class StackOverFlowTest {
    private int stackLength = 1;
    public void stackLeak() {
        stackLength++;
        stackLeak();
    }
    public static void main(String[] args) throws Throwable {
        StackOverFlowTest oom = new StackOverFlowTest();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length:" + oom.stackLength);
            throw e;
        }
    }
}
